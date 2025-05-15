import com.atlassian.jira.issue.Issue
import groovyx.net.http.HTTPBuilder;
import groovyx.net.http.ContentType;
import static groovyx.net.http.ContentType.URLENC;
import groovyx.net.http.ContentType
import com.opensymphony.workflow.InvalidInputException
import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import groovy.json.JsonSlurper 
import com.atlassian.jira.issue.ModifiedValue
import com.atlassian.jira.issue.util.DefaultIssueChangeHolder
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.event.type.EventDispatchOption
import com.atlassian.jira.issue.MutableIssue
import groovy.sql.Sql
import java.sql.Driver
def issueManager = ComponentAccessor.getIssueManager();
Issue issueKey  = issue
def customFieldManager = ComponentAccessor.getCustomFieldManager()
//--------------------------GET-CUSTOM-FEILDS-----------------
def type_of_installation = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_13908")) 
def Owner_of_equipment = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_12550"))
def SIM_Owner = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_22302"))
def Accessories_Owner = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_22304"))
def New_CPE_Serial = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_21806"))
def New_ICCID = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_21807"))
def Old_CPE_Serial = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_21805"))
def Old_ICCID = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_21808"))
def Accessories = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_22000"))
def Agent_Name = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_12565")) 



//--------------------------------------CHECK-AGENT-CODE-------------------------------------------------------------
def errors = [] // Create an empty list to store errors
def  agent_id = ""
    if (Agent_Name){
        def driver = Class.forName("com.mysql.jdbc.Driver").newInstance() as Driver
        def prop = new Properties()
        prop.setProperty("user"," ")
        prop.setProperty("password"," ")
        def conn=driver.connect("DB URL",prop)
    	def sql= new Sql(conn)
    	agent_id = sql.firstRow("select Code from agent where name = ${Agent_Name}")?.Code
    	sql.close()
    	conn.close()
		if(!agent_id){
            errors << "Agent name ${Agent_Name} have not SAPCode."      }}
    else{  errors << "Please insert agent name." }
//--------------------------API-SAMPLE-----------------------
def CallAsset(URL){
     def Key = " "
       def http = new HTTPBuilder(URL)
        http.request(GET, ContentType.JSON) {
            requestContentType = ContentType.JSON
            request.addHeader("ContentType", "application/json")
            request.addHeader("Authorization", "bearer ${Key}")
            def parser = new JsonSlurper()
            response.success = { resp, JSON ->
                    return JSON//.data//.AssetCode[0]
   }    }   }
def Asset = "http"//BASE URL
//-------TEMPLATE-OF-API-CALLING--->----->>>>>>> ["URL", "ERROR", CATEGORY, "ASSET_NAME", ] 
def AccessoryCheck = false
def AccessoriesPool= [
    ["${Asset}status=Contractor---Intact&agentCode=${agent_id}&Category=${Accessories}", "accessory"],
    ["${Asset}status=Contractor---Round%20up&agentCode=${agent_id}&Category=${Accessories}", "round up accessory"],
]
def AssetItems= []
if (Owner_of_equipment == "استفاده از تجهیزات مبین نت") {
    AssetItems.add(["${Asset}serial=${New_CPE_Serial.trim()}","Contractor---Intact", "CPE","NEW_CPE"])
    AssetItems.add(["${Asset}serial=${New_ICCID.trim()}", "Contractor---Intact", "SIM","NEW_SIM"])
    AccessoryCheck = true
} 
else if (Owner_of_equipment == "استفاده از تجهیزات بانک") {
    AssetItems.add(["${Asset}serial=${Old_CPE_Serial.trim()}","Customer---Round up", "CPE", "OLD_CPE"])
    if (SIM_Owner == "استفاده از سیم کارت بانک") {
        AssetItems.add(["${Asset}serial=${Old_ICCID.trim()}", "Customer---Round up", "SIM","OLD_SIM"])
    } else if (SIM_Owner == "استفاده از سیم کارت مبین نت") {
        AssetItems.add(["${Asset}serial=${New_ICCID.trim()}", "Contractor---Intact", "SIM", "NEW_SIM"])
    }
    if (Accessories_Owner == "استفاده از اکسسوری مبین نت") {
        AccessoryCheck = true
    }  
}
else if(Owner_of_equipment == "نصب مودم جدید و جمع آوری مودم بانک"){
    AssetItems.add(["${Asset}serial=${New_CPE_Serial.trim()}", "Contractor---Intact", "CPE","NEW_CPE",])
 	AssetItems.add(["${Asset}serial=${Old_CPE_Serial.trim()}", "Customer---Round up", "CPE","OLD_CPE",]) 
     if (SIM_Owner == "استفاده از سیم کارت بانک"){
    AssetItems.add(["${Asset}serial=${Old_ICCID.trim()}",  "Customer---Round up" , "SIM", "OLD_SIM"])
    }
    else if (SIM_Owner == "استفاده از سیم کارت مبین نت"){
    AssetItems.add(["${Asset}serial=${New_ICCID.trim()}",  "Contractor---Intact", "SIM", "NEW_SIM"])
    }
    if (Accessories_Owner == "استفاده از اکسسوری مبین نت"){
    AccessoryCheck=true
    }
}
//-------CALLING-API-BY-ITERATE-ARRAY-WITH-CHECK-CONDITIONS----------------
//if (type_of_installation == "Installation"){
if (AccessoryCheck == true) {
    boolean responseReceived = false
    AccessoriesPool.each { data ->
        if (!responseReceived) {
            def result = CallAsset(data[0])
            def assetCode = result.data.AssetCode[0].toString()
            if (!assetCode) {
                errors << "Agent does not have any ${data[1]} in its pool" // Add error message to the list
            } else { responseReceived = true // Set flag to indicate response was received
                log.warn("${data[1]} Asset code is ${assetCode}")
  }  }  }  }
AssetItems.each { data ->
  def result = CallAsset(data[0])
  def status = result.data.Status[0].toString() 
  def assetCode = result.data.AssetCode[0].toString() 
  def categoryCode = result.data.CategoryCode[0].toString()
  if(!assetCode){
        errors << "${data[3]} does not exist " // Add error message to the list        
   }
  if(status != data[1]) {
    errors << "${data[3]} status is not valid"      
  }
  if (categoryCode != data[2]) {
    errors << "${data[3]} category is not valid"   
  }
  if (result.data.size > 1) {
    errors << "Please insert ${data[3]} complete" 
  }
    log.warn("${data[3]} Asset code is ${assetCode}")
}
 if(errors) {
  def errorMessage = errors.join('\n -------> ') // Concatenate all error messages
  log.error(errorMessage) // Log all errors
  throw new InvalidInputException(errorMessage) // Throw a single exception with all errors
}
//} for type installation checking
