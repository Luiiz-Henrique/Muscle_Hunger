package com.luizhenrique.muscle_hunger.screen_home


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.luizhenrique.muscle_hunger.MainActivity
import com.luizhenrique.muscle_hunger.ObserveItemsAdded
import com.luizhenrique.muscle_hunger.ObserveOrders
import com.luizhenrique.muscle_hunger.R
import com.luizhenrique.muscle_hunger.databinding.FragmentMenuBarHomeBinding
import com.luizhenrique.muscle_hunger.models.Item


class MenuBarHome : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentMenuBarHomeBinding
    private lateinit var viewModel: ObserveOrders
    private lateinit var viewModelItem: ObserveItemsAdded

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenuBarHomeBinding.inflate(inflater, container, false)

        // incializando a viewModel
        viewModel = ViewModelProvider(requireActivity()).get(ObserveOrders::class.java)
        viewModelItem = ViewModelProvider(requireActivity()).get(ObserveItemsAdded::class.java)

        // binding dos botões do menu
        binding.btnBarPayment.setOnClickListener(this)

        viewModelItem.observeListItemsAdded.observe(viewLifecycleOwner) { listItems ->
            insertBarOrder(listItems)
        }

        changeColorPayment()

        return binding.root
    }

    // OnClick para ações dos botões invisíveis da menu_bar.
    override fun onClick(view: View) {

        // ações do botão de requisição do pagamento.
        if (view.id == R.id.btn_bar_payment){

            // Observa mudanças na variável do ViewModel
            viewModel.observeOrderData.observe(requireActivity()) { listOrdersData ->

                if (listOrdersData.size != 0) {
                    (activity as MainActivity).transitionScreenPayment()
                }
                else {
                    // se o cliente clicar no pagamento e não tiver feito nenhum pedido
                    Toast.makeText(requireActivity(), R.string.text_toast_no_orders_placed, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun insertBarOrder(listItems: MutableList<Item>) {

        // condicional para quando tiver item na lista de pedido, inserir a barra de pedido
        if (listItems.size != 0) {
            childFragmentManager.beginTransaction()
                .replace(R.id.fragment_bar_order, BarOrder())
                .commit()
        }
    }

    private fun changeColorPayment() {
        viewModel.observeOrderData.observe(requireActivity()) { listOrdersData ->
            if (listOrdersData.size != 0) {
                //trocar cor do texto e do icone (payment)
                binding.textBarPayment.setTextColor(resources.getColor(R.color.yellow))
                binding.textBarPayment.alpha = 1f
                binding.imgBarPayment.setImageResource(R.drawable.payment_yellow)
                binding.imgBarPayment.alpha = 1f
            }
            else {
                binding.textBarPayment.setTextColor(resources.getColor(R.color.grey_))
                binding.textBarPayment.alpha = 0.6f
                binding.imgBarPayment.setImageResource(R.drawable.payment_grey)
                binding.imgBarPayment.alpha = 0.6f
            }
        }
    }
}