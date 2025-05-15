// تعریف یک لیست از اسامی
def names = ['Ali', 'Sara', 'Reza']

// استفاده از حلقه برای چاپ اسامی با ترتیب و پیام شخصی
names.eachWithIndex { name, index ->
    println "Hello ${name}! You are person number ${index + 1}."
}

// تعریف یک تابع برای بررسی عدد اول بودن
def isPrime(n) {
    if (n <= 1) return false
    for (int i = 2; i <= Math.sqrt(n); i++) {
        if (n % i == 0) return false
    }
    return true
}

// چاپ اعداد اول بین ۱ تا ۲۰
println "\nPrime numbers between 1 and 20:"
(1..20).each {
    if (isPrime(it)) println it
}
