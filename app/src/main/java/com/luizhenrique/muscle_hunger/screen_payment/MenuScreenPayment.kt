package com.luizhenrique.muscle_hunger.screen_payment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.luizhenrique.muscle_hunger.MainActivity
import com.luizhenrique.muscle_hunger.ObserveItemsAdded
import com.luizhenrique.muscle_hunger.ObserveOrders
import com.luizhenrique.muscle_hunger.R
import com.luizhenrique.muscle_hunger.databinding.FragmentMenuScreenPaymentBinding


class MenuScreenPayment : Fragment() {
    private lateinit var binding: FragmentMenuScreenPaymentBinding
    private lateinit var viewModel: ObserveOrders

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenuScreenPaymentBinding.inflate(inflater, container, false)

        // incializando a viewModel
        viewModel = ViewModelProvider(requireActivity()).get(ObserveOrders::class.java)

        // Inserindo o valor total no menu
        insertData()

        binding.btnPaymentScreenPayment.setOnClickListener { payment() }
        binding.btnContinueScreenPayment.setOnClickListener {
            // clique do "continuar pedindo" irá direcionar para a tela principal
            (activity as MainActivity).transitionScreenHome()
        }

        return binding.root
    }

    // Quando acionar o botão de pagamento irá resetar a lista de pedidos e encaminhar a tela principal.
    private fun payment() {
        Toast.makeText(requireActivity(), R.string.text_toast_screen_payment_success_payment, Toast.LENGTH_LONG).show()
        (activity as MainActivity).transitionScreenHome()
        viewModel.resetListData()
    }

    private fun insertData(){
        var priceTotalOrders = 0f
        viewModel.observeOrderData.observe(requireActivity()) { listOrdersData ->
            for (order in listOrdersData) {
                priceTotalOrders += order.price
            }
        }
        binding.priceTotalScreenPayment.text = "R$ ${"%.2f".format(priceTotalOrders)}"
    }
}