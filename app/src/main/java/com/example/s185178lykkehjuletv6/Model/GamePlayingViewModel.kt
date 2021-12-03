package com.example.s185178lykkehjuletv6.Model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.s185178lykkehjuletv6.Words.Capitals
import com.example.s185178lykkehjuletv6.Words.Countries
import com.example.s185178lykkehjuletv6.Words.categories
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

/**
 * ViewModel containing the app data and methods to process the data
 */
//View model taken inspiration from:
// https://github.com/google-developer-training/android-basics-kotlin-unscramble-app/blob/main/app/src/main/java/com/example/android/unscramble/ui/game/GameViewModel.kt
class GamePlayingViewModel : ViewModel() {

    private var _Winner = false
    private lateinit var WordToFind: String
    private lateinit var categoryList : List<String>
    lateinit var categoryString : String
    lateinit var guessWord : String

    // Setting the users start values
    private val _lives = MutableLiveData(5)
    val lives: LiveData<Int>
        get() = _lives

    private val _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score


    private var wordsList: MutableList<String> = mutableListOf()
    init {
        WordToBeGuessed()
    }

    // Calling the catagories to randomize words
    private fun randomCategory(){
        val category = categories.random()
        when (category){
            "Countries" -> categoryList = Countries
            "Capitals" -> categoryList = Capitals
        }
        categoryString = category
    }
    //Calling the random word from the chosen catagory
    private fun WordToBeGuessed() {
        randomCategory()
        WordToFind = categoryList.random()

        val builder = StringBuilder()
       /* for (i in WordToFind.indices){
            builder.append("-")
        }*/
        guessWord = builder.toString()
    }

    // Intial values when round starts
    fun StartGame() {
        startLifeScore(5)
        _score.value = 0
        wordsList.clear()
        _Winner = FALSE

        WordToBeGuessed()
        println("")
    }

    fun LetterGuessed(inputLetter : Char) :Boolean{
        if(WordToFind.contains(inputLetter)){
            showLetter(inputLetter)

            if(guessWord.equals(WordToFind)){
                _Winner = TRUE
            }
            return true
        }
        LifeScore(-1)
        return false
    }

    //Taken inspiration from a Dice roll simulation we made earlier this semester
    fun WheelSimulation(isPlayerBankrupt : Boolean) {

        if(isPlayerBankrupt == TRUE){
            _score.value = 0
        }
        else {
            val wheel = (1..9).random()
            // Defining the random values the wheel can land on
            when (wheel) {
                1 -> _score.value = (_score.value)?.plus(200)
                2 -> _score.value = (_score.value)?.plus(400)
                3 -> _score.value = (_score.value)?.plus(600)
                4 -> _score.value = (_score.value)?.plus(1000)
                5 -> _score.value = (_score.value)?.plus(1200)
                6 -> _score.value = (_score.value)?.plus(1400)
                7 -> _score.value = (_score.value)?.plus(1600)
                8 -> _score.value = (_score.value)?.plus(1800)
                9 -> _score.value = (_score.value)?.plus(2000)
            }
        }
    }


    fun LifeScore(amount: Int){
        _lives.value =(_lives.value)?.plus(amount)
    }

    fun startLifeScore(amount: Int){
        _lives.value = amount
    }


    fun spinWheel() : String {
        val rand = (0.. spinWheelTypes.values().size-1).random()
        val wheelResult = spinWheelTypes.values()[rand]

        when (wheelResult){
            spinWheelTypes.INCREASE_SCORE -> WheelSimulation(FALSE)
            spinWheelTypes.BANKRUPT       -> WheelSimulation(TRUE)
            spinWheelTypes.INCREASE_LIFE -> LifeScore(1)
            spinWheelTypes.DECREASE_LIFE  -> LifeScore(-1)
        }

        return wheelResult.description
    }

    private fun showLetter(playerLetter: Char) {
        for (i in WordToFind.indices){
            if (WordToFind[i].uppercase() == playerLetter.uppercase()){
                guessWord = guessWord.replaceRange(i,i+1,playerLetter.toString())
            }
        }
    }

    fun wordisGuessed() : Boolean{
        if(guessWord.uppercase() == WordToFind.uppercase()){
            return true
        }
        return false
    }

}

enum class spinWheelTypes(val description : String){
    INCREASE_SCORE("Du får flere point!"),
    INCREASE_LIFE ("Du har fået et ekstra liv"),
    BANKRUPT ("Du er gået fallit"),
    DECREASE_LIFE("Du har mistet et liv")
}


    /*private val _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score

    private var _lives = MutableLiveData(5)
    val lives: LiveData<Int>
        get() = _lives


    private val _currentWordCount = MutableLiveData(0)
    val currentWordCount: LiveData<Int>
        get() = _currentWordCount

    private val _currentScrambledWord = MutableLiveData<String>()
    val currentScrambledWord: LiveData<Spannable> = Transformations.map(_currentScrambledWord) {
        if (it == null) {
            SpannableString("")
        } else {
            val scrambledWord = it.toString()
            val spannable: Spannable = SpannableString(scrambledWord)
            spannable.setSpan(
                TtsSpan.VerbatimBuilder(scrambledWord).build(),
                0,
                scrambledWord.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            spannable
        }
    }

    // List of words used in the game
    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    init {
        getNextWord()
    }

    *//*
     * Updates currentWord and currentScrambledWord with the next word.
     *//*
    private fun getNextWord() {
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        while (String(tempWord).equals(currentWord, false)) {
            tempWord.shuffle()
        }
        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            Log.d("Unscramble", "currentWord= $currentWord")
            _currentScrambledWord.value = String(tempWord)
            _currentWordCount.value = _currentWordCount.value?.inc()
            wordsList.add(currentWord)
        }
    }

    *//*
     * Re-initializes the game data to restart the game.
     *//*
    fun reinitializeData() {
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }

    *//*
    * Increases the game score if the player’s word is correct.
    *//*
    private fun increaseScore() {
        _score.value = _score.value?.plus(SCORE_INCREASE)
    }

    *//*
    * Returns true if the player word is correct.
    * Increases the score accordingly.
    *//*
    fun isLetterGussedCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

    *//*
    * Returns true if the current word count is less than MAX_NO_OF_WORDS
    *//*
    fun nextWord(): Boolean {
        return if (_currentWordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }
}*/