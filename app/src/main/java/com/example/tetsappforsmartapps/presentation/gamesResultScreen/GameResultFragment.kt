package com.example.tetsappforsmartapps.presentation.gamesResultScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tetsappforsmartapps.R
import com.example.tetsappforsmartapps.databinding.FragmentGameResultBinding

class GameResultFragment : Fragment() {

    private var _binding: FragmentGameResultBinding? = null
    private val binding: FragmentGameResultBinding
        get() = _binding ?: throw RuntimeException("FragmentGameResultBinding == null")

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private val args by navArgs<GameResultFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvScore.text = "${getString(R.string.your_score)} ${args.score}"
        binding.buttonRetry.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}