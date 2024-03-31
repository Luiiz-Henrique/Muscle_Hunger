package com.luizhenrique.muscle_hunger


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.luizhenrique.muscle_hunger.databinding.ActivityMainBinding
import com.luizhenrique.muscle_hunger.models.Item
import com.luizhenrique.muscle_hunger.screen_home.ContentHome
import com.luizhenrique.muscle_hunger.screen_home.HeaderHome
import com.luizhenrique.muscle_hunger.screen_home.MenuBarHome
import com.luizhenrique.muscle_hunger.screen_home.ItemAdapter
import com.luizhenrique.muscle_hunger.screen_order.ContentScreenOrder
import com.luizhenrique.muscle_hunger.screen_order.HeaderScreenOrder
import com.luizhenrique.muscle_hunger.screen_order.MenuScreenOrder
import com.luizhenrique.muscle_hunger.screen_payment.ContentScreenPayment
import com.luizhenrique.muscle_hunger.screen_payment.HeaderScreenPayment
import com.luizhenrique.muscle_hunger.screen_payment.MenuScreenPayment

class MainActivity : AppCompatActivity(), ItemAdapter.OnItemButtonClickListener{
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        fragmentManager = supportFragmentManager

        // função para inserir os fragmentos na primeira entrada
        transitionScreenHome ()
    }

    // popup dialog item
    override fun onItemButtonClick(item: Item) {
        val dialog = ItemPopUp(item)
        dialog.show(supportFragmentManager, "dialog_item_popup")
    }

    // ação do botão de voltar
    override fun onBackPressed() {
        super.onBackPressed()
        fragmentManager.popBackStack()
        fragmentManager.popBackStack()
    }

    // função para trocar a Header, Content e o Menu para a simulação de mudança de tela
    fun transitionScreenHome() {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_header, HeaderHome())
            .commit()
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_content_home, ContentHome())
            .commit()
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_menu_bar, MenuBarHome())
            .commit()
    }

    // função para trocar a Header, Content e o Menu para a simulação de mudança de tela
    fun transitionScreenOrder () {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_header, HeaderScreenOrder())
            .addToBackStack("Header")
            .commit()
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_content_home, ContentScreenOrder())
            .addToBackStack("Content")
            .commit()
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_menu_bar, MenuScreenOrder())
            .addToBackStack("Menu")
            .commit()
    }

    // função para trocar a Header, Content e o Menu para a simulação de mudança de tela
    fun transitionScreenPayment () {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_header, HeaderScreenPayment())
            .addToBackStack("Header")
            .commit()
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_content_home, ContentScreenPayment())
            .addToBackStack("Content")
            .commit()
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_menu_bar, MenuScreenPayment())
            .addToBackStack("Menu")
            .commit()
    }
}