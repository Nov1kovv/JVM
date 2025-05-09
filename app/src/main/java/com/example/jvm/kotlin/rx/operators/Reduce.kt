package com.example.jvm.kotlin.rx.operators

fun main(){
    reduceExample()
}
/*Оператор reduce - функция, которая позволяет сводить элементы коллекции к одному результату с помощью операции агрегации,
накапливает значение начиная с первого элемента и применяет операции слева
направо. В итоге из списка мы получаем только одно значение.
*/
fun reduceExample(){
    val numbers = listOf(1 ,2, 3, 4)

    val result = numbers
        //acc - это аккамулятор, i текущий элемент списка
        .reduce{ acc, i -> acc * i }
    println(result)
}