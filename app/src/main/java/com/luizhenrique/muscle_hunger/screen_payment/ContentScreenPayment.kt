package com.luizhenrique.muscle_hunger.screen_payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.luizhenrique.muscle_hunger.ObserveItemsAdded
import com.luizhenrique.muscle_hunger.ObserveOrders
import com.luizhenrique.muscle_hunger.R
import com.luizhenrique.muscle_hunger.databinding.FragmentContentScreenPaymentBinding
import com.luizhenrique.muscle_hunger.screen_order.ItemAdapterOrder

class ContentScreenPayment : Fragment() {
    private lateinit var binding: FragmentContentScreenPaymentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapterPayment
    private lateinit var viewModel: ObserveOrders

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentContentScreenPaymentBinding.inflate(inflater, container, false)

        // incializando a viewModel
        viewModel = ViewModelProvider(requireActivity()).get(ObserveOrders::class.java)

        // Observa mudanças na variável do ViewModel
        viewModel.observeOrderData.observe(requireActivity()) { listOrdersData ->
            var number = 1
            for (orderData in listOrdersData) {
                orderData.name = "Pedido $number"
                number += 1
            }

            // recycleView irá armazenar o binding do itemView declarado com id recycleItemContentScreenPayment
            recyclerView = binding.recycleItemContentScreenPayment

            // adapter de tipo recycleView adapter está armazenando o arquivo adapter do item que será
            // exposto na tela de pagamento e passa como parâmetro a lista de pedidos feitos pelo cliente
            adapter = ItemAdapterPayment(listOrdersData)

            //declarando o recycleView adapter com o arquivo adapter ja configurado com as alterações do item.
            recyclerView.adapter = adapter
        }

        return binding.root
    }

}