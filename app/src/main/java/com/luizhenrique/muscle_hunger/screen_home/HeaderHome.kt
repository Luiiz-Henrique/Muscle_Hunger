package com.luizhenrique.muscle_hunger.screen_home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.luizhenrique.muscle_hunger.R
import com.luizhenrique.muscle_hunger.databinding.FragmentHeaderHomeBinding


class HeaderHome : Fragment() {
    private lateinit var binding: FragmentHeaderHomeBinding
    private lateinit var viewModel: ObserveHeaderHome

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentHeaderHomeBinding.inflate(inflater, container, false)

        // inicializando o viewModel
        viewModel = ViewModelProvider(requireActivity()).get(ObserveHeaderHome::class.java)


        // binding dos "botões" do scrool na header
        clickedTextViewHeader(binding.entriesMenuHeader)
        clickedTextViewHeader(binding.mainDishesMenuHeader)
        clickedTextViewHeader(binding.drinksMenuHeader)
        clickedTextViewHeader(binding.dessertsMenuHeader)

        return binding.root
    }

    // Funções para trocar cor de fundo e texto das textviews na menu header em 3 partes

    // função que vai ouvir o click das textViews na header
    private fun clickedTextViewHeader(textView: TextView){
        textView.setOnClickListener {

            // chamando a função que irá coloca todos com o fundo e texto de mesma cor. linha 60
            resetTextsViews()

            // função que "ativa" a categoria selecionada, mudando a corde fundo e de texto. linha 66
            activeTextView(textView)

            // mudando a categoria na viewModel para ser observada em outra parte do código
            // a função verifyCategory pega o texto da categoria no layout e muda para o nome da coleção no firebase.
            viewModel.changeCategory(verifyCategory(textView))
        }
    }

    // Irá chamar a função "setActiveState" para colocar todas as
    // Textsviews como falso e dar um "reset" na formatação do texto
    // colocando todos com o fundo e texto de mesma cor
     fun resetTextsViews (){
         binding.dessertsMenuHeader.setActiveState(false)  // linha
         binding.entriesMenuHeader.setActiveState(false)
         binding.mainDishesMenuHeader.setActiveState(false)
         binding.drinksMenuHeader.setActiveState(false)
     }


    // Irá chamar a função "setActiveState" para colocar como True
    // a textview que foi clicada e destaca seu elemento
    private fun activeTextView (textView: TextView){
        textView.setActiveState(true)
    }


    // Nesta função irá verificar se a TextView foi "setada" como true ou false e se caso estiver
    // true irá mudar a cor para destacar, e se colocada como false irá permanecer a cor original do design
    private fun TextView.setActiveState(isActive: Boolean){

        this.isSelected = isActive

        this.setTextColor(resources.getColor(if (isActive) R.color.green else R.color.off_white))
        this.background.setTint(resources.getColor(if (isActive) R.color.off_white else R.color.green_))
    }


    //muda o nome da categoria na view e transforma para o msm titulo do documento no firebase.
    private fun verifyCategory (category: TextView): String{
        when (category.text) {
            "Entradas" -> return "Entries"
            "Pratos Principais" -> return "Main_Dishes"
            "Sobremesas" -> return "Desserts"
            "Bebidas" -> return "Drinks"
            else -> return "Entries"
        }
    }
}