package com.luizhenrique.muscle_hunger.screen_order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.luizhenrique.muscle_hunger.ObserveItemsAdded
import com.luizhenrique.muscle_hunger.databinding.FragmentContentScreenOrderBinding
import com.luizhenrique.muscle_hunger.models.Item

class ContentScreenOrder : Fragment(), ItemAdapterOrder.ItemClickListener {
    private lateinit var binding: FragmentContentScreenOrderBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapterOrder
    private lateinit var viewModel: ObserveItemsAdded

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContentScreenOrderBinding.inflate(inflater, container, false)

        // incializando a viewModel
        viewModel = ViewModelProvider(requireActivity()).get(ObserveItemsAdded::class.java)

        // Observa mudanças na variável do ViewModel
        viewModel.observeListItemsAdded.observe(requireActivity()) { listItems ->

            // recycleView irá armazenar o binding do itemView declarado com id recycleItemContentScreenOrder
            recyclerView = binding.recycleItemContentScreenOrder

            // adapter de tipo recycleView adapter está armazenando o arquivo adapter do item
            // que será exposto na tela de pedido e passa como parâmetro a lista retornada do firebase
            adapter = ItemAdapterOrder(listItems)

            // está chamando a função da interface do item adapter e colocando
            // como parâmetro a própria classe que está implementada com a interface
            adapter.setItemClickListener(this)

            //declarando o recycleView adapter com o arquivo adapter ja configurado com as alterações do item.
            recyclerView.adapter = adapter
        }

        return binding.root
    }

    // sobreescrevendo função da viewModel para deletar um item da lista declarada
    override fun onDeleteItem(item: Item) {
        viewModel = ViewModelProvider(requireActivity()).get(ObserveItemsAdded::class.java)
        viewModel.deleteItem(item)
    }
}