package com.api.fruittrader.service

import com.api.fruittrader.model.FruitTransaction
import com.api.fruittrader.model.TransactionType
import org.springframework.stereotype.Service

@Service
class FruitTraderService {

    private val transactions = mutableListOf<FruitTransaction>()
    private var profit = 0.0

    fun buy(fruitTransaction: FruitTransaction): String {
        transactions.add(fruitTransaction.copy(transactionType = TransactionType.BUY))
        return "BOUGHT ${fruitTransaction.quantity} KG ${fruitTransaction.fruit.toUpperCase()} AT ${fruitTransaction.price} RUPEES/KG"
    }

    fun sell(fruitTransaction: FruitTransaction): String {
        transactions.add(fruitTransaction.copy(transactionType = TransactionType.SELL))

        var remainingQuantityToSell = fruitTransaction.quantity
        val sellPricePerKg = fruitTransaction.price

        val buyTransactions = transactions.filter { it.transactionType == TransactionType.BUY && it.fruit == fruitTransaction.fruit }

        for (buyTransaction in buyTransactions) {
            if (remainingQuantityToSell <= 0) break

            val quantityFromThisTransaction = minOf(buyTransaction.quantity, remainingQuantityToSell)
            profit += quantityFromThisTransaction * (sellPricePerKg - buyTransaction.price)

            remainingQuantityToSell -= quantityFromThisTransaction
        }

        return "SOLD ${fruitTransaction.quantity} KG ${fruitTransaction.fruit.toUpperCase()} AT ${fruitTransaction.price} RUPEES/KG"
    }


    fun calculateProfit(): Double {
        // TODO: Implement profit calculation based on FIFO

        return profit
    }
}