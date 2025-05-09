package com.example.jvm.kotlin.rx.operators

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver

fun main() {
    singleExample()
}

/*
Оператор Single - всегда возвращает ровно одно значение или ошибку.
Подходит для работы с запросами или загрузкой одного элемента.
 */
fun singleExample() {
    val single: Single<Int> = Single.just(1) // создаю Single, который сразу вернёт 1
    val observer = object : DisposableSingleObserver<Int>() {
        override fun onSuccess(value: Int) {
            println("Received value: $value")
        }

        override fun onError(e: Throwable) {
            println("Error: ${e.message}")
        }
    }

    val disposable: Disposable = single.subscribeWith(observer)// Подписка observer на Single и сохраняется Disposable для возможной отписки
    disposable.dispose()
}