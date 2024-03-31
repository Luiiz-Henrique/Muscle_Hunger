package com.luizhenrique.muscle_hunger.screen_home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.luizhenrique.muscle_hunger.MainActivity
import com.luizhenrique.muscle_hunger.ObserveItemsAdded
import com.luizhenrique.muscle_hunger.databinding.FragmentBarOrderBinding

class BarOrder : Fragment() {
    private lateinit var binding: FragmentBarOrderBinding
    private lateinit var viewModelItem: ObserveItemsAdded

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment.
        binding = FragmentBarOrderBinding.inflate(inflater, container, false)

        // inicializando o viewModel
        viewModelItem = ViewModelProvider(requireActivity()).get(ObserveItemsAdded::class.java)

        // Eliminar a barra de pedido se não tiver nenhum item na lista.
        viewModelItem.observeListItemsAdded.observe(viewLifecycleOwner) { listItems ->
            if (listItems.size == 0) {
                parentFragmentManager.beginTransaction().remove(this).commit()
            }
        }

        // Ao clicar na barra ira para tela de pedido.
        binding.btnViewOrder.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).addToBackStack("").commit()
            (activity as MainActivity).transitionScreenOrder()
        }

        return binding.root
    }

    // Inserir dados no barrade pedido quando criada.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var priceTotal: Float = 0f
        var timeMaxTotal: Int = 0
        var timeMinTotal: Int = 0
        var itemsTotal: Int = 0

        viewModelItem.observeListItemsAdded.observe(requireActivity()) { listItems ->
            for (item in listItems){
                priceTotal += item.price
                timeMaxTotal += item.time["max"]!!
                timeMinTotal += item.time["min"]!!
                itemsTotal += item.quantity
            }
            binding.quantityTotalItemsOrder.text = itemsTotal.toString()
            binding.priceTotalBarOrder.text = "${"%.2f".format(priceTotal)}"
            binding.timeTotalBarOrder.text = "$timeMinTotal - $timeMaxTotal min"
        }
    }
}