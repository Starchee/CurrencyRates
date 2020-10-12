package com.starchee.currencyrates.views

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface ConverterDialogView: MvpView {
    fun setResult(amount: String, result: String)
}