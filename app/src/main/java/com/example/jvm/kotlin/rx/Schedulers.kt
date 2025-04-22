package com.example.jvm.kotlin.rx

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

fun main() {
    ioSchedulerExample()
    computationSchedulerExample()
    trampolineSchedulerExample()
    newThreadSchedulerExample()
    singleSchedulerExample()
}


// io(Input/Output): Поток для работы с IO операциями чтения,запись файлов, сетевые запросы)
//Использует пул потоков, который автоматически масштабируется в зависимости от числа активных задач
//Для задач, которые могут блокировать поток, например, операции чтения с диска или обращения к удаленному серверу.
fun ioSchedulerExample() {
    val observable: Observable<String> = Observable.just("Network Request", "File Read")
        .subscribeOn(Schedulers.io())

    val observer: Observer<String> = object : Observer<String> {
        override fun onSubscribe(d: Disposable) {
            println("Subscribed to Observable with IO scheduler")
        }

        override fun onNext(item: String) {
            println("Performed on IO thread: $item")
        }

        override fun onError(e: Throwable) {
            println("Error: ${e.message}")
        }

        override fun onComplete() {
            println("Completed IO scheduler stream")
        }
    }

    observable.subscribe(observer)
}

// computation: Поток для тяжелых вычислений например, математика, обработка данных
// Использует фиксированное количество потоков, которое соответствует количеству доступных процессоров.
// Оптимизирован для задач, которые не блокируют потоки, но требуют значительных вычислительных ресурсов.

fun computationSchedulerExample() {
    val observable: Observable<String> = Observable.just("Heavy Computation 1", "Heavy Computation 2")
        .subscribeOn(Schedulers.computation())

    val observer: Observer<String> = object : Observer<String> {
        override fun onSubscribe(d: Disposable) {
            println("Subscribed to Observable with Computation scheduler")
        }

        override fun onNext(item: String) {
            println("Performed computation: $item")
        }

        override fun onError(e: Throwable) {
            println("Error: ${e.message}")
        }

        override fun onComplete() {
            println("Completed computation stream")
        }
    }

    observable.subscribe(observer)
}

// trampoline: Сначала выполняем в текущем потоке, а затем переключаемся на главный поток
// выполняет задачи в том же потоке, где был вызван, но с постановкой задач в очередь. Задачи выполняются последовательно.

fun trampolineSchedulerExample() {
    val observable: Observable<String> = Observable.just("Task 1", "Task 2")
        .subscribeOn(Schedulers.trampoline()) // Выполняем задачу в текущем потоке

    val observer: Observer<String> = object : Observer<String> {
        override fun onSubscribe(d: Disposable) {
            println("Subscribed to Observable with Trampoline scheduler")
        }

        override fun onNext(item: String) {
            println("Executed task: $item")
        }

        override fun onError(e: Throwable) {
            println("Error: ${e.message}")
        }

        override fun onComplete() {
            println("Completed trampoline scheduler stream")
        }
    }

    observable.subscribe(observer)
}
// newThread: Каждый раз создается новый поток для каждой задачи
// Каждый элемент Observable обрабатывается в новом потоке.
fun newThreadSchedulerExample() {
    val observable: Observable<String> = Observable.just("Task 1", "Task 2")
        .subscribeOn(Schedulers.newThread()) // Каждый раз создается новый поток для каждой задачи

    val observer: Observer<String> = object : Observer<String> {
        override fun onSubscribe(d: Disposable) {
            println("Subscribed to Observable with NewThread scheduler")
        }

        override fun onNext(item: String) {
            println("Executed in new thread: $item")
        }

        override fun onError(e: Throwable) {
            println("Error: ${e.message}")
        }

        override fun onComplete() {
            println("Completed newThread scheduler stream")
        }
    }

    observable.subscribe(observer)
}

// single: Все задачи выполняются в одном потоке, поочередно
// использует только один поток для выполнения всех задач. Все задачи будут выполнены последовательно
// в одном потоке, и они не могут быть выполнены параллельно.
fun singleSchedulerExample() {
    val observable: Observable<String> = Observable.just("Task 1", "Task 2")
        .subscribeOn(Schedulers.single()) // Все задачи выполняются в одном потоке, поочередно

    val observer: Observer<String> = object : Observer<String> {
        override fun onSubscribe(d: Disposable) {
            println("Subscribed to Observable with Single scheduler")
        }

        override fun onNext(item: String) {
            println("Executed sequentially on single thread: $item")
        }

        override fun onError(e: Throwable) {
            println("Error: ${e.message}")
        }

        override fun onComplete() {
            println("Completed single scheduler stream")
        }
    }

    observable.subscribe(observer)
}