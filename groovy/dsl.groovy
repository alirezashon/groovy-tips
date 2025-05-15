// DSL: تعریف لیست کارها به زبان ساده
def myTasks = {
    task 'Wake up'
    task 'Eat breakfast'
    task 'Code for 2 hours'
    task 'Take a break'
    task 'Sleep'
}

// کلاس‌هایی که دی اس ال رو ممکن می‌کنن
class TaskList {
    def tasks = []

    def task(String description) {
        tasks << description
    }

    def run(Closure closure) {
        closure.delegate = this
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure()
    }

    def printTasks() {
        println "Your Tasks for Today:"
        tasks.eachWithIndex { t, i -> println "${i + 1}. $t" }
    }
}

// اجرا
def taskList = new TaskList()
taskList.run(myTasks)
taskList.printTasks()
