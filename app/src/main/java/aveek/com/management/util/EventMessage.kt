package aveek.com.management.util

class EventMessage(private val message : String , private val postEvent : EnumEventOperations) {
    fun getEvents() : Pair<String,EnumEventOperations>{
        return Pair(message,postEvent)
    }
}