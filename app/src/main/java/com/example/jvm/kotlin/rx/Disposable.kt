package com.example.jvm.kotlin.rx

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

fun main() {
    disposableExample()
}
//Disposable — это интерфейс в RxJava, который представляет собой ссылку на подписку между Observable и Observer.
//С его помощью можно отменить подписку и тем самым остановить получение данных, освободить ресурсы и предотвратить утечки памяти.
//В Android для очистки в onStop(), onDestroy()
fun disposableExample() {
    //Создаю Observable который эмитит значения A B C
    val observable = Observable.just("A", "B", "C")
    //Создаю наблюдателя
    val observer = object : Observer<String> {
        //объявляю переменную для хранения Disposable
        lateinit var disposable: Disposable

        override fun onSubscribe(d: Disposable) {
            //Сохраняем ссылку на Disposable
            disposable = d
            println("subscription")
        }

        override fun onNext(t: String) {
            println("received: $t")
            if (t == "B") {
                println("stop subscribe")
                disposable.dispose() //отключаемся после получения B
            }
        }

        override fun onError(e: Throwable) {
            println("error: ${e.message}")
        }

        override fun onComplete() {
            println("complete")
        }
    }

    observable.subscribe(observer)
}