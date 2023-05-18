package com.project.currencyapp.presentation.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.project.currencyapp.data.source.repository.currencyRepository
import com.project.currencyapp.databinding.FragmentHomeBinding
import com.project.currencyapp.domain.models.common.ResponseWrapper

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModel.Factory(currencyRepository)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.currencySymbolsResponseLiveData.observe(viewLifecycleOwner) {
            if (it is ResponseWrapper.Success) {
                initFromDropdown(it.value.symbols.keys)
                initToDropdown(it.value.symbols.keys)
                //  Log.d("TAG", "onCreateView: "+it.value.symbols.v.get("IDR"))
            } else {
                Log.d("TAG", "onCreateView: "+it.toString())
            }
        }

        viewModel.getCurrencySymbols()

        return root
    }

    private fun initFromDropdown(it: Set<String>) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            it.toTypedArray()
        )
        (binding.fromMenu.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun initToDropdown(it: Set<String>) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            it.toTypedArray()
        )
        (binding.toMenu.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}