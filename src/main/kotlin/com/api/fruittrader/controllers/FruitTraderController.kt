package com.api.fruittrader.controllers

import com.api.fruittrader.model.FruitTransaction
import com.api.fruittrader.service.FruitTraderService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/fruittrader")
class FruitTraderController(private val service: FruitTraderService) {

    @PostMapping("/buy")
    fun buy(@RequestBody fruitTransaction: FruitTransaction): String {
        return service.buy(fruitTransaction)
    }

    @PostMapping("/sell")
    fun sell(@RequestBody fruitTransaction: FruitTransaction): String {
        return service.sell(fruitTransaction)
    }

    @GetMapping("/profit")
    fun profit(): Double {
        return service.calculateProfit()
    }
}