import com.atlassian.jira.issue.Issue
import com.atlassian.jira.component.ComponentAccessor

def issueManager = ComponentAccessor.getIssueManager();
Issue issueKey  = issue
def customFieldManager = ComponentAccessor.getCustomFieldManager()
def cpeRes = customFieldManager.getCustomFieldObject("customfield_21902")
def simRes = customFieldManager.getCustomFieldObject("customfield_21901")
 
 switch(data[3].split("_")[1]){
      case "CPE":
        issue.setCustomFieldValue(cpeRes,data[3].toString()+"&"+assetCode.toString())  // change custom field value
       	break
      case "SIM":
        issue.setCustomFieldValue(simRes,data[3].toString()+"&"+assetCode.toString())
        break
    }
