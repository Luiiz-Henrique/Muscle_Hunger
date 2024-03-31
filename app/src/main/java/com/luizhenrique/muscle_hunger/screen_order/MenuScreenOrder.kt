package com.luizhenrique.muscle_hunger.screen_order

import android.os.Bundle
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
import com.luizhenrique.muscle_hunger.databinding.FragmentMenuScreenOrderBinding
import com.luizhenrique.muscle_hunger.models.Item
import com.luizhenrique.muscle_hunger.models.Order
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MenuScreenOrder : Fragment(){
    private lateinit var binding: FragmentMenuScreenOrderBinding
    private lateinit var viewModelListItems: ObserveItemsAdded
    private lateinit var viewModelListOrders: ObserveOrders

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenuScreenOrderBinding.inflate(inflater, container, false)

        // incializando as viewModels
        viewModelListItems = ViewModelProvider(requireActivity()).get(ObserveItemsAdded::class.java)
        viewModelListOrders = ViewModelProvider(requireActivity()).get(ObserveOrders::class.java)

        // binding buttons menu
        binding.btnAddScreenOrder.setOnClickListener {
            // clique do "adicionar mais itens" irá direcionar para a tela principal
            (activity as MainActivity).transitionScreenHome()
        }
        binding.btnAccomplishScreenOrder.setOnClickListener { insertOrderDatabase() }

        return binding.root
    }

    // função que irá setar os dados no menu
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var timeMinTotal: Int = 0
        var timeMaxTotal: Int = 0
        var priceTotal: Float = 0f

        // será utilizado para cada vez que a lista de itens atualizar ele ir somando de acordo com os items da lista
        viewModelListItems.observeListItemsAdded.observe(requireActivity()) { dataItems ->

            for (data in dataItems){
                timeMinTotal += data.time["min"]!!
                timeMaxTotal += data.time["max"]!!
                priceTotal += data.price
            }

        }

        binding.priceTotalScreenOrder.text = "R$ ${"%.2f".format(priceTotal)}"
        binding.timeTotalScreenOrder.text = "$timeMinTotal - $timeMaxTotal min"
    }

    // clique do "realizar pedido" irá inserir o pedido em uma lista de pedidos no view model
    // e mostrar um toast após redirecionar a tela principal.
    private fun insertOrderDatabase () {
        var listItems = mutableListOf<Item>()

        // observando os itens que contém no pedido
        viewModelListItems.observeListItemsAdded.observe(viewLifecycleOwner) { listItemsData ->

            listItems = listItemsData
        }

        // condicional para verificar se a listar não está vazia para realizar as ações
        if (listItems.size != 0) {

            // adiciona o pedido na lista de pedidos
            viewModelListOrders.addOrder(listItems)

            // reseta a lista de items da view model de pedido
            viewModelListItems.resetList()

            // observando a lista de pedidos
            viewModelListOrders.observeListOrders.observe(viewLifecycleOwner) { listOrders ->
                var pedido = ""
                var price = 0f

                // aqui possui uma matriz, para percorrer os pedidos e cada pedido um for percorrer os itens.
                for (order in listOrders) {

                    for (item in order) {
                        pedido += "${item.name} ${item.quantity}x - "
                        price += item.price
                    }
                    var name = "Pedido"
                    val items = "${pedido.substring(0, pedido.length - 3)}"
                    val priceData = price
                    val hour = SimpleDateFormat("HH:mm", Locale("pt", "BR")).format(Date())

                    // Declarei uma variavel "orderData" que vai ser uma especie de dados do pedido
                    // que vai ser utilizado no recycleView e mandei pra lista de dados de pedido na mesma viewModel
                    val orderData = Order(name, items, priceData, hour)
                    viewModelListOrders.addOrderData(orderData)
                    viewModelListOrders.resetList()

                    pedido = ""
                    price = 0f
                }
            }

            Toast.makeText(requireActivity(), R.string.text_toast_screen_order_accomplish, Toast.LENGTH_LONG).show()
            (activity as MainActivity).transitionScreenHome()
        }
        else {
            Toast.makeText(requireActivity(), R.string.text_toast_screen_order_not_accomplish, Toast.LENGTH_LONG).show()
        }
    }
}