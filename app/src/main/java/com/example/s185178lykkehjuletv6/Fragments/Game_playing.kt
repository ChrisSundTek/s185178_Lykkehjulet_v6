package com.example.s185178lykkehjuletv6.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil.*
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.s185178lykkehjuletv6.Model.GamePlayingViewModel
import com.example.s185178lykkehjuletv6.R
import com.example.s185178lykkehjuletv6.databinding.GamePlayingFragmentBinding
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

// Code inspired by https://developer.android.com/codelabs/basic-android-kotlin-training-livedata#0 &
//// https://github.com/google-developer-training/android-basics-kotlin-unscramble-app/blob/main/app/src/main/java/com/example/android/unscramble/ui/game/GameViewModel.kt
class Game_playing : Fragment() {
    private var _guessTheWord = FALSE

    private lateinit var binding: GamePlayingFragmentBinding
    private val viewModel: GamePlayingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = GamePlayingFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.Guess.setOnClickListener {
            binding.inputField.text.toString()
        }

        binding.Guess.setOnClickListener {

            if (_guessTheWord) {
                _guessTheWord = FALSE
                updatescoreandlifecount()
            } else {
                val unknownWord: String = binding.inputField.text.toString()

// .isNotEmpty taken from https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/is-not-empty.html
                if (unknownWord.isNotEmpty() && unknownWord.length == 1) {

                    val letter: Char = unknownWord.first()
                    viewModel.LetterGuessed(letter)
                }
                _guessTheWord = TRUE
                updatescoreandlifecount()
            }
        }
        updatescoreandlifecount()
    }

// Code inspired from https://developer.android.com/codelabs/kotlin-android-training-live-data#3
    private fun updatescoreandlifecount() {
        binding.pointScore.text = viewModel.score.value.toString()
        binding.WheelText.text = viewModel.spinWheel()
        (viewModel.lives.value.toString()).also { binding.lifeScore.text = it }
        //(viewModel.score.value.toString()).also { binding.pointScore.text = it }
        binding.word.text = viewModel.guessWord
        binding.catagory.text = viewModel.categoryString
        GameOver()

//Implemented a double button instead https://developer.android.com/guide/topics/ui/controls/togglebutton
        if (_guessTheWord) {
            binding.Guess.visibility = View.INVISIBLE
            binding.Guess.visibility = View.VISIBLE
            binding.Guess.text = "Spin the wheel"

        } else {
            binding.Guess.visibility = View.VISIBLE
            binding.Guess.text = "Guess Letter"
        }
    }

    private fun Winner() {
        findNavController().navigate(R.id.action_game_playing_to_game_won)
    }

 // Restart game and then call StartGame to reset values
    private fun restartGame() {
        viewModel.StartGame()
        updatescoreandlifecount()
    }

    private fun exitGame() {
        activity?.finish()
    }

    private fun GameOver() {
        if (viewModel.lives.value!! <= 0) {
            findNavController().navigate(R.id.action_game_playing_to_game_lost)
        }
        if (viewModel.wordIsGuessed()) {
            Winner()
        }
    }
}