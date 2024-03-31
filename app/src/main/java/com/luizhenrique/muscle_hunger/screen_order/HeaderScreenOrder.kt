package com.luizhenrique.muscle_hunger.screen_order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luizhenrique.muscle_hunger.databinding.FragmentHeaderScreenOrderBinding

class HeaderScreenOrder : Fragment() {
    private lateinit var binding: FragmentHeaderScreenOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHeaderScreenOrderBinding.inflate(inflater, container, false)

        // onclick na imagem, para ao clicar voltar a tela anterior.
        binding.iconBackScreenOrder.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }
}