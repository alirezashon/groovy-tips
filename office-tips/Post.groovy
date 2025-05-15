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

def issueManager = ComponentAccessor.getIssueManager();
Issue issueKey  = issue
def customFieldManager = ComponentAccessor.getCustomFieldManager()
def currentkey = "${issue.key}"
//--------------------------GET-CUSTOM-FEILDS-----------------
def Owner_of_equipment = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_12550"))
def SIM_Owner = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_22302"))
def Accessories_Owner = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_22304"))
def New_CPE_Serial = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_21806"))
def New_ICCID = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_21807"))
def Old_CPE_Serial = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_21805"))
def Old_ICCID = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_21808"))
def Accessories = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_22000"))
def Agent_Name = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_12565"))
//def Province = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_12557"))
//def City = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_125558"))
//def Region = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_21801"))
def CPE_asset_detail_NEW = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_21810"))
def CPE_asset_detail_OLD = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_21802"))
def SIM_asset_detail_NEW = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_21801"))
def SIM_asset_detail_OLD = (String)issue.getCustomFieldValue(customFieldManager.getCustomFieldObject("customfield_21800"))


//--------------------------API-SAMPLE-----------------------
		 
//class Update {
//def location = "Customer Location > Region 4 > Esfahan > Esfahan"
class Update {
    String location
    String key = ""
    def makeRequest(String url, String requestBody) {
        def http = new HTTPBuilder("${url}")
        http.request(POST, ContentType.JSON) {
            requestContentType = ContentType.JSON
            request.addHeader("ContentType", "application/json")
            request.addHeader("Authorization", "bearer ${key}")
            def parser = new JsonSlurper()
            def jsonBody = parser.parseText(requestBody)
            body = jsonBody
            response.success = { resp, JSON ->
                return JSON
            }
        }
    }
    def updateAsset(String code, String status, String customerDetails, String childAssetCode, String customerName, String agentCode, String isRemoveLink, String allottedTo, String settlementType) {
        def requestBody = """
            {
                "userId": "3198",
                "assetcode": "${code}",
                "status": "${status}",
                "Location": "${location}",
                "CustomerDetails": "${customerDetails}",
                "ChildAssetcode": "${childAssetCode}",
                "CustomerName": "${customerName}",
                "AgentCode": "${agentCode}",
                "IsRemoveLink": "${isRemoveLink}",
                "AllottedTo": "${allottedTo}",
                "SettlementType": "${settlementType}"
            }
        """

      makeRequest("AssetInstallation", requestBody)}

    def linkAsset(String cpeAssetCode, String simAssetCode) {
        def requestBody = """
            {
                "userId": "3198",
                "assetList": [{
                    "assetcode": "${cpeAssetCode}",
                    "linkassetcode": "${simAssetCode}"
                }]
            }
        """
        makeRequest("linkAsset", requestBody)
    }

    def updateStatus(String assetCode, String status, String customerDetails, String remark) {
        def requestBody = """
            {
                "userId": "3198",
                "assetcode": "${assetCode}",
                "status": "${status}",
                "location": "${location}",
                "allottedto": "customer@test.ir",
                "isCustomUpdate": true,
                "remark": "${remark}"
            }
        """
        makeRequest("updateAsset", requestBody)
    }
}

// Example usage
def myClass = new Update()
myClass.location = "Customer Location > Region 4 > Esfahan > Esfahan"

// Call the linkAsset method with different asset codes
//myClass.linkAsset("cpe_assetcode1", "sim_assetcode1")

// Call the updateStatus method with different asset codes and request body values
//myClass.updateStatus("cpe_assetcode1", "Customer---Installation consum", "customer_details1", "remark1")

def response1 = myClass.updateAsset( "AST2023333658", "Contractor---Intact", "currentkey", "AST2023336032", "AZ", "10000000", "1", "3107", "yakhchi")

log.warn(response1)



















        
