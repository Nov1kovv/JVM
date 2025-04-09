package com.example.jvm.kotlin.rx

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

fun main() {
    takeUntilObservable()
    takeWhileObservable()
    takeWhileWithConditionObservable()
    skipWhileObservable()
    skipUntilObservable()
}

//takeUntil: эмиттируем элементы до тех пор, пока не произойдёт определённое условие
fun takeUntilObservable() {
    val observable: Observable<Int> = Observable.create<Int> {
        it.onNext(1)
        it.onNext(2)
        it.onNext(3)
        it.onNext(4)
        it.onComplete()
    }

    val observer: Observer<Int> = object : Observer<Int> {
        override fun onComplete() {
            println("Values emitted until condition met")
        }

        override fun onNext(item: Int) {
            println("Received: $item")
        }

        override fun onError(e: Throwable) {
            println("Error: ${e.message}")
        }

        override fun onSubscribe(d: Disposable) {
            println("Subscribed to Observable with takeUntil")
        }
    }

    val stopCondition: Observable<Boolean> = Observable.just(false) // Условие, которое прекратит
    observable.takeUntil(stopCondition) // Эмиттируем до того, как сработает условие
        .subscribe(observer)
}

//takeWhile: эмиттируем элементы, пока выполняется условие
fun takeWhileObservable() {
    val observable: Observable<Int> = Observable.create<Int> {
        it.onNext(1)
        it.onNext(2)
        it.onNext(3)
        it.onNext(4)
        it.onComplete()
    }

    val observer: Observer<Int> = object : Observer<Int> {
        override fun onComplete() {
            println("Emitting values while condition met")
        }

        override fun onNext(item: Int) {
            println("Received: $item")
        }

        override fun onError(e: Throwable) {
            println("Error: ${e.message}")
        }

        override fun onSubscribe(d: Disposable) {
            println("Subscribed to Observable with takeWhile")
        }
    }

    observable.takeWhile { it < 3 } // Эмиттируем значения, пока они меньше 3
        .subscribe(observer)
}

//takeWhile с условием: эмиттируем значения, пока выполняется условие
fun takeWhileWithConditionObservable() {
    val observable: Observable<Int> = Observable.create<Int> {
        it.onNext(1)
        it.onNext(2)
        it.onNext(3)
        it.onNext(4)
        it.onComplete()
    }

    val observer: Observer<Int> = object : Observer<Int> {
        override fun onComplete() {
            println("Values emitted while condition was true")
        }

        override fun onNext(item: Int) {
            println("Received: $item")
        }

        override fun onError(e: Throwable) {
            println("Error: ${e.message}")
        }

        override fun onSubscribe(d: Disposable) {
            println("Subscribed to Observable with takeWhile with condition")
        }
    }

    observable.takeWhile { it != 3 } // Эмиттируем значения, пока не встретится число 3
        .subscribe(observer)
}

//skipWhile: пропускаем элементы, пока выполняется условие
fun skipWhileObservable() {
    val observable: Observable<Int> = Observable.create<Int> {
        it.onNext(1)
        it.onNext(2)
        it.onNext(3)
        it.onNext(4)
        it.onComplete()
    }

    val observer: Observer<Int> = object : Observer<Int> {
        override fun onComplete() {
            println("Skipped values until condition was false")
        }

        override fun onNext(item: Int) {
            println("Received: $item")
        }

        override fun onError(e: Throwable) {
            println("Error: ${e.message}")
        }

        override fun onSubscribe(d: Disposable) {
            println("Subscribed to Observable with skipWhile")
        }
    }

    observable.skipWhile { it < 3 } // Пропускаем значения, пока они меньше 3
        .subscribe(observer)
}

//skipUntil: пропускаем элементы до тех пор, пока не произойдёт условие
fun skipUntilObservable() {
    val observable: Observable<Int> = Observable.create<Int> {
        it.onNext(1)
        it.onNext(2)
        it.onNext(3)
        it.onNext(4)
        it.onComplete()
    }

    val stopCondition: Observable<Boolean> = Observable.just(true) // Условие, которое прекратит пропуск значений
    val observer: Observer<Int> = object : Observer<Int> {
        override fun onComplete() {
            println("Skipped values until condition met")
        }

        override fun onNext(item: Int) {
            println("Received: $item")
        }

        override fun onError(e: Throwable) {
            println("Error: ${e.message}")
        }

        override fun onSubscribe(d: Disposable) {
            println("Subscribed to Observable with skipUntil")
        }
    }

    observable.skipUntil(stopCondition) // Пропускаем значения до тех пор, пока не сработает условие
        .subscribe(observer)
}