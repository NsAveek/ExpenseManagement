package aveek.com.management.util

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject



object RxBus {

    private val publisher = PublishSubject.create<EnumEventOperations>()

    fun publish(event: EnumEventOperations) {
        publisher.onNext(event)
    }

    // Listen should return an Observable
    fun listen(): Observable<EnumEventOperations> {
        return publisher
    }
}