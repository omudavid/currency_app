package com.hackerman.currencyapp.features.currencyconverter.presentation

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.hackerman.currencyapp.R
import com.hackerman.currencyapp.common.extension.hideProgressBar
import com.hackerman.currencyapp.common.extension.showErrorSnackBar
import com.hackerman.currencyapp.common.extension.showProgressBar
import com.hackerman.currencyapp.common.resource.Resource
import com.hackerman.currencyapp.databinding.FragmentCurrencyConverterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrencyConverterFragment : Fragment() {


    private var _binding: FragmentCurrencyConverterBinding? = null
    private val binding get() = _binding!!
    private var fromCurrencySpinnerIndex = 0
    private var toCurrencySpinnerIndex = 0
    private var toCurrency = ""
    private var fromCurrency = ""
    lateinit var symbols: List<String>


    private val viewModel: CurrencyConverterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCurrencyConverterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCurrencySymbolSubscriber()
        fromCurrencySelected()
        toCurrencySelected()
        attachSwapButtonClick()
        fromCurrencyInputChanged()
        getConvertedCurrencySubscriber()
        attachDetailsButtonClick()
    }


    private fun attachDetailsButtonClick() {
        binding.fragmentCurrencyConverterDetailsBtn.setOnClickListener {
            if (toCurrency.isNotEmpty() && fromCurrency.isNotEmpty()) {
                val action =
                    CurrencyConverterFragmentDirections.actionCurrencyConverterFragmentToDetailsFragment(
                        fromCurrency,
                        toCurrency
                    )
                findNavController().navigate(action)
            }
        }
    }

    private fun attachSwapButtonClick() {
        binding.fragmentCurrencyConverterArrowIv.setOnClickListener {
            if (toCurrency.isNotEmpty() && fromCurrency.isNotEmpty()) {
                val newFromIndex = toCurrency
                val newToIndex = fromCurrency
                toCurrency = newToIndex
                fromCurrency = newFromIndex
                binding.fragmentCurrencyConverterFromAtv.setText(newFromIndex)
                binding.fragmentCurrencyConverterToAtv.setText(newToIndex)

                setUpCurrencySymbols(symbols)
                binding.fragmentCurrencyConverterFromAtv.dismissDropDown()
                binding.fragmentCurrencyConverterToAtv.dismissDropDown()

                getConvertedCurrency()
            }
        }
    }


    private fun fromCurrencySelected() {
        binding.fragmentCurrencyConverterFromAtv.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                fromCurrencySpinnerIndex = parent.selectedItemPosition + 1
                fromCurrency = parent.getItemAtPosition(position).toString()
                val target = binding.fragmentCurrencyConverterToAtv.text.toString()
                if (target.isNotEmpty()) {
                    getConvertedCurrency()
                }
            }
    }


    private fun toCurrencySelected() {
        binding.fragmentCurrencyConverterToAtv.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                toCurrencySpinnerIndex = parent.selectedItemPosition + 1
                toCurrency = parent.getItemAtPosition(position).toString()
                val base = binding.fragmentCurrencyConverterFromAtv.text.toString()
                if (base.isNotEmpty()) {
                    getConvertedCurrency()
                }
            }
    }


    private fun getConvertedCurrency() {
        if (toCurrency.isNotEmpty() && fromCurrency.isNotEmpty()) {
            val amount = binding.fragmentCurrencyConverterFromValueEt.text.toString()
            viewModel.getConvertedCurrency(fromCurrency, toCurrency, amount)
        }
    }

    private fun fromCurrencyInputChanged() {
        binding.fragmentCurrencyConverterFromValueEt.addTextChangedListener(object : TextWatcher {
            var debounceTimer: CountDownTimer? = null

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                debounceTimer?.cancel()
                debounceTimer = object : CountDownTimer(1000, 1500) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        getConvertedCurrency()
                    }
                }.start()
            }
        })
    }


    private fun getCurrencySymbolSubscriber() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getSymbolsResponse.collectLatest {
                when (it) {
                    is Resource.Success -> {
                        hideProgressBar(binding.fragmentCurrencyConverterPb)
                        it.data.symbols?.keys?.let { it1 -> setUpCurrencySymbols(it1.toList()) }
                        it.data.symbols?.keys?.let { it1 -> symbols = it1.toList() }

                    }
                    is Resource.Loading -> {
                        showProgressBar(binding.fragmentCurrencyConverterPb)
                    }
                    is Resource.Error -> {
                        hideProgressBar(binding.fragmentCurrencyConverterPb)
                        showErrorSnackBar(binding.fragmentCurrencyConverter, it.message)
                    }
                }
            }
        }
    }

    private fun getConvertedCurrencySubscriber() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getConvertedCurrencyResponse.collectLatest {
                when (it) {
                    is Resource.Success -> {
                        hideProgressBar(binding.fragmentCurrencyConverterPb)
                        binding.fragmentCurrencyConverterToValueEt.setText(it.data.result.toString())

                    }
                    is Resource.Loading -> {
                        showProgressBar(binding.fragmentCurrencyConverterPb)
                    }
                    is Resource.Error -> {
                        hideProgressBar(binding.fragmentCurrencyConverterPb)
                        showErrorSnackBar(binding.fragmentCurrencyConverter, it.message)
                    }
                }
            }
        }
    }

    private fun setUpCurrencySymbols(list: List<String>) {
        populateSpinner(
            list,
            binding.fragmentCurrencyConverterFromAtv as MaterialAutoCompleteTextView
        )
        populateSpinner(
            list,
            binding.fragmentCurrencyConverterToAtv as MaterialAutoCompleteTextView
        )
    }

    private fun populateSpinner(list: List<String>, view: MaterialAutoCompleteTextView) {
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.spinner_list_item, list)
        view.setAdapter(arrayAdapter)
    }


}