package com.example.tetsappforsmartapps.presentation.levelScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tetsappforsmartapps.databinding.FragmentLevelBinding
import com.example.tetsappforsmartapps.domain.entity.Level

class LevelFragment : Fragment() {

    private var _binding: FragmentLevelBinding? = null
    private val binding: FragmentLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentLevelBinding == null")


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){

            buttonLevelTest.setOnClickListener {
                setUpGameFragment(Level.TEST)
            }
            buttonLevelEasy.setOnClickListener {
                setUpGameFragment(Level.EASY)
            }
            buttonLevelNormal.setOnClickListener {
                setUpGameFragment(Level.NORMAL)
            }
            buttonLevelHard.setOnClickListener {
                setUpGameFragment(Level.HARD)
            }
        }
    }

    private fun setUpGameFragment(level: Level) {
        findNavController().navigate(
            LevelFragmentDirections.actionLevelFragmentToGameFragment(level)
        )
    }
}