package com.luizhenrique.muscle_hunger

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.luizhenrique.muscle_hunger.models.Item
import com.luizhenrique.muscle_hunger.models.Order

class ObserveOrders(private val state: SavedStateHandle) : ViewModel() {

    // Variável listOrders(MutableLiveData que por ventura é a lista de pedidos feitos) que será observada
    private val listOrders = MutableLiveData<MutableList<MutableList<Item>>>().apply {
        value = mutableListOf()
    }

    // Variável LiveData que será exposta para observação externa
    val observeListOrders: LiveData<MutableList<MutableList<Item>>>
        get() = listOrders

    // função que irá adicionar um pedido a lista quando chamado
    fun addOrder (order: MutableList<Item>) : Unit {
        val listCurrent = listOrders.value ?: mutableListOf()
        if (order.size != 0){
            listCurrent.add(order)
            listOrders.value = listCurrent
        }
    }

    // função que irá resetar a listar de orders, quando o o mesmo for adicionado em outra lista
    fun resetList () : Unit {
        var listCurrent = listOrders.value ?: mutableListOf()
        listCurrent = mutableListOf()
        listOrders.value = listCurrent
    }


    // Variável listOrders(MutableLiveData que por ventura é a lista de pedidos feitos) que será observada
    private val orderData = MutableLiveData<MutableList<Order>>().apply {
        value = mutableListOf()
    }

    // Variável LiveData que será exposta para observação externa
    val observeOrderData: LiveData<MutableList<Order>>
        get() = orderData

    // função que irá adicionar um pedido a lista quando chamado
    fun addOrderData (order: Order) : Unit {
        val listCurrent = orderData.value ?: mutableListOf()
        listCurrent.add(order)
        orderData.value = listCurrent
    }

    // função que irá resetar a listar de orders, quando o "pagamento" for efetuado
    fun resetListData () : Unit {
        var listCurrent = orderData.value ?: mutableListOf()
        listCurrent = mutableListOf()
        orderData.value = listCurrent
    }
}