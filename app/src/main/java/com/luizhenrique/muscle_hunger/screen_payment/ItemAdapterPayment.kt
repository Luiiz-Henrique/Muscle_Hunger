package com.luizhenrique.muscle_hunger.screen_payment

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.luizhenrique.muscle_hunger.R
import com.luizhenrique.muscle_hunger.models.Order

class ItemAdapterPayment (val orderData: MutableList<Order>) : RecyclerView.Adapter<ItemAdapterPayment.ItemViewHolder>() {

    // pegará a view que será implementada junto com os dados fornecidos.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_content_screen_payment, parent, false)
        return ItemViewHolder(viewItem)
    }

    // Tamanho da lista (quantidade de produtos)
    override fun getItemCount(): Int = orderData.size

    // posição do item (essa função é individual de cada elemento da lista)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        // implementará os dados na view do item
        holder.bind(orderData[position])

        // botão de expandir e retrair o texto mudando a imagem
        val btnExCoText = holder.itemView.findViewById<ImageView>(R.id.icon_exco_screen_payment)
        btnExCoText.setOnClickListener {
            val ingredients = holder.itemView.findViewById<TextView>(R.id.items_order_screen_payment)
            if (ingredients.maxLines == 2) {
                ingredients.maxLines = Int.MAX_VALUE

                val rotation = ObjectAnimator.ofFloat(btnExCoText, View.ROTATION, 360f, 180f)
                rotation.duration = 600
                rotation.start()
            }
            else {
                ingredients.maxLines = 2

                val rotation = ObjectAnimator.ofFloat(btnExCoText, View.ROTATION, 180f, 360f)
                rotation.duration = 600
                rotation.start()
            }
        }
    }

    inner class ItemViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val numberOrder = itemView.findViewById<TextView>(R.id.title_order_screen_payment)
        val price = itemView.findViewById<TextView>(R.id.price_total_order_screen_order)
        val items = itemView.findViewById<TextView>(R.id.items_order_screen_payment)
        val hours = itemView.findViewById<TextView>(R.id.hour_order_screen_order)

        // setando os dados em cada item
        fun bind (order: Order){
            numberOrder.text = order.name
            price.text = "R$ ${"%.2f".format(order.price)}"
            items.text = order.items
            hours.text = order.hour
        }
    }
}