package com.luizhenrique.muscle_hunger.screen_home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luizhenrique.muscle_hunger.models.Item

class ObserveHeaderHome : ViewModel() {
    // Variável category(MutableLiveData) que será observada
    private val category = MutableLiveData<String>().apply {
        value = "Entries"
    }

    // Variável LiveData que será exposta para observação externa
    val currentCategory: LiveData<String>
        get() = category

    // Função para atualizar o valor da variável
    fun changeCategory(newCategory: String) {
        category.value = newCategory
    }

    private val dataItem = MutableLiveData<Item>().apply{
        value = Item("", "", "", 0f, mapOf("" to 0))
    }

    val currentDataItem: LiveData<Item>
        get() = dataItem

    fun insertDataItem (currentItem: Item){
        dataItem.value = currentItem
    }
}