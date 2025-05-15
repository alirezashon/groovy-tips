import groovy.json.JsonSlurper

// آدرس API
def url = 'https://jsonplaceholder.typicode.com/users'

// ارسال درخواست GET
def connection = new URL(url).openConnection()
connection.requestMethod = 'GET'

// دریافت و پارس کردن JSON
def responseText = connection.inputStream.text
def json = new JsonSlurper().parseText(responseText)

// عملیات روی داده‌ها: فقط کاربرانی که اسمشون با 'C' شروع می‌کنه
def filteredUsers = json.findAll { it.name.startsWith('C') }

// نمایش نتایج
println "Users whose names start with 'C':"
filteredUsers.each { println "- ${it.name} (${it.email})" }
// println(json)