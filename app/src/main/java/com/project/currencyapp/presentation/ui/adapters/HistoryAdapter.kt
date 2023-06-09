package com.project.currencyapp.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.currencyapp.databinding.HistoryItemBinding
import com.project.currencyapp.domain.models.history.HistoryRatesResponse

class HistoryAdapter(private val historyResponse: HistoryRatesResponse) :
    RecyclerView.Adapter<HistoryAdapter.ActorViewHolder>() {

    var base = ""

    init {
        base = historyResponse.base
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
        val date = historyResponse.rates.keys.toList().get(position)
        val symbol = historyResponse.rates.values.toList().get(position).keys.toList().get(0)
        val value = historyResponse.rates.values.toList().get(position).values.toList().get(0)
        holder.binding.date = date
        holder.binding.symbol = symbol
        holder.binding.value = value.toString()
    }

    override fun getItemCount(): Int {
        return historyResponse.rates.size
    }

    class ActorViewHolder
    constructor(
        val binding: HistoryItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

    }
}

