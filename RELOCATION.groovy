import com.atlassian.jira.component.ComponentAccessor
import java.util.Random
import com.atlassian.jira.issue.IssueManager
import com.atlassian.jira.issue.Issue;
import java.util.Calendar;
import com.azadi.jalalicalendar.JalaliCalendar
import com.atlassian.jira.bc.JiraServiceContextImpl;
import com.atlassian.jira.bc.filter.SearchRequestService;
import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.search.SearchResults;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.web.bean.PagerFilter;
import com.atlassian.query.Query;
import groovy.sql.Sql
import java.sql.*
import java.util.Date

SearchResults getIssuesByJQL(String jql) throws Exception
    {
        SearchService serchService = ComponentAccessor.getComponentOfType(SearchService.class);
   
      SearchService.ParseResult parseResult =
serchService.parseQuery(ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(),
 jql);
         
        if (!parseResult.isValid())
        {
            throw new Exception("jql is not valid. jql was "+jql);
        }
         
        Query query = parseResult.getQuery();
  
          SearchResults sr =
serchService.search(ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(),
 query, PagerFilter.getUnlimitedFilter());
            return sr;
    }
//Get a pointer to the current Issue
def issueManager = ComponentAccessor.getIssueManager();
Issue issueKey  = issue
//def id=getIssuesByJQL("project = BR AND created > startOfDay() AND created < endOfDay()").getTotal()+1000
def jdate = JalaliCalendar.gregorianToJalali(
    new JalaliCalendar.YearMonthDate(
        Calendar.getInstance().get(Calendar.YEAR), 
        Calendar.getInstance().get(Calendar.MONTH), 
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    ))

//Get field
def customFieldManager = ComponentAccessor.getCustomFieldManager()
//def text ="RE-"+jdate+id
//def cf = customFieldManager.getCustomFieldObjectByName("Ticket ID")
//issue.setCustomFieldValue(cf, text)

//def driver = Class.forName("com.mysql.jdbc.Driver").newInstance() as Driver
//def prop = new Properties()
//prop.setProperty("user","dw")
//prop.setProperty("password","Dw@123")
//def conn=driver.connect("jdbc:mysql://10.104.27.12:3306/DW",prop)
//def sql= new Sql(conn)
//try {
//    sql.eachRow("select * from Counter for update"){ row ->
//        def id = row.getString('Number_BR')
//        def text ="RE-"+jdate+id
//        def cf = customFieldManager.getCustomFieldObjectByName("Ticket ID")
//        issue.setCustomFieldValue(cf, text)
//        sql.executeUpdate("update Counter set Number_BR = Number_BR+1")
//    }
//}
//finally
//{
//    sql.close()
//    conn.close()
//}

def cf1 = customFieldManager.getCustomFieldObject("customfield_10614")
def cf2 = customFieldManager.getCustomFieldObject("customfield_12775")
def subscriberid = (String)issue.getCustomFieldValue(cf2)
def cf3 = customFieldManager.getCustomFieldObject("customfield_14037")
def customer = (String)issue.getCustomFieldValue(cf3)
def cf4 = customFieldManager.getCustomFieldObject("customfield_13211")
def branch= (String)issue.getCustomFieldValue(cf4)
def cf5 = customFieldManager.getCustomFieldObject("customfield_12901")
def account = (String)issue.getCustomFieldValue(cf5)
def cf6 = customFieldManager.getCustomFieldObject("customfield_12789")
def ecc= issue.getCustomFieldValue(cf6)
def cf7 = customFieldManager.getCustomFieldObject("customfield_13115")
def cf8 = customFieldManager.getCustomFieldObject("customfield_13906")
def cf9 = customFieldManager.getCustomFieldObject("customfield_10369")
def Province= (String)issue.getCustomFieldValue(cf9)
def cf10 = customFieldManager.getCustomFieldObject("customfield_10364")
def City= (String)issue.getCustomFieldValue(cf10)
def cf11 = customFieldManager.getCustomFieldObject("customfield_14038")
def ECS_Category = (String)issue.getCustomFieldValue(cf11)
def cf12 = customFieldManager.getCustomFieldObject("customfield_10307")
def IP= (String)issue.getCustomFieldValue(cf12)
def cf13 = customFieldManager.getCustomFieldObject("customfield_12557")
def Province1 = (String)issue.getCustomFieldValue(cf13)
def cf14 = customFieldManager.getCustomFieldObject("customfield_12558")
def City1= issue.getCustomFieldValue(cf14)
def cf15= customFieldManager.getCustomFieldObject("customfield_13805")
def ECS_Category1= (String)issue.getCustomFieldValue(cf15)
def cf16 = customFieldManager.getCustomFieldObject("customfield_12717")
def IP1= (String)issue.getCustomFieldValue(cf16)
def cf17 = customFieldManager.getCustomFieldObject("customfield_14206")
def service1= (String)issue.getCustomFieldValue(cf17)
def cf18 = customFieldManager.getCustomFieldObject("customfield_13916")
def clone= (String)issue.getCustomFieldValue(cf18)
def cf19 = customFieldManager.getCustomFieldObject("customfield_13909")
def relocation= (String)issue.getCustomFieldValue(cf19)
def cf20 = customFieldManager.getCustomFieldObject("customfield_11833")
def MAC = (String)issue.getCustomFieldValue(cf20)
def cf21 = customFieldManager.getCustomFieldObject("customfield_10204")
def IMSI= (String)issue.getCustomFieldValue(cf21)
def cf22 = customFieldManager.getCustomFieldObject("customfield_13931")
def IMEI = (String)issue.getCustomFieldValue(cf22)
def cf24= customFieldManager.getCustomFieldObject("customfield_12709")
def MAC1= (String)issue.getCustomFieldValue(cf24)
def cf25 = customFieldManager.getCustomFieldObject("customfield_12712")
def IMSI1= (String)issue.getCustomFieldValue(cf25)
def cf26 = customFieldManager.getCustomFieldObject("customfield_12710")
def IMEI1= (String)issue.getCustomFieldValue(cf26)
def cf27 = customFieldManager.getCustomFieldObject("customfield_12141")
def acount= (String)issue.getCustomFieldValue(cf27)
def cf28 = customFieldManager.getCustomFieldObject("customfield_12901")
def acount1= (String)issue.getCustomFieldValue(cf28)
def cf29 = customFieldManager.getCustomFieldObject("customfield_12789")
def ecc2= (String)issue.getCustomFieldValue(cf29)
def cf30 = customFieldManager.getCustomFieldObject("customfield_14700")
def newsub= (String)issue.getCustomFieldValue(cf30)
//set option
def cfConfig1 = cf15.getRelevantConfig(issue)
def value1 = ComponentAccessor.optionsManager.getOptions(cfConfig1)?.find { it.toString() == ECS_Category }
def cfConfig = cf7.getRelevantConfig(issue)
def value2 = ComponentAccessor.optionsManager.getOptions(cfConfig)?.find { it.toString() == service1 }
def cfConfig2 = cf28.getRelevantConfig(issue)
def value3 = ComponentAccessor.optionsManager.getOptions(cfConfig2)?.find { it.toString() == acount }

//def currentUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()
//if (ComponentAccessor.getGroupManager().getGroupsForUser(currentUser)?.find { it.name == "ecare" }){
if (IP!=null){
    if (subscriberid ==null){
    issue.setCustomFieldValue(cf2,newsub)
    }
    issue.setCustomFieldValue(cf13,Province)
    issue.setCustomFieldValue(cf14,City)
    issue.setCustomFieldValue(cf15,value1)
    issue.setCustomFieldValue(cf16,IP)
    issue.setCustomFieldValue(cf7,value2)
    issue.setCustomFieldValue(cf24,MAC)
    issue.setCustomFieldValue(cf25,IMSI)
    issue.setCustomFieldValue(cf26,IMEI)
    issue.setCustomFieldValue(cf28,value3)
}
def sub= (String)issue.getCustomFieldValue(cf2)
issue.setCustomFieldValue(cf8,sub)
//set submit time
def today = new java.sql.Timestamp(new Date().getTime())
issue.setCustomFieldValue(cf1,today)
//copy old sub to new sub
if (clone == "NO"){

//set sub
def text2= customer +"-"+"SmartSpot"+"-"+branch
def text3= customer +"-"+ branch 
def service= (String)issue.getCustomFieldValue(cf7)
if (service == "SmartSpot"){
issue.setCustomFieldValue(cf2,text2)    
}
else{
issue.setCustomFieldValue(cf2,text3) 
}
}
//set sum
def subid = issue.getCustomFieldValue(cf2)
def text4= "Relocation : (Roundup) - "+subid
def text5= "Relocation : (Installation) - "+subid

if (relocation == "Need Roundup"){
    issue.setSummary(text4)
}
else {
    issue.setSummary(text5)
}
