package com.example.s185178lykkehjuletv6.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.s185178lykkehjuletv6.model.GamePlayingViewModel
import com.example.s185178lykkehjuletv6.R
import com.example.s185178lykkehjuletv6.databinding.GamePlayingFragmentBinding
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

// Code inspired by https://developer.android.com/codelabs/basic-android-kotlin-training-livedata#0 &
//// https://github.com/google-developer-training/android-basics-kotlin-unscramble-app/blob/main/app/src/main/java/com/example/android/unscramble/ui/game/GameViewModel.kt
class Gameplaying : Fragment() {
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
                    viewModel.letterGuessed(letter)
                }
                _guessTheWord = TRUE
                updatescoreandlifecount()
            }
        }
        updatescoreandlifecount()
    }

// Code inspired from https://developer.android.com/codelabs/kotlin-android-training-live-data#3
    @SuppressLint("SetTextI18n")
    private fun updatescoreandlifecount() {
        binding.pointScore.text = viewModel.score.value.toString()
        binding.WheelText.text = viewModel.spinThatWheel()
        (viewModel.lives.value.toString()).also { binding.lifeScore.text = it }
        binding.word.text = viewModel.guessWord
        binding.catagory.text = viewModel.categoryString
        gameOver()

//Implemented a double button instead https://developer.android.com/guide/topics/ui/controls/togglebutton
    // import to stop issue with button text https://developer.android.com/reference/android/annotation/SuppressLint
        if (_guessTheWord) {
            binding.Guess.visibility = View.INVISIBLE
            binding.Guess.visibility = View.VISIBLE
            binding.Guess.text = "Spin the wheel"

        } else {
            binding.Guess.visibility = View.VISIBLE
            binding.Guess.text = "Guess Letter"
        }
    }

    private fun winner() {
        findNavController().navigate(R.id.action_game_playing_to_game_won)
    }

 // Restart game and then call StartGame to reset values
    private fun restartGame() {
        viewModel.startGame()
        updatescoreandlifecount()
    }
// Kept it because it was in the example https://developer.android.com/codelabs/kotlin-android-training-live-data#0
    private fun exitGame() {
        activity?.finish()
    }

    private fun gameOver() {
        if (viewModel.lives.value!! <= 0) {
            findNavController().navigate(R.id.action_game_playing_to_game_lost)
        }
        if (viewModel.wordIsGuessed()) {
            winner()
        }
    }
}
/*

*/
//Code where there have been taken inspiration from https://developer.android.com/codelabs/kotlin-android-training-live-data#0
/**
 * Fragment where the game is played, contains the game logic.
 *//*

class GameFragment : Fragment() {

    // Binding object instance with access to the views in the game_fragment.xml layout
    private lateinit var binding: GameFragmentBinding

    // Create a ViewModel the first time the fragment is created.
    // If the fragment is re-created, it receives the same GameViewModel instance created by the
    // first fragment.
    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout XML file and return a binding object instance
        binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the viewModel for data binding - this allows the bound layout access
        // to all the data in the VieWModel
        binding.gameViewModel = viewModel
        binding.maxNoOfWords = MAX_NO_OF_WORDS
        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = viewLifecycleOwner

        // Setup a click listener for the Submit and Skip buttons.
        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }
    }

    */
/*
    * Checks the user's word, and updates the score accordingly.
    * Displays the next scrambled word.
    * After the last word, the user is shown a Dialog with the final score.
    *//*

    private fun onSubmitWord() {
        val playerWord = binding.textInputEditText.text.toString()

        if (viewModel.isUserWordCorrect(playerWord)) {
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
     * Skips the current word without changing the score.
     * Increases the word count.
     * After the last word, the user is shown a Dialog with the final score.
     *//*

    private fun onSkipWord() {
        if (viewModel.nextWord()) {
            setErrorTextField(false)
        } else {
            showFinalScoreDialog()
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
*/
