package aveek.com.management.util

class EventMessage(private val message : String , private val postEvent : Boolean) {
    fun getEvents() : Pair<String,Boolean>{
        return Pair(message,postEvent)
    }
}