package com.hackerman.currencyapp.features.details.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hackerman.currencyapp.common.extension.hideProgressBar
import com.hackerman.currencyapp.common.extension.showErrorSnackBar
import com.hackerman.currencyapp.common.extension.showProgressBar
import com.hackerman.currencyapp.common.resource.Resource
import com.hackerman.currencyapp.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsDetailViewModel by viewModels()
    private lateinit var fromCurrency: String
    private lateinit var toCurrency: String
    private lateinit var currentDate: String
    private lateinit var threeDaysAgoDate: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fromCurrency = DetailsFragmentArgs.fromBundle(requireArguments()).fromCurrency ?: ""
        toCurrency = DetailsFragmentArgs.fromBundle(requireArguments()).toCurrency ?: ""
        viewModel.getHistoricData(fromCurrency, toCurrency)
        viewModel.getPopularCurrencies(fromCurrency)
        getHistoricDataSubscriber()
        getPopularCurrenciesSubscriber()

    }


    private fun getHistoricDataSubscriber() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getHistoricData.collectLatest {
                when (it) {
                    is Resource.Success -> {
                        hideProgressBar(binding.fragmentDetailsPb)
                        val dates = it.data.rates?.keys?.toList()?.sorted()?.reversed()
                        val map = it.data.rates
                        binding.fragmentDetailsDate1Tv.text = "Date: ${dates?.get(0)}"
                        binding.fragmentDetailsRate1Tv.text =
                            "Rate: ${map?.get(dates?.get(0))?.get(toCurrency)}"

                        binding.fragmentDetailsDate2Tv.text = "Date: ${dates?.get(1)}"
                        binding.fragmentDetailsRate2Tv.text =
                            "Rate: ${map?.get(dates?.get(1))?.get(toCurrency)}"

                        binding.fragmentDetailsDate3Tv.text = "Date: ${dates?.get(2)}"
                        binding.fragmentDetailsRate3Tv.text =
                            "Rate: ${map?.get(dates?.get(2))?.get(toCurrency)}"


                    }
                    is Resource.Loading -> {
                        showProgressBar(binding.fragmentDetailsPb)
                    }
                    is Resource.Error -> {
                        hideProgressBar(binding.fragmentDetailsPb)
                        showErrorSnackBar(binding.fragmentDetails, it.message)
                    }
                }
            }
        }
    }


    private fun getPopularCurrenciesSubscriber() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getPopularCurrenciesResponse.collectLatest {
                when (it) {
                    is Resource.Success -> {
                        val keys = it.data.rates.keys.toList()
                        val max = if (keys.size > 10) 10 else keys.size
                        var popularCurrencies = ""

                        for (i in 0..max) {
                            popularCurrencies += "${keys[i]} : ${it.data.rates[keys[i]]}"
                            popularCurrencies += "\n"
                        }
                        binding.fragmentDetailsPopularCurrenciesTv.text = popularCurrencies
                    }
                    is Resource.Loading -> {
                        showProgressBar(binding.fragmentDetailsPb)
                    }
                    is Resource.Error -> {
                        hideProgressBar(binding.fragmentDetailsPb)
                        showErrorSnackBar(binding.fragmentDetails, it.message)
                    }
                }
            }
        }
    }


}