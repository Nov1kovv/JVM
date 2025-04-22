package com.example.jvm.kotlin.rx

import android.annotation.SuppressLint
import io.reactivex.rxjava3.subjects.*
import io.reactivex.rxjava3.observers.DisposableObserver

fun main() {
    publishSubjectExample()
    behaviorSubjectExample()
    replaySubjectExample()
    asyncSubjectExample()
    unicastSubjectExample()
}
//Subject — это двунаправленный объект, который: сам принимает данные (onNext() и т.д.),
// и раздаёт их подписчикам, как Observable.
// PublishSubject: Не хранит прошлые значения. Подписчики получают только события после подписки.
fun publishSubjectExample() {
    val subject = PublishSubject.create<String>()

    val disposable1 = subject.subscribeWith(object : DisposableObserver<String>() {
        override fun onNext(item: String) {
            println("PublishSubject → Subscriber 1 received: $item")
        }

        override fun onError(e: Throwable) {}
        override fun onComplete() {}
    })

    subject.onNext("First")

    val disposable2 = subject.subscribeWith(object : DisposableObserver<String>() {
        override fun onNext(item: String) {
            println("PublishSubject → Subscriber 2 received: $item")
        }

        override fun onError(e: Throwable) {}
        override fun onComplete() {}
    })

    subject.onNext("Second")

    disposable1.dispose()
    disposable2.dispose()
}

// BehaviorSubject: Хранит последнее значение. Новый подписчик получает его сразу.
fun behaviorSubjectExample() {
    val subject = BehaviorSubject.createDefault("Initial")

    val disposable1 = subject.subscribeWith(object : DisposableObserver<String>() {
        override fun onNext(item: String) {
            println("BehaviorSubject → Subscriber 1 received: $item")
        }

        override fun onError(e: Throwable) {}
        override fun onComplete() {}
    })

    subject.onNext("Update 1")

    val disposable2 = subject.subscribeWith(object : DisposableObserver<String>() {
        override fun onNext(item: String) {
            println("BehaviorSubject → Subscriber 2 received: $item")
        }

        override fun onError(e: Throwable) {}
        override fun onComplete() {}
    })

    subject.onNext("Update 2")

    disposable1.dispose()
    disposable2.dispose()
}

// ReplaySubject: Хранит всю историю (либо ограниченный размер). Все подписчики получают кэш.
fun replaySubjectExample() {
    val subject = ReplaySubject.createWithSize<String>(2)

    subject.onNext("One")
    subject.onNext("Two")
    subject.onNext("Three") // "One" вытесняется, остаются "Two", "Three"

    val disposable = subject.subscribeWith(object : DisposableObserver<String>() {
        override fun onNext(item: String) {
            println("ReplaySubject → Subscriber received: $item")
        }

        override fun onError(e: Throwable) {}
        override fun onComplete() {}
    })

    subject.onNext("Four")

    disposable.dispose()
}

// AsyncSubject: Передаёт только последнее значение, и только после onComplete().
fun asyncSubjectExample() {
    val subject = AsyncSubject.create<String>()

    val disposable = subject.subscribeWith(object : DisposableObserver<String>() {
        override fun onNext(item: String) {
            println("AsyncSubject → Subscriber received: $item")
        }

        override fun onError(e: Throwable) {}
        override fun onComplete() {
            println("AsyncSubject → Subscriber complete")
        }
    })

    subject.onNext("Alpha")
    subject.onNext("Beta")
    subject.onComplete()

    disposable.dispose()
}

// UnicastSubject:  используется для отправки значений только одному подписчикус
fun unicastSubjectExample() {
    val subject = UnicastSubject.create<String>()

    subject.onNext("Pre-Subscribe 1")
    subject.onNext("Pre-Subscribe 2")

    val disposable = subject.subscribeWith(object : DisposableObserver<String>() {
        override fun onNext(item: String) {
            println("UnicastSubject → Subscriber received: $item")
        }

        override fun onError(e: Throwable) {}
        override fun onComplete() {}
    })

    subject.onNext("After Subscribe")
    subject.onComplete()

    disposable.dispose()
}
