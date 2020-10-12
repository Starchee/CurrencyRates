package com.starchee.currencyrates.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.starchee.currencyrates.R
import com.starchee.currencyrates.model.local.CurrencyLocal
import com.starchee.currencyrates.presenters.RatesPresenter
import kotlinx.android.synthetic.main.rates_rv_list_item.view.*

class RatesAdapter constructor(
    private val ratesPresenter: RatesPresenter): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var sourceList: ArrayList<CurrencyLocal> = ArrayList()
    private var ratesList: ArrayList<CurrencyLocal> = ArrayList()

    fun setupRates(ratesList: List<CurrencyLocal>) {
        sourceList.clear()
        sourceList.addAll(ratesList.sortedBy { it.charCode })
        filter(query = "")
    }

    fun filter(query: String) {
        ratesList.clear()
        sourceList.forEach {
            if (it.charCode.contains(query, ignoreCase = true)) {
                ratesList.add(it)
            }
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RatesViewHolder) {
            holder.bind(currencyLocal = ratesList[position], ratesPresenter = ratesPresenter)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.rates_rv_list_item, parent, false)
        return RatesViewHolder(itemView = itemView)
    }

    override fun getItemCount(): Int {
        return ratesList.count()
    }

    class RatesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(currencyLocal: CurrencyLocal, ratesPresenter: RatesPresenter) {

            itemView.apply {
                setOnClickListener {
                    ratesPresenter.startConvert(currencyLocal = currencyLocal)
                }

                rates_rv_list_item_tv_charCode.text =  currencyLocal.charCode
                rates_rv_list_item_tv_nominal.text = resources.getString(R.string.nominal, currencyLocal.nominal)
                rates_rv_list_item_tv_name.text = currencyLocal.name
                rates_rv_list_item_tv_value.text = currencyLocal.value.toString()
                if (currencyLocal.difference < 0 ) {
                    rates_rv_list_item_tv_difference.setTextColor(ContextCompat.getColor(context,android.R.color.holo_red_dark))
                } else {
                    rates_rv_list_item_tv_difference.setTextColor(ContextCompat.getColor(context,android.R.color.holo_green_dark))
                }
                rates_rv_list_item_tv_difference.text = currencyLocal.difference.toString()
            }

        }
    }
}