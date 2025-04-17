package com.example.jvm.kotlin.rx

import io.reactivex.rxjava3.observables.ConnectableObservable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

fun main() {
    ConnectableObservableExample()
}

// ConnectableObservable - не начинает эмиттировать данные, пока не будет вызван connect()
// Используется, когда нужно отложить начало эмиссии до того, пока не будут готовы все подписчики

fun ConnectableObservableExample() {
    // Создаю обычный Observable, который просто эмиттирует три строки по порядку
    val source: Observable<String> = Observable.just("Item 1", "Item 2", "Item 3")

    // Преобразую обычный Observable в ConnectableObservable с помощью метода publish
    // Это для контроля, когда начнётся эмиссия элементов
    val connectableObservable: ConnectableObservable<String> = source.publish()

    // Создаю первого наблюдателя (Observer)
    val observer1: Observer<String> = object : Observer<String> {
        override fun onComplete() {
            // Вызывается, когда Observable завершает передачу всех данных
            println("Observer 1: Completed")
        }

        override fun onNext(item: String) {
            // Вызывается каждый раз, когда Observable эмиттирует новый элемент
            println("Observer 1: Received $item")
        }

        override fun onError(e: Throwable) {
            println("Observer 1: Error - ${e.message}")
        }

        override fun onSubscribe(d: Disposable) {
            println("Observer 1: Subscribed to ConnectableObservable")
        }
    }

    // Создаём второго наблюдателя (Observer)
    val observer2: Observer<String> = object : Observer<String> {
        override fun onComplete() {
            println("Observer 2: Completed")
        }

        override fun onNext(item: String) {
            println("Observer 2: Received $item")
        }

        override fun onError(e: Throwable) {
            println("Observer 2: Error - ${e.message}")
        }

        override fun onSubscribe(d: Disposable) {
            println("Observer 2: Subscribed to ConnectableObservable")
        }
    }

    // Подписка наблюдателей до начала эмиссии
    connectableObservable.subscribe(observer1)
    connectableObservable.subscribe(observer2)

    // Запуск эмиссии
    // в итоге получается Observable, который не начинает испускать данные сразу после подписки, а ждёт вызова метода connect
    // для того чтобы несколько подписчиков получили один и тот же поток данных одновременно, а не с какого момента успели подключиться
    connectableObservable.connect()
}
