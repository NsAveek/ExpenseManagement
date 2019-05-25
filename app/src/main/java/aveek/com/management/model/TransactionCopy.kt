package aveek.com.management.model

data class TransactionCopy(var paymentType: String? = "",
                           var category: String = "",
                           var purpose: String = "",
                           var amount: Double = 0.0,
                           var date: String = "",
                           var type : String? = null)