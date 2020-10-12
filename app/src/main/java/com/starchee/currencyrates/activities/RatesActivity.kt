package com.starchee.currencyrates.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.starchee.currencyrates.App
import com.starchee.currencyrates.R
import com.starchee.currencyrates.adapters.RatesAdapter
import com.starchee.currencyrates.model.local.CurrencyLocal
import com.starchee.currencyrates.presenters.RatesPresenter
import com.starchee.currencyrates.views.RatesView
import kotlinx.android.synthetic.main.activity_rates.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class RatesActivity : MvpAppCompatActivity(), RatesView {

    private lateinit var ratesAdapter: RatesAdapter

    @Inject
    @InjectPresenter
    lateinit var ratesPresenter: RatesPresenter

    @ProvidePresenter
    fun provideRatesPresenter() = ratesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rates)

        init()
    }

    private fun init() {

        rates_sr_searchRates.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    ratesPresenter.setFilterQuery(query = it)
                }
                return false
            }

        })

        ratesAdapter = RatesAdapter(ratesPresenter = ratesPresenter)
        rates_rv_list.apply {
            adapter = ratesAdapter
            layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
        }

        rates_swipeRefreshLayout.setOnRefreshListener {
            ratesPresenter.updateRates()
        }
    }

    override fun startRefreshing() {
        rates_swipeRefreshLayout.isRefreshing = true
    }

    override fun stopRefreshing() {
        rates_swipeRefreshLayout.isRefreshing = false
    }

    override fun setRates(rates: List<CurrencyLocal>) {
        ratesAdapter.setupRates(ratesList = rates)
    }

    override fun filterRates(query: String) {
        ratesAdapter.filter(query = query)
    }

    override fun startConvertWindow(currencyLocal: CurrencyLocal) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        supportFragmentManager.findFragmentByTag(ConverterDialogFragment.TAG)?.let {
            fragmentTransaction.remove(it)
        }
        ConverterDialogFragment.newInstance(currencyLocal)
            .show(supportFragmentManager, ConverterDialogFragment.TAG)
    }

    override fun showError(error: Int) {
        Toast.makeText(applicationContext, getString(error), Toast.LENGTH_LONG).show()
    }
}

