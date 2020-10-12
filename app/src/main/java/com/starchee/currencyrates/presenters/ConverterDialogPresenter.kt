package com.starchee.currencyrates.presenters

import com.starchee.currencyrates.views.ConverterDialogView
import moxy.InjectViewState
import moxy.MvpPresenter
import java.text.DecimalFormat

@InjectViewState
class ConverterDialogPresenter : MvpPresenter<ConverterDialogView>() {

    private val formatter = DecimalFormat("#.##")

    fun convert(nominal: Int, value: Float, amount: String){

       viewState.setResult(amount = amount, result = formatter.format(nominal * amount.toFloat() / value).toString())
    }
}