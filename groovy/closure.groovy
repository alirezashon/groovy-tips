def saySomething(Closure c) {
    println 'befor'
    c()
    println 'after'
}
saySomething {
    println 'I am in closure'
}
