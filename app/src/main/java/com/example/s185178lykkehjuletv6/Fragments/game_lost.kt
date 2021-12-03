package com.example.s185178lykkehjuletv6.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.s185178lykkehjuletv6.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [game_lost.newInstance] factory method to
 * create an instance of this fragment.
 */
class game_lost : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.game_lost_fragment, container, false)
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
        findNavController().navigate(R.id.action_game_lost_to_start_screen)
    }
}