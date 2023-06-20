import com.atlassian.jira.component.ComponentAccessor
import java.util.Random
import com.atlassian.jira.issue.IssueManager
import com.atlassian.jira.issue.Issue;
import groovyx.net.http.HTTPBuilder;
import groovyx.net.http.ContentType;
import static groovyx.net.http.ContentType.URLENC;
import groovyx.net.http.ContentType
import com.atlassian.jira.config.ResolutionManager
import groovy.json.JsonSlurper
import java.net.URLEncoder;
import groovy.json.JsonSlurper  
import static groovyx.net.http.Method.*




def issueManager = ComponentAccessor.getIssueManager();
Issue issueKey  = issue
def customFieldManager = ComponentAccessor.getCustomFieldManager()


def reporter = "${issue.reporter.name}"
def Key = "${issue.key}"
def summary = "${issue.summary}"
def created = issue.getCreated()?.format("dd/MMM/yyyy hh:mm a z") ?: "Unknown"
def status = "${issue.status.name}"

def cf1 = customFieldManager.getCustomFieldObject("customfield_10030")
def cf1Value = issue.getCustomFieldValue(cf1)
def CROwner = cf1Value?.displayName

def cf2 = customFieldManager.getCustomFieldObject("customfield_10024")
def CRStartTime = (String)issue.getCustomFieldValue(cf2)

def cf3 = customFieldManager.getCustomFieldObject("customfield_10408")
def CRDuration = (String)issue.getCustomFieldValue(cf3)

def cf4= customFieldManager.getCustomFieldObject("customfield_10113")
def OutageDuration = (String)issue.getCustomFieldValue(cf4)

def cf5= customFieldManager.getCustomFieldObject("customfield_10016")
def ServiceAffected = (String)issue.getCustomFieldValue(cf5)

def cf6 = customFieldManager.getCustomFieldObject("customfield_11701")
def ProductName = (String)issue.getCustomFieldValue(cf6)

def cf7 = customFieldManager.getCustomFieldObject("customfield_10009")
def category = (String)issue.getCustomFieldValue(cf7)

def cf9 = customFieldManager.getCustomFieldObject("customfield_10608")
def List_of_affected_service_Node = (String)issue.getCustomFieldValue(cf9)
def count_affected_service = "0"

def cf10 = customFieldManager.getCustomFieldObject("customfield_10114")
def OutageStartTime = (String)issue.getCustomFieldValue(cf3)





def Hosting = ["0915" , "0912419" , "0797164"]

def ITEnterprise = ["002900" , "0912155" , "093901388" , "091437" , "0912732"]

def ITCRM = ["0904724","0910" ,"091282" ,"093298417" , "09202311" , "092456206" , "0920339" , "09128919" , "4965631" , "091278732"]

def ITCharging = ["092092900" , "091205" , "09388" , "0910107","09694260","0912332" , "9202311" , "098417" , "09202311" , "09620 , "0912732"]

def ITSoftware =  ["09202900" , "09121905" , "093908388" , "091014627" , "0965631","0912832311" , "092099" " , "091219", "09128732","091694260"]


def ITOSS = ["0900" , "0911905" , "09398388" , "09239" , "091260","09124819" ,"09129332", "0938417" , "093565631" , "02456206", "088732"]


def ITDS =  ["09900" ,"09121505" , "0938388" ,"09160" , "0935491", "09147", "088732"]

def ITInfra = ["09200" , "0912105" , "098388" , "0935437" , "095631", "04808919", "098732"]

def ITDataManagement = ["092900" , "0911905" , "0138388" , "09352007" , "09355631" , "09919" , "0902311" , "02311", "08732"]

def ITDepartment = ["09900","0902339","911905","091732"]


def recipient = []

if(ProductName.contains("IT-Enterprise")){
    recipient.addAll(ITEnterprise)
}
if(ProductName.contains("IT-CRM")){
    recipient.addAll(ITCRM)
}
if(ProductName.contains("IT-Charging")){
    recipient.addAll(ITCharging)
}
 if(ProductName.contains("IT-Software")){
    recipient.addAll(ITSoftware)
}   
if(ProductName.contains("IT-OSS")){
    recipient.addAll(ITOSS)
}
if(ProductName.contains("IT-DS")){
    recipient.addAll(ITDS)
}

if(ProductName.contains("IT-Infra")){
    recipient.addAll(ITInfra)
}

if(ProductName.contains("IT-DataManagement")){
    recipient.addAll(ITDataManagement)
}
if(ProductName.contains("IT-Department")){
    recipient.addAll(ITDepartment)
}
if(category.contains("Hosting-Installation/Dismantle")){
    recipient.addAll(Hosting)
}
if(category.contains("Hosting Visit")){
    recipient.addAll(Hosting)
}
  
log.warn(ProductName) 
log.warn(category) 
log.warn(summary)

//*Outage Duration: ${OutageDurationParrent} - ${OutageDurationChild}

def data = """{ "type": 1,
"recipients":  ${recipient} ,
"values": 
{ "Body": 
" --- Change Request ---
*Ticket: ${Key}
*Summary: ${summary}
*Reporter:  ${reporter} 
*CR Owner: ${CROwner}
*Created: ${created}
*Start Time: ${CRStartTime.replaceAll(/.{5}$/,'')}
*CR Outage Start Time: ${OutageStartTime.replaceAll(/.{5}$/,'')}
*CR Duration: ${CRDuration}
*Service Affected: ${ServiceAffected}
*Status of ticket: ${status}

https://yax.mobinnet.net/browse/${Key}

--- Mobinnet ---
" }}"""
log.warn(data)

class API { 
     def send_sms(data){
         def http = new HTTPBuilder('http://gateway/Sms/Send')
         http.request(POST, ContentType.JSON) {
             requestContentType = ContentType.JSON
             request.addHeader("ContentType", "application/json")
             request.addHeader("accept", "application/json")
             request.addHeader("AppName", "It-yax")
             request.addHeader("Token", " ")
        

        def parser = new JsonSlurper()
    	def json_body = parser.parseText(data)
    	body = json_body
        response.success = { resp, JSON ->
            return JSON
        }
    }
}
}
def req = new API().send_sms(data)
log.warn(req)




