package com.example.s185178lykkehjuletv6.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.s185178lykkehjuletv6.R
import android.widget.Button
import androidx.navigation.fragment.findNavController

class start_screen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.start_screen_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val clickListener = View.OnClickListener {
            StartGame()
        }
        val button = view.findViewById<Button>(R.id.play)
        button.setOnClickListener(clickListener)
    }
    private fun StartGame(){
        // Navigate to Game
        findNavController().navigate(R.id.action_start_screen_to_game_playing)
    }
}