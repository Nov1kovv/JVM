package com.example.jvm.kotlin.rx.operators

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableCompletableObserver

/*
Оператор Completable - не возвращает значение, только сигнал завершения или ошибку.
Используется если мы не хотим получать значение.
 */
fun main() {
    val completable: Completable = Completable.fromAction { ////fromAction это метод принимает Action, код без агрументов и без возвращаемого значения
        // Код, который выполняется, но ничего не возвращает
        println("The task is performed without result")
    }

    val observer = object : DisposableCompletableObserver() {
        override fun onComplete() {
            println("The task was completed successfully.")
        }

        override fun onError(e: Throwable) {
            println("Error: ${e.message}")
        }
    }

    val disposable: Disposable = completable.subscribeWith(observer)


    disposable.dispose()//Используется чтобы отписаться от потока и освободить ресурсы
}