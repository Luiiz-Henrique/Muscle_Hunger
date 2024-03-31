package com.luizhenrique.muscle_hunger

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.luizhenrique.muscle_hunger.databinding.FragmentItemPopUpBinding
import com.luizhenrique.muscle_hunger.models.Item
import com.luizhenrique.muscle_hunger.screen_home.ObserveHeaderHome



class ItemPopUp (var item: Item) : DialogFragment() {
    private lateinit var binding: FragmentItemPopUpBinding
    private lateinit var viewModelHome: ObserveHeaderHome
    private lateinit var viewModelItem: ObserveItemsAdded

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentItemPopUpBinding.inflate(inflater, container, false)

        // inicializando as views
        viewModelItem = ViewModelProvider(requireActivity()).get(ObserveItemsAdded::class.java)
        viewModelHome = ViewModelProvider(requireActivity()).get(ObserveHeaderHome::class.java)

        // inserindo os dados na variavel mutavel do viewModel para ser observada após
        // pegando como parâmetro o item clicado
        viewModelHome.insertDataItem(item)

        // irá inserir os dados do item no popup e passando como parâmetro o item que foi passado na viewModel
        insertData (viewModelHome.currentDataItem)

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // Cria o diálogo e define se é cancelável
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(true) // Permitir fechar clicando fora do diálogo

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // estabelecendo alguns detalhes no dialog
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.BOTTOM)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.border_radius_15dp)

        // Configura o OnClickListener para o botão de fechar
        view.findViewById<View>(R.id.icon_close_popup).setOnClickListener {
            dismiss() // Fecha o diálogo ao clicar no botão
        }

        binding.iconSumPopup.setOnClickListener { sumQuantity() }
        binding.iconSubtractPopup.setOnClickListener { subtractQuantity() }
        binding.btnAddOrderPopup.setOnClickListener { addItemOrder() }
    }

    // responsável por inserir os dados no popup
    fun insertData (item: LiveData<Item>) {
        item.observe(requireActivity()) {item ->
            Glide.with(binding.root)
                .load(item.photo)
                .into(binding.imgItemPopup)

            binding.nameItemPopup.setText(item.name)
            binding.timePopup.setText("${item.time["min"]} - ${item.time["max"]} min")
            binding.ingredientsPopup.setText(item.ingredients)
            binding.priceItemPopup.setText("${"%.2f".format(item.price)}")
            binding.priceUtil.setText(item.price.toString())
            binding.priceTotalItemPopup.setText("${"%.2f".format(item.price)}")
        }
    }

    // irá acrescentar um na quantidade do item no popup
    private fun sumQuantity(){
        val quantity = binding.quantityItemPopup.text.toString().toInt() + 1
        binding.quantityItemPopup.setText(quantity.toString())

        // determinando um novo valor e inserindo na view do popup
        val priceItem = binding.priceUtil.text.toString().toFloat()
        val total: Float = priceItem * quantity.toFloat()
        binding.priceTotalItemPopup.setText("${"%.2f".format(total)}")
    }

    // irá decrescentar um na quantidade do item no popup
    private fun subtractQuantity(){
        var quantity = binding.quantityItemPopup.text.toString().toInt()

        // condicional para limitar as mudanças quando a quantidade atingir 1
        if (quantity > 1){
            quantity -= 1
            binding.quantityItemPopup.setText(quantity.toString())

            // determinando um novo valor e inserindo na view do popup
            val priceItem = binding.priceUtil.text.toString().toFloat()
            val total = priceItem * quantity.toFloat()
            binding.priceTotalItemPopup.setText("${"%.2f".format(total)}")
        }

    }

    // responsável por adicionar o item na lista de items no viewModel, quando o botão "adicionar" for clicado
    private fun addItemOrder(){
        if (isAdded) {
            viewModelHome.currentDataItem.observe(viewLifecycleOwner) { itemData ->

                val name: String = itemData.name
                val time: Map<String, Int> = itemData.time
                val quantity: Int = binding.quantityItemPopup.text.toString().toInt()
                val price: Float = itemData.price * quantity.toFloat()
                val ingredients: String = itemData.ingredients
                val photo: String = itemData.photo

                val item = Item(ingredients, name, photo, price, time, quantity)

                viewModelItem.addItem(item)
            }
            dismiss()
        }
    }
}