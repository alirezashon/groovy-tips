// تعریف کلاس ساده
class Animal {
    String name
}

// اضافه کردن متد جدید به صورت داینامیک به کلاس Animal
Animal.metaClass.speak = { ->
    return "Hi, I am a ${delegate.name}"
}

// ساخت یک شی از کلاس Animal
def a = new Animal(name: 'Cat')

// صدا زدن متد جدید که به صورت متاپروگرمینگ اضافه شده
println a.speak()
