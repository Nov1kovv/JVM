package com.example.jvm.kotlin.rx

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit

fun main() {
    mergeObservable()
    concatObservable()
    ambObservable()
    zipObservable()
    combineLatestObservable()
    withLatestFromObservable()
    flatMapExample()
    concatMapExample()
}

// merge: объединяет emissions из нескольких Observable в один поток
// Элементы приходят в том порядке, в каком они появляются, порядок не сохраняется
fun mergeObservable() {
    val first = Observable.interval(100, TimeUnit.MILLISECONDS).take(3).map { "First $it" }
    val second = Observable.interval(50, TimeUnit.MILLISECONDS).take(3).map { "Second $it" }

    Observable.merge(first, second)
        .subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                println("\nSubscribed to merge")
            }

            override fun onNext(item: String) {
                println("merge emitted: $item")
            }

            override fun onError(e: Throwable) {
                println("merge error: ${e.message}")
            }

            override fun onComplete() {
                println("merge completed")
            }
        })

    Thread.sleep(1000) // Даем Observable  время на эмиссию
}

// concat: выполняет Observable последовательно, дожидаясь завершения предыдущего
// Поддерживает порядок элементов
fun concatObservable() {
    val first = Observable.just("A", "B")
    val second = Observable.just("1", "2")

    Observable.concat(first, second)
        .subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                println("\nSubscribed to concat")
            }

            override fun onNext(item: String) {
                println("concat emitted: $item")
            }

            override fun onError(e: Throwable) {
                println("concat error: ${e.message}")
            }

            override fun onComplete() {
                println("concat completed")
            }
        })
}

//  amb: выбирает первый Observable, который начнет эмитить данные, остальные игнорирует
fun ambObservable() {
    val slow = Observable.timer(200, TimeUnit.MILLISECONDS).map { "slow" }
    val fast = Observable.timer(100, TimeUnit.MILLISECONDS).map { "fast" }

    Observable.amb(listOf(slow, fast))
        .subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                println("\nSubscribed to amb")
            }

            override fun onNext(item: String) {
                println("amb emitted: $item")
            }

            override fun onError(e: Throwable) {
                println("amb error: ${e.message}")
            }

            override fun onComplete() {
                println("amb completed")
            }
        })

    Thread.sleep(500)
}

// zip:  комбинирует элементы из разных Observable по позиции (1й с 1м, 2й со 2м и т.д.)
fun zipObservable() {
    val letters = Observable.just("A", "B", "C")
    val numbers = Observable.just(1, 2, 3)

    Observable.zip(letters, numbers) { letter, number -> "$letter$number" }
        .subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                println("\nSubscribed to zip")
            }

            override fun onNext(item: String) {
                println("zip emitted: $item")
            }

            override fun onError(e: Throwable) {
                println("zip error: ${e.message}")
            }

            override fun onComplete() {
                println("zip completed")
            }
        })
}

// combineLatest: берет последние значения из каждого источника при любом обновлении
fun combineLatestObservable() {
    val obs1 = Observable.interval(100, TimeUnit.MILLISECONDS).take(3)
    val obs2 = Observable.interval(150, TimeUnit.MILLISECONDS).take(3)

    Observable.combineLatest(obs1, obs2) { a, b -> "combine: $a & $b" }
        .subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                println("\nSubscribed to combineLatest")
            }

            override fun onNext(item: String) {
                println(item)
            }

            override fun onError(e: Throwable) {
                println("combineLatest error: ${e.message}")
            }

            override fun onComplete() {
                println("combineLatest completed")
            }
        })

    Thread.sleep(1000)
}

// withLatestFrom: берет последнее значение второго Observable при эмиссии первого
fun withLatestFromObservable() {
    val trigger = Observable.interval(200, TimeUnit.MILLISECONDS).take(3)
    val data = Observable.interval(100, TimeUnit.MILLISECONDS)

    trigger.withLatestFrom(data) { t, d -> "trigger: $t with latest data: $d" }
        .subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                println("\nSubscribed to withLatestFrom")
            }

            override fun onNext(item: String) {
                println(item)
            }

            override fun onError(e: Throwable) {
                println("withLatestFrom error: ${e.message}")
            }

            override fun onComplete() {
                println("withLatestFrom completed")
            }
        })

    Thread.sleep(1000)
}

// flatMap: разбирает каждый элемент на Observable и объединяет их (порядок НЕ гарантирован)
fun flatMapExample() {
    Observable.just("A", "B", "C")
        .flatMap { letter ->
            Observable.just("$letter-1", "$letter-2").delay((Math.random() * 100).toLong(), TimeUnit.MILLISECONDS)
        }
        .subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                println("\nSubscribed to flatMap")
            }

            override fun onNext(item: String) {
                println("flatMap emitted: $item")
            }

            override fun onError(e: Throwable) {
                println("flatMap error: ${e.message}")
            }

            override fun onComplete() {
                println("flatMap completed")
            }
        })

    Thread.sleep(1000)
}

// concatMap: как flatMap, но сохраняет порядок
fun concatMapExample() {
    Observable.just("X", "Y", "Z")
        .concatMap { letter ->
            Observable.just("$letter-1", "$letter-2").delay(100, TimeUnit.MILLISECONDS)
        }
        .subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                println("\nSubscribed to concatMap")
            }

            override fun onNext(item: String) {
                println("concatMap emitted: $item")
            }

            override fun onError(e: Throwable) {
                println("concatMap error: ${e.message}")
            }

            override fun onComplete() {
                println("concatMap completed")
            }
        })

    Thread.sleep(1000)
}