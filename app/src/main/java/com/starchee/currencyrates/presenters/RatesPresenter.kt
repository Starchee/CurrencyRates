package com.starchee.currencyrates.presenters

import com.starchee.currencyrates.R
import com.starchee.currencyrates.model.local.CurrencyLocal
import com.starchee.currencyrates.repositories.RatesRepository
import com.starchee.currencyrates.views.RatesView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@InjectViewState
class RatesPresenter @Inject constructor(
    private val ratesRepository: RatesRepository
) : MvpPresenter<RatesView>() {

    companion object {
        private const val UPDATE_HOURS = 4
    }

    @Volatile
    private var updateAvailable = true
    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        val result = ratesRepository.loadRates()
            .flatMap {
                if (updateAvailable) {
                    checkRatesAge(it.savedDateLocal.date)
                }
                Single.just(it.currencyLocalList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.setRates(it)
            }, {
                updateRates()
            })
        compositeDisposable.add(result)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    fun setFilterQuery(query: String) {
        viewState.filterRates(query = query)
    }

    fun updateRates() {
        viewState.startRefreshing()
        val result = ratesRepository.updateRates()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.setRates(rates = it)
                viewState.stopRefreshing()
            },
                {
                    viewState.showError(R.string.error_internet_connection)
                    viewState.stopRefreshing()
                })
        compositeDisposable.add(result)
    }

    fun startConvert(currencyLocal: CurrencyLocal) {
        viewState.startConvertWindow(currencyLocal = currencyLocal)
    }

    private fun checkRatesAge(date: String) {
        updateAvailable = false

        val currentDate = ZonedDateTime.now()
        val saveDate = ZonedDateTime.parse(date)

        if (UPDATE_HOURS < ChronoUnit.HOURS.between(saveDate, currentDate)) {
            updateRates()
        }

    }
}