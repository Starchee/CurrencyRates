package com.starchee.currencyrates.views

import com.starchee.currencyrates.model.local.CurrencyLocal
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface RatesView: MvpView {
    fun startRefreshing()
    fun stopRefreshing()
    fun setRates(rates: List<CurrencyLocal>)
    fun filterRates(query: String)
    @StateStrategyType(value = SkipStrategy::class)
    fun startConvertWindow(currencyLocal: CurrencyLocal)
    @StateStrategyType(value = SkipStrategy::class)
    fun showError(error: Int)
}