package com.example.s185178lykkehjuletv6.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.s185178lykkehjuletv6.R

class game_won : Fragment() {
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
            NextActivity()
        }
        val Playbutton = view.findViewById<Button>(R.id.Play_again)
        Playbutton.setOnClickListener(clickListener)

    }
    private fun NextActivity(){
        // Navigate to game or home screen
        findNavController().navigate(R.id.action_game_won_to_start_screen)
    }
}