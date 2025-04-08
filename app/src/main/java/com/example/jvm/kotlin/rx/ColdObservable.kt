package com.example.jvm.kotlin.rx

fun main() {
    coldEmitterExample()
}

// Пример с холодным эмиттером (Cold Observable)
fun coldEmitterExample() {
    val coldObservable: Observable<String> = Observable.create { emitter ->
        // Эмиттируем данные только когда появиться подписчик
        println("Observable starts emitting...")
        emitter.onNext("Value 1")
        emitter.onNext("Value 2")
        emitter.onNext("Value 3")
        emitter.onComplete()
    }

    // Подписка первого наблюдателя
    println("First observer subscribing...")
    val observer1: Observer<String> = object : Observer<String> {
        override fun onSubscribe(d: Disposable) {
            println("First observer subscribed")
        }

        override fun onNext(item: String) {
            println("First observer received: $item")
        }

        override fun onError(e: Throwable) {
            println("Error: ${e.message}")
        }

        override fun onComplete() {
            println("First observer complete!")
        }
    }

    coldObservable.subscribe(observer1)

    // Подписка второго наблюдателя
    println("\nSecond observer subscribing...")
    val observer2: Observer<String> = object : Observer<String> {
        override fun onSubscribe(d: Disposable) {
            println("Second observer subscribed")
        }

        override fun onNext(item: String) {
            println("Second observer received: $item")
        }

        override fun onError(e: Throwable) {
            println("Error: ${e.message}")
        }

        override fun onComplete() {
            println("Second observer complete!")
        }
    }

    coldObservable.subscribe(observer2)
}