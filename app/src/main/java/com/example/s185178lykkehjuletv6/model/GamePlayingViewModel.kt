package com.example.s185178lykkehjuletv6.model

/* import android.widget.TextView */
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.lang.Boolean.*
import com.example.s185178lykkehjuletv6.words.Capitals
import com.example.s185178lykkehjuletv6.words.Countries
import com.example.s185178lykkehjuletv6.words.categories


//View model taken inspiration from:
// https://github.com/google-developer-training/android-basics-kotlin-unscramble-app/blob/main/app/src/main/java/com/example/android/unscramble/ui/game/GameViewModel.kt
class GamePlayingViewModel : ViewModel() {

    private lateinit var wordToFind: String
    private lateinit var categoryList : List<String>
    lateinit var categoryString : String
    lateinit var guessWord : String
    private var _winning = false

    // Setting the users start values
    private val _lifecount = MutableLiveData(5)
    val lives: LiveData<Int>
        get() = _lifecount

    private val _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score

    private var wordsList: MutableList<String> = mutableListOf()
    init {
        wordToBeGuessed()
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
    private fun wordToBeGuessed() {
        randomCategory()
        wordToFind = categoryList.random()

//String builder https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/-string-builder/
        val builder = StringBuilder()
        for (i in wordToFind.indices){
            builder.append("-")
        }
        guessWord = builder.toString()
    }

    // Intial values when round starts
    fun startGame() {
        startLifeScore(5)
        _score.value = 0
        wordsList.clear()
        _winning = FALSE
        wordToBeGuessed()

        println("")
    }

    //Defining Lifescore start
    private fun startLifeScore(value: Int){
        _lifecount.value = value
    }

    //Making a lifescore class to update
    private fun lifeScore(value: Int){
        _lifecount.value =(_lifecount.value)?.plus(value)
    }

    //Implemented the different states a guess can have and the results from that
    fun letterGuessed(inputLetter : Char) :Boolean{
        if(wordToFind.contains(inputLetter)){
            showLetter(inputLetter)

            if(guessWord == wordToFind){
                _winning = TRUE
            }
            return true
        }
        lifeScore(-1)
        return false
    }

    //Taken inspiration from a Dice roll simulation we made earlier this semester
    //Plus help from https://developer.android.com/reference/kotlin/java/lang/Boolean &
    // https://kotlinlang.org/docs/keyword-reference.html#special-identifiers
    //Added true false state to add bankrupt to the wheel
    private fun wheelScoreSimulation(landedBankrupt : Boolean) {
        val wheel = (1..9).random()
        if(landedBankrupt == TRUE){
            _score.value = 0
        }
        else {
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
                //10 -> _lifecount.value =(_lifecount.value)?.plus(-1)
                //11 -> _lifecount.value = (_lifecount.value)?.plus(+1)
                //Implemented life count update to the spinThatWheel
            }
        }
    }

    /*class Dice(val numSides: Int) {
        fun rolling(): Int {
            return (1..numSides).random()
        }
    }*/
    //Code above have been used to inspire spinThatWheel (code is from dice assingment made earlier this semester)
    //Would have liked to get all 9 values above worked in together with this but haven't managed
    fun spinThatWheel() : String {
        val randomizer = (WheelResult.values().indices).random()
        val wheelResult = WheelResult.values()[randomizer]

        when (wheelResult){
            WheelResult.PLUS_LIFE -> lifeScore(1)
            WheelResult.MINUS_LIFE  -> lifeScore(-1)
            WheelResult.SCORE -> wheelScoreSimulation(FALSE)
            WheelResult.BANKRUPT -> wheelScoreSimulation(TRUE)
        }
        return wheelResult.Text
    }

    // Priciple about indices used from https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/indices.html
    // Comparing the letter input with the word there needs be guessed
    private fun showLetter(guessedLetter: Char) {
        for (i in wordToFind.indices){
            if (wordToFind[i] == guessedLetter){
                guessWord = guessWord.replaceRange(i,i+1,guessedLetter.toString())
            }
        }
    }

    //Checking if the whole word is found and then Game_Playing.kt calls it if it is true it calls Winner method
    fun wordIsGuessed() : Boolean{
        if(guessWord == wordToFind){
            return true
        }
        return false
    }
}

//Private class which have the text string there will be showed after wheel have been spun
enum class WheelResult(val Text : String){
    SCORE("+"),
    PLUS_LIFE ("Gained a extra life"),
    MINUS_LIFE("You lost a life"),
    BANKRUPT ("You bankrupt"),
}

// Code taken which have been used as a guideline for this project https://developer.android.com/codelabs/kotlin-android-training-live-data#0
/**
 * ViewModel containing the app data and methods to process the data
 *//*

class GameViewModel : ViewModel() {
    private val _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score

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

    */
/*
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

    */
/*
     * Re-initializes the game data to restart the game.
     *//*

    fun reinitializeData() {
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }

    */
/*
    * Increases the game score if the player’s word is correct.
    *//*

    private fun increaseScore() {
        _score.value = _score.value?.plus(SCORE_INCREASE)
    }

    */
/*
    * Returns true if the player word is correct.
    * Increases the score accordingly.
    *//*

    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

    */
/*
    * Returns true if the current word count is less than MAX_NO_OF_WORDS
    *//*

    fun nextWord(): Boolean {
        return if (_currentWordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }
}
*/
