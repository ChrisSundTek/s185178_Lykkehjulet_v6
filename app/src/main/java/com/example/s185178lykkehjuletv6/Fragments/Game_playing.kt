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
import com.google.android.material.snackbar.Snackbar
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
            val unknownWord: String = binding.inputField.text.toString()
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


    private fun updatescoreandlifecount() {
        (viewModel.lives.value.toString()).also { binding.lifeScore.text = it }
        (viewModel.score.value.toString()).also { binding.pointScore.text = it }
        binding.word.text = viewModel.guessWord
        binding.catagory.text = viewModel.categoryString
        GameOver()

//Implemented a double button instead https://developer.android.com/guide/topics/ui/controls/togglebutton
        if (_guessTheWord) {
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

 /*   *//**
     * Used to restart the game
     *//*
    private fun restartGame() {
        viewModel.StartGame()
        updatescoreandlifecount()
    }*/

    private fun exitGame() {
        activity?.finish()
    }

    private fun GameOver() {
        if (viewModel.lives.value!! <= 0) {
            findNavController().navigate(R.id.action_game_playing_to_game_lost)
        }
        if (viewModel.wordisGuessed()) {
            Winner()
        }


    }
}

/*
    private var _canIGuess = FALSE

    // Binding object instance with access to the views in the game_fragment.xml layout
    private lateinit var binding: GamePlayingFragmentBinding
    private val viewModel: GamePlayingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = inflate(inflater, R.layout.game_playing_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.guessWordButton.setOnClickListener {
            val word : String = binding.inputField.text.toString()

            if(word.isNotEmpty()){

                if(viewModel.isUserWordCorrect(word)){
                    showFinalScoreDialog()
                }
                _canIGuess = TRUE
                update()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup a click listener for the Submit and Skip buttons.
        binding.spinner.setOnClickListener { SimulateWheel() }
        binding.spinner.setOnClickListener { onLetterGuessed() }
    }

    private fun SimulateWheel() {
        //Simulation is sat up like a dice roll (inspiration from class)
        val wheel = Wheel(10)
        val wheelSpin = wheel.spin()

        val changingTextView: TextView = findViewById(R.id.Wheel_result)
        changingTextView.text = wheel.spin.toString()


    }

    class Wheel(private val numsides: Int) {
        fun spin(): Int {
            return (1..numsides).random()
        }

    private fun onLetterGuessed() {
        val playerWord = binding.inputField.text.toString()

        if (viewModel.isLetterGussedCorrect(playerWord)) {
            setErrorTextField(false)
            if (!viewModel.nextWord()) {
                showFinalScoreDialog()
            }
        } else {
            setErrorTextField(true)
        }
    }

    */
/*
     * Creates and shows an AlertDialog with final score.
     *//*

    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.congratulations))
            .setMessage(getString(R.string.you_scored, viewModel.score.value))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.exit)) { _, _ ->
                exitGame()
            }
            .setPositiveButton(getString(R.string.play_again)) { _, _ ->
                restartGame()
            }
            .show()
    }

    */
/*
     * Re-initializes the data in the ViewModel and updates the views with the new data, to
     * restart the game.
     *//*

    private fun restartGame() {
        viewModel.reinitializeData()
        setErrorTextField(false)
    }

    */
/*
     * Exits the game.
     *//*

    private fun exitGame() {
        activity?.finish()
    }

    */
/*
    * Sets and resets the text field error status.
    *//*

    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textField.isErrorEnabled = true
            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.isErrorEnabled = false
            binding.textInputEditText.text = null
        }
    }
}
    }

}*/
