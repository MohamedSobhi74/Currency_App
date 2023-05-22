package com.project.currencyapp.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.project.currencyapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        destinationObserve()
        return root
    }

    private fun destinationObserve() {

        viewModel.navigateDestination.observe(viewLifecycleOwner) {
            it?.let {
                var bundle = Bundle()
                bundle.putString("base",viewModel.fromCurrency.value.toString())
                bundle.putString("symbols",viewModel.toCurrency.value.toString())
                    NavHostFragment.findNavController(this).navigate(it,bundle) }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}