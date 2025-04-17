package com.example.jvm.kotlin.rx

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.schedulers.Schedulers

fun main() {
    ThreadExampleWithSchedulers()
}

fun ThreadExampleWithSchedulers() {

    // Создаём Observable, который эмиттирует числа 1, 2, 3 с задержкой
    val source = Observable.create<Int> { emitter ->
        println("Observable: Создание на потоке ${Thread.currentThread().name}")
        for (i in 1..3) {
            Thread.sleep(100) // задержка
            emitter.onNext(i)
        }
        emitter.onComplete()
    }

    // операторы для работы в разных потоках
    source
        .subscribeOn(Schedulers.io()) // генерация данных будет происходить в IO-потоке
        .observeOn(Schedulers.computation()) // обработка map будет в другом потоке
        .map(object : Function<Int, Int> {
            override fun apply(value: Int): Int {
                println("Map: обрабатываем $value на потоке ${Thread.currentThread().name}")
                return value * 10
            }
        })
        .observeOn(Schedulers.single()) // подписчик получит данные на single-потоке
        .subscribe(object : Observer<Int> {
            override fun onSubscribe(d: Disposable) {
                println("Observer: Подписка на потоке ${Thread.currentThread().name}")
            }

            override fun onNext(item: Int) {
                println("Observer: Получено значение $item на потоке ${Thread.currentThread().name}")
            }

            override fun onError(e: Throwable) {
                println("Observer: Ошибка - ${e.message}")
            }

            override fun onComplete() {
                println("Observer: Завершено на потоке ${Thread.currentThread().name}")
            }
        })

    // Пауза, чтобы программа не завершилась до окончания работы Observable
    Thread.sleep(1000)
}