package com.example.jvm.kotlin.rx.operators

//Функция filter позволяет выбрать из коллекции те элементы, которые соответствуют определенному условию. Например, можно отфильтровать только четные числа.
fun filterExample() {
    val numbers = listOf(1, 2, 3, 4, 5, 6)

    val result = numbers
        // Оставляем только четные числа
        .filter { it % 2 == 0 }
    println(result)
}