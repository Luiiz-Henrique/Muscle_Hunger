package com.luizhenrique.muscle_hunger.screen_payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luizhenrique.muscle_hunger.R
import com.luizhenrique.muscle_hunger.databinding.FragmentHeaderScreenPaymentBinding

class HeaderScreenPayment : Fragment() {
    private lateinit var binding: FragmentHeaderScreenPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHeaderScreenPaymentBinding.inflate(inflater, container, false)

        // onclick na imagem, para ao clicar voltar a tela anterior.
        binding.iconBackScreenPayment.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }
}