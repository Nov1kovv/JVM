package com.example.jvm.kotlin.rx

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable


fun main() {
    simpleObservable()
    observableWithError()
    observableWithDelay()
}

// Observable - Эмиттирует последовательность данных, которые могут быть как успешными, так и с ошибкой

// Пример  Observable
fun simpleObservable() {
    val observable: Observable<String> = Observable.create<String> {
        // Эмиттируем несколько значений
        it.onNext("Value 1")
        it.onNext("Value 2")
        it.onNext("Value 3")
        // Эмиттируем завершение
        it.onComplete()
    }

    val observer: Observer<String> = object : Observer<String> {
        override fun onComplete() {
            println("All values emitted successfully!")
        }

        override fun onNext(item: String) {
            println("Received: $item")
        }

        override fun onError(e: Throwable) {
            println("Error: ${e.message}")
        }

        override fun onSubscribe(d: Disposable) {
            println("Subscribed to Observable")
        }
    }

    observable.subscribe(observer)
}

// Пример с ошибкой: если в Observable происходит ошибка
fun observableWithError() {
    val observable: Observable<String> = Observable.create<String> {
        it.onNext("Value 1")
        it.onNext("Value 2")
        it.onError(Throwable("Something went wrong"))
    }

    val observer: Observer<String> = object : Observer<String> {
        override fun onComplete() {
            println("All values emitted successfully!")
        }

        override fun onNext(item: String) {
            println("Received: $item")
        }

        override fun onError(e: Throwable) {
            println("Error: ${e.message}")
        }

        override fun onSubscribe(d: Disposable) {
            println("Subscribed to Observable")
        }
    }

    observable.subscribe(observer)
}

// Пример с задержкой: Эмиттируем значения с задержкой
fun observableWithDelay() {
    val observable: Observable<String> = Observable.create<String> {
        it.onNext("Delayed Value 1")
        Thread.sleep(1000) // Имитируем задержку
        it.onNext("Delayed Value 2")
        Thread.sleep(1000)
        it.onNext("Delayed Value 3")
        it.onComplete()
    }

    val observer: Observer<String> = object : Observer<String> {
        override fun onComplete() {
            println("All delayed values emitted successfully!")
        }

        override fun onNext(item: String) {
            println("Received: $item")
        }

        override fun onError(e: Throwable) {
            println("Error: ${e.message}")
        }

        override fun onSubscribe(d: Disposable) {
            println("Subscribed to Observable with delay")
        }
    }

    observable.subscribe(observer)
}