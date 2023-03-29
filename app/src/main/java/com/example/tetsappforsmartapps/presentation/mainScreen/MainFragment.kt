package com.example.tetsappforsmartapps.presentation.mainScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tetsappforsmartapps.R
import com.example.tetsappforsmartapps.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding == null")

    private lateinit var viewModel: MainViewModel

    private var requestCountryName = ""

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.getButton.setOnClickListener {
            requestCountryName = binding.nameOfCountryET.text.toString()
            viewModel.makeRequest(requestCountryName)
        }
        viewModel.result.observe(viewLifecycleOwner) {
            binding.resultTextView.text = it
        }

        viewModel.getFirstLoadedCountry().observe(viewLifecycleOwner) {
            if (viewModel.isEmpty) {
                if (it != null) {
                    binding.savedResultTextView.text = it[0].name.common
                }
            }
        }
        binding.toWebViewButton.setOnClickListener {
            setUpWebViewFragment()
        }
        binding.toGameButton.setOnClickListener {
            setUpLevelFragment()
        }
    }

    private fun setUpWebViewFragment() {
        findNavController().navigate(R.id.action_mainFragment_to_webViewFragment)
    }

    private fun setUpLevelFragment() {
        findNavController().navigate(R.id.action_mainFragment_to_levelFragment)
    }
}