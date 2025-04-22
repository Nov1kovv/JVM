package com.example.jvm.kotlin.rx

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

fun main() {
    toListObservable()
    mapObservable()
    flatMapObservable()
    switchMapObservable()
}

// toList: собираем все элементы в список и эмиттим один раз
fun toListObservable() {
    val observable: Observable<String> = Observable.just("A", "B", "C")

    val observer: SingleObserver<List<String>> = object : SingleObserver<List<String>> {
        override fun onSubscribe(d: Disposable) {
            println("Subscribed to Observable with toList")
        }

        override fun onSuccess(t: List<String>) {
            println("Received list: $t")
        }

        override fun onError(e: Throwable) {
            println("Error: ${e.message}")
        }
    }

    observable.toList()
        .subscribe(observer)
}

// map преобразуем каждый элемент
fun mapObservable() {
    val observable: Observable<String> = Observable.just("M", "C", "V", "I")

    val observer: Observer<Int> = object : Observer<Int> {
        override fun onSubscribe(d: Disposable) {
            println("Subscribed to Observable with map")
        }

        override fun onNext(item: Int) {
            println("Mapped value: $item")
        }

        override fun onError(e: Throwable) {
            println("Error: ${e.message}")
        }

        override fun onComplete() {
            println("Completed mapping")
        }
    }

    observable.map {
        romanNumeralIntValue(it)
    }.subscribe(observer)
}

// flatMap вытаскиваем вложенные Observable и объединяем в один поток
fun flatMapObservable() {
    val oleg = Student(BehaviorSubject.createDefault(80)) //BehaviorSubject это поток который при подписке сразу выдаёт последнее значение.
    val ivan = Student(BehaviorSubject.createDefault(90))
    val studentStream = PublishSubject.create<Student>() //Создаю поток студентов, PublishSubject начинает эмитить элементы только после подписки.

    val observer: Observer<Int> = object : Observer<Int> {
        override fun onSubscribe(d: Disposable) {
            println("Subscribed to Observable with flatMap")
        }

        override fun onNext(item: Int) {
            println("Score: $item")
        }

        override fun onError(e: Throwable) {
            println("Error: ${e.message}")
        }

        override fun onComplete() {
            println("Completed flatMap stream")
        }
    }

    studentStream
        .flatMap { it.score }
        .subscribe(observer)

    studentStream.onNext(oleg)
    oleg.score.onNext(85)
    studentStream.onNext(ivan)
    oleg.score.onNext(95)
    ivan.score.onNext(100)
}

// switchMap следим только за последним вложенным Observable
fun switchMapObservable() {
    val ryan = Student(BehaviorSubject.createDefault(80))
    val charlotte = Student(BehaviorSubject.createDefault(90))
    val studentStream = PublishSubject.create<Student>()

    val observer: Observer<Int> = object : Observer<Int> {
        override fun onSubscribe(d: Disposable) {
            println("Subscribed to Observable with switchMap")
        }

        override fun onNext(item: Int) {
            println("Latest score: $item")
        }

        override fun onError(e: Throwable) {
            println("Error: ${e.message}")
        }

        override fun onComplete() {
            println("Completed switchMap stream")
        }
    }

    studentStream
        .switchMap { it.score }
        .subscribe(observer)

    studentStream.onNext(ryan)
    ryan.score.onNext(85)
    studentStream.onNext(charlotte)
    ryan.score.onNext(95) // не будет выведен
    charlotte.score.onNext(100)
}

// Метод-конвертер римских цифр
fun romanNumeralIntValue(letter: String): Int {
    return when (letter) {
        "M" -> 1000
        "C" -> 100
        "V" -> 5
        "I" -> 1
        else -> 0
    }
}

// Класс Student с BehaviorSubject
class Student(val score: BehaviorSubject<Int>)
