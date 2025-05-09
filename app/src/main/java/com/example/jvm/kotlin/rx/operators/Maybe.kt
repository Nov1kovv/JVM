package com.example.jvm.kotlin.rx.operators


import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableMaybeObserver

/*
Оператор Maybe - может вернуть один элемент, ничего не вернуть, или ошибку.
Используется, когда значение может быть, а может и не быть.
 */
fun main() {
    val maybe: Maybe<Int> = Maybe.just(1)

    val observer = object : DisposableMaybeObserver<Int>() { //Отдаёт 1 значение или ничего (onSuccess), без just нужно самому создать поток и вызвать onSuccess или onComplete
        override fun onSuccess(value: Int) {
            println("Received value: $value")
        }

        override fun onError(e: Throwable) {
            println("An error occurred: ${e.message}")
        }

        override fun onComplete() {
            println("Completed without meaning")
        }
    }

    val disposable: Disposable = maybe.subscribeWith(observer)

    disposable.dispose()
}