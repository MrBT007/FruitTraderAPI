package com.api.fruittrader.service

import com.api.fruittrader.model.FruitTransaction
import com.api.fruittrader.model.TransactionType
import org.springframework.stereotype.Service

@Service
class FruitTraderService {

    private var transactions = mutableListOf<FruitTransaction>()
    private var profit = 0.0

    fun buy(fruitTransaction: FruitTransaction): String {
        transactions.add(fruitTransaction.copy(transactionType = TransactionType.BUY))
        return "BOUGHT ${fruitTransaction.quantity} KG ${fruitTransaction.fruit.toUpperCase()} AT ${fruitTransaction.price} RUPEES/KG"
    }

    private fun availableQuantityOfFruit(fruit: String): Int {
        val bought = transactions.filter { it.transactionType == TransactionType.BUY && it.fruit == fruit }
            .sumBy { it.quantity }

        return bought
    }
    fun sell(fruitTransaction: FruitTransaction): String {
        val availableQuantity = availableQuantityOfFruit(fruitTransaction.fruit)
        if (fruitTransaction.quantity > availableQuantity) {
            return "ERROR: Not enough stock of ${fruitTransaction.fruit.toUpperCase()}.\nTrying to sell: ${fruitTransaction.quantity} KG.\nAvailable: $availableQuantity KG"
        }

        transactions.add(fruitTransaction.copy(transactionType = TransactionType.SELL))
        var remainingQuantityToSell = fruitTransaction.quantity
        val sellPricePerKg = fruitTransaction.price

        val iterator = transactions.iterator()
        while (iterator.hasNext()) {
            val transaction = iterator.next()
            if (transaction.transactionType == TransactionType.BUY && transaction.fruit == fruitTransaction.fruit) {
                if (remainingQuantityToSell <= 0) break

                if (transaction.quantity <= remainingQuantityToSell) {
                    profit += transaction.quantity * (sellPricePerKg - transaction.price)
                    remainingQuantityToSell -= transaction.quantity
                    iterator.remove() // remove this transaction from the list
                } else {
                    profit += remainingQuantityToSell * (sellPricePerKg - transaction.price)
                    transaction.quantity -= remainingQuantityToSell // reduce the quantity in the transaction
                    remainingQuantityToSell = 0
                }
            }
        }

        return "SOLD ${fruitTransaction.quantity} KG ${fruitTransaction.fruit.toUpperCase()} AT ${fruitTransaction.price} RUPEES/KG"
    }



    fun calculateProfit(): Double {
        return profit
    }
}