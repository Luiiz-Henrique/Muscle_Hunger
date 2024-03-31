package com.luizhenrique.muscle_hunger


import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.luizhenrique.muscle_hunger.models.Item
import com.luizhenrique.muscle_hunger.screen_home.ItemAdapter

class ObserveItemsAdded(private val state: SavedStateHandle) : ViewModel() {

    // Variável listItemsAdded(MutableLiveData que por ventura é a lista de items adicionados ao pedido)
    // que será observada
    private val listItemsAdded = MutableLiveData<MutableList<Item>>().apply {
        value = mutableListOf()
    }

    // Variável LiveData que será exposta para observação externa
    val observeListItemsAdded: LiveData<MutableList<Item>>
        get() = listItemsAdded

    // função que irá adicionar um item a lista quando chamado
    fun addItem (item: Item) : Unit {
        val listCurrent = listItemsAdded.value ?: mutableListOf()
        var existsList = false
        for (itemList in listCurrent) {
            if (itemList.name == item.name) {
                itemList.quantity += item.quantity
                itemList.price += item.price
                listItemsAdded.value = listCurrent
                existsList = true
            }
        }
        if (!existsList) {
            listCurrent.add(item)
            item.selected = true
            listItemsAdded.value = listCurrent
        }
    }

    // função que irá deletar um item da lista quando chamado
    fun deleteItem (item: Item): Unit {
        val listCurrent = listItemsAdded.value ?: mutableListOf()
        listCurrent.remove(item)
        item.selected = false
        listItemsAdded.value = listCurrent
    }

    // função para resetar a lista
    fun resetList (): Unit {
        var listCurrent = listItemsAdded.value ?: mutableListOf()
        listCurrent = mutableListOf()
        listItemsAdded.value = listCurrent
    }
}