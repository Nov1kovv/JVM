package com.example.jvm.kotlin.rx

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject

fun main(){
hotEmitterExample()
}

fun hotEmitterExample() {
    //Publish излучает(emit) все последующие элементы наблюдаемого источника в момент подписки.
    val hotObservable = PublishSubject.create<String>()

    val observer1 = object : Observer<String> {
        override fun onSubscribe(d: Disposable) {
            println("The first subscriber has subscribed")
        }

        override fun onNext(item: String) {
            println("The first subscriber received: $item")
        }

        override fun onError(e: Throwable) {
            println("Error")
        }

        override fun onComplete() {
            println("The first subscriber is ready")
        }
    }
    hotObservable.subscribe(observer1)

    // Эмитичу данные, до подписки второго подписчика
    hotObservable.onNext("1")
    hotObservable.onNext("2")

    // Подписываем второго наблюдателя
    val observer2 = object : Observer<String> {
        override fun onSubscribe(d: Disposable) {
            println("The second subscriber has subscribed")
        }

        override fun onNext(item: String) {
            println("The second subscriber received $item")
        }

        override fun onError(e: Throwable) {
            println("Error")
        }

        override fun onComplete() {
            println("The second subscriber is ready")
        }
    }
    hotObservable.subscribe(observer2)

    // Эмитичу ещё одно значение
    hotObservable.onNext("3")

    // Завершаю
    hotObservable.onComplete()
}