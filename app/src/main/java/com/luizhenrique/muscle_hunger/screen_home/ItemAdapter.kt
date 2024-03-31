package com.luizhenrique.muscle_hunger.screen_home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luizhenrique.muscle_hunger.R
import com.luizhenrique.muscle_hunger.models.Item

class ItemAdapter (val items: MutableList<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var itemButtonClickListener: OnItemButtonClickListener? = null

    // pegará a view que será implementada junto com os dados fornecidos.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_content_home, parent, false)
        return ItemViewHolder(viewItem)
    }


    // Tamanho da lista (quantidade de produtos)
    override fun getItemCount(): Int = items.size


    // posição do item (essa função é individual de cada elemento da lista)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        // implementará os dados na view do item
        holder.bind(items[position])

        val cardItem = holder.itemView.findViewById<View>(R.id.layout_card_item_content)

        if (items[position].selected) cardItem.setBackgroundResource(R.drawable.card_item_selected)
        else cardItem.setBackgroundResource(R.color.transparent)

        // pegando o id do butão que está na view do item
        val btnShowDialog = holder.itemView.findViewById<Button>(R.id.btn_show_popup)

        // quando clicar nesse botão irá chamar a interface e implementar o item na variável declarada
        btnShowDialog.setOnClickListener {
            itemButtonClickListener?.onItemButtonClick(items[position])
        }
    }

    // classe responsável por implementação de dados de cada item
    inner class ItemViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.name_item)
        val price = itemView.findViewById<TextView>(R.id.price_item)
        val time = itemView.findViewById<TextView>(R.id.time_preparation_item)
        val photo = itemView.findViewById<ImageView>(R.id.image_item)

        // setando os dados em cada item
        fun bind (item: Item){
            name.setText(item.name)
            price.setText("R$ ${"%.2f".format(item.price)}")
            time.setText("${item.time["min"]} - ${item.time["max"]} min")

            Glide.with(itemView.context)
                .load(item.photo)
                .into(photo)
        }
    }

    // Interface para lidar com os cliques no botão do item
    interface OnItemButtonClickListener {
        fun onItemButtonClick(item: Item)
    }

    fun setOnItemButtonClickListener(listener: OnItemButtonClickListener) {
        this.itemButtonClickListener = listener
    }
}