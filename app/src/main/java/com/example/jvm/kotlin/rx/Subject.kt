package com.example.jvm.kotlin.rx

import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject

fun main() {
    PublicSubjectExample()
}

// Subject - это Observable и Observer одновременноо
// Используется, когда нужно эмиттировать значения вручную и при этом позволить другим подписываться на эти значения

// Пример с Subject (PublishSubject)
fun PublicSubjectExample() {
    val subject: PublishSubject<String> = PublishSubject.create()

    val observer1: Observer<String> = object : Observer<String> {
        override fun onComplete() {
            println("Observer 1: Completed")
        }

        override fun onNext(item: String) {
            println("Observer 1: Received $item")
        }

        override fun onError(e: Throwable) {
            println("Observer 1: Error - ${e.message}")
        }

        override fun onSubscribe(d: Disposable) {
            println("Observer 1: Subscribed to Subject")
        }
    }

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
            println("Observer 2: Subscribed to Subject")
        }
    }

    // Подписка первого наблюдателя
    subject.subscribe(observer1)

    // Эмиттируем данные
    subject.onNext("Subject Value 1")
    subject.onNext("Subject Value 2")

    // Подписка второго наблюдателя, получит только те данные, которые будут эмиттированы после подписки
    subject.subscribe(observer2)

    subject.onNext("Subject Value 3")
    subject.onComplete()
}
