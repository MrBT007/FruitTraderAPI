package com.api.fruittrader.model

data class FruitTransaction(
    val fruit:String,
    val price:Double,
    val quantity:Int,
    var transactionType:TransactionType? = null)

enum class TransactionType {
    BUY,SELL
}
