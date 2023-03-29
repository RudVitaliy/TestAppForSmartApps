package com.example.tetsappforsmartapps.presentation.gameScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tetsappforsmartapps.databinding.FragmentGameBinding

class GameFragment: Fragment()  {

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    private lateinit var gameView: GameView

    private lateinit var viewModel: GameViewModel

    private val args by navArgs<GameFragmentArgs>()

    private val viewModelFactory by lazy {
        GameViewModelFactory(
            args.level,
            requireActivity().application
        )
    }

    private var screenHeight: Int = 0
    private var screenWidth: Int = 0
    private var score = 0

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        gameView = binding.gameView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
        getScreenMetrics()
        gameView.setViewModel(viewModel)
        viewModel.startGame(screenWidth, screenHeight)
        viewModel.formattedTime.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
            viewModel.update()
        }
        viewModel.score.observe(viewLifecycleOwner) {
            binding.tvScore.text = it.toString()
            score = it
        }
        viewModel.running.observe(viewLifecycleOwner) {
            if(it == false) {
                startGameResultFragment()
            }
        }
    }

    private fun startGameResultFragment() {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameResultFragment(score)
        )
    }

    private fun getScreenMetrics() {
        val displayMetrics = resources.displayMetrics
        screenWidth = displayMetrics.widthPixels
        screenHeight = displayMetrics.heightPixels
    }


}