package com.luizhenrique.muscle_hunger.screen_home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.luizhenrique.muscle_hunger.ItemPopUp
import com.luizhenrique.muscle_hunger.database.DatabaseRequests
import com.luizhenrique.muscle_hunger.databinding.FragmentContentHomeBinding
import com.luizhenrique.muscle_hunger.models.Item

class ContentHome : Fragment(), ItemAdapter.OnItemButtonClickListener{
    private lateinit var binding: FragmentContentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private lateinit var viewModel: ObserveHeaderHome

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentContentHomeBinding.inflate(inflater, container, false)

        // Inicializa o ViewModel
        viewModel = ViewModelProvider(requireActivity()).get(ObserveHeaderHome::class.java)

        // Observa mudanças na variável do ViewModel
        viewModel.currentCategory.observe(requireActivity()) { category ->
            // Faça algo com o novo valor

            val database = DatabaseRequests()
            database.getDataItems(category) { listItens -> // { listItens -> ...} uma função lambda como argumento
                // Agora tenho a lista de itens aqui e posso manipular-la

                // recycleView irá armazenar o binding do itemView declarado com id recycleItemContentScreenOrder
                recyclerView = binding.recycleItemContentHome

                // adapter de tipo recycleView adapter está armazenando o arquivo adapter do item
                // que será exposto na tela principal e passa como parâmetro a lista retornada do firebase
                adapter = ItemAdapter(listItens)

                // está chamando a função da interface do item adapter e colocando
                // como parâmetro a própria classe que está implementada com a interface
                adapter.setOnItemButtonClickListener(this)

                //declarando o recycleView adapter com o arquivo adapter ja configurado com as alterações do item.
                recyclerView.adapter = adapter
            }
        }


        return binding.root
    }

    // função que irá abrir um popup após clicar em um item no conteúdo da home
    override fun onItemButtonClick(item: Item) {
        val dialog = ItemPopUp(item)
        dialog.setTargetFragment(this, 0)
        dialog.show(requireFragmentManager(), "dialog_item_popup")
    }
}
