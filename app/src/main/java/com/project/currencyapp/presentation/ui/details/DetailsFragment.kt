package com.project.currencyapp.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.project.currencyapp.data.source.repository.currencyRepository
import com.project.currencyapp.data.source.repository.detailsRepository
import com.project.currencyapp.databinding.FragmentDetailsBinding
import com.project.currencyapp.presentation.ui.home.HomeViewModel

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by viewModels {
        DetailsViewModel.Factory(detailsRepository)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val bundle = arguments
     /*   viewModel.base = bundle!!.getString("base")
        viewModel.symbols = bundle!!.getString("symbols")*/

     /*   binding.baseTv.text = "AED"
        binding.symbolTv.text = "EGP"*/
        viewModel.base.value = "AED"
        viewModel.symbols.value = "EGP"
        viewModel.getConvertHistory()


        return _binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}