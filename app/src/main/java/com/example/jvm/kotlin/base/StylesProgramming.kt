package com.example.jvm.kotlin.base

fun main() {
//    imperativeStyleExample()
//    declarativeStyleExample()
    functionalStyleExample()
}

// Императивный стиль: говорит КАК делать (пошаговые инструкции)
// Часто использует циклы, изменяемые переменные, условия и прямое управление потоком
// Прохожусь по всем элементам списка, проверяю делится ли на 2, если да, то кладу его в новый список

fun imperativeStyleExample() {
    val list = listOf(1, 2, 3, 4, 5)
    val evenNumbers = mutableListOf<Int>()

    for (item in list) {
        if (item % 2 == 0) {
            evenNumbers.add(item)
        }
    }

    println("Imperative: $evenNumbers") // [2, 4]
}

// Декларативный стиль: говорит ЧТО нужно получить, а не как это делать
// Описание результата, часто с использованием коллекций, SQL, XML, HTML

fun declarativeStyleExample() {
    val list = listOf(1, 2, 3, 4, 5)
    //Мы не говорим КАК вручную отобрать чётные числа, мы просто описываем ЧТО хотим получить: все числа, которые делятся на 2 без остатка.
    //Через встроенную функцию высшего порядка filter
    val evenNumbers = list.filter { it % 2 == 0 }

    println("Declarative: $evenNumbers") // [2, 4]
}

// Функциональный стиль: декларативный подход + использование функций высшего порядка,
// неизменяемость, отсутствие побочных эффектов, композиция функций

fun functionalStyleExample() {
    val numbers = listOf(1, 2, 3, 4)

    val result = numbers
//        .map { it * 2 }        // [2, 4, 6, 8, 10]
//        .filter { it > 5 }     // [6, 8, 10]
        .reduce { acc, i -> acc + i } // 24

    println("Functional: $result")
}