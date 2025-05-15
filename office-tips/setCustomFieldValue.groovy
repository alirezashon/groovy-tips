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

//set value for custom field type select list single choice 
def cf0 = customFieldManager.getCustomFieldObject("customfield_22445")
def asset_check  = (String)issue.getCustomFieldValue(cf0)
def cfConfig = cf0.getRelevantConfig(issue)
value = ComponentAccessor.optionsManager.getOptions(cfConfig)?.find { it.toString() == '1' }
