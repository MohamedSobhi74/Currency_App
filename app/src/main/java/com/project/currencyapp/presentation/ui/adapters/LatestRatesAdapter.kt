package com.project.currencyapp.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.currencyapp.databinding.HistoryItemBinding
import com.project.currencyapp.domain.models.latest.LatestRatesResponse

class LatestRatesAdapter(private val latestRatesResponse: LatestRatesResponse) :
    RecyclerView.Adapter<LatestRatesAdapter.ActorViewHolder>() {

    var base = ""

    init {
        base = latestRatesResponse.base
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ActorViewHolder(
            HistoryItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        val date = latestRatesResponse.rates.keys.toList().get(position)
        val symbol = latestRatesResponse.rates.keys.toList().get(position)
        val value = latestRatesResponse.rates.values.toList().get(position)
        holder.binding.date = date
        holder.binding.symbol = symbol
        holder.binding.value = value.toString()
    }

    override fun getItemCount(): Int {
        return latestRatesResponse.rates.size
    }

    class ActorViewHolder
    constructor(
        val binding: HistoryItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

    }
}

