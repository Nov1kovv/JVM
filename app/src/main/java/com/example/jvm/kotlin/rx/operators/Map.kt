package com.example.jvm.kotlin.rx.operators

fun main(){
    mapExample()
}
//map - оператор который преобразует каждый элемент списка.
//важно сохранить список в переменную
//на выходе мы получаем список
fun mapExample(){
    val numbers = listOf(1, 2, 3 ,4 ,5)

    val result = numbers
        .map { it * 3 }
    println(result)
}