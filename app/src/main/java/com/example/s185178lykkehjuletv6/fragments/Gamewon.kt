package com.example.s185178lykkehjuletv6.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.s185178lykkehjuletv6.R

class Gamewon : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.game_won_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val clickListener = View.OnClickListener {
            nextActivity()
        }
        val playbutton = view.findViewById<Button>(R.id.Play_again)
        playbutton.setOnClickListener(clickListener)

    }
    private fun nextActivity(){
        // Navigate to game or home screen
        findNavController().navigate(R.id.action_game_won_to_start_screen)
    }
}