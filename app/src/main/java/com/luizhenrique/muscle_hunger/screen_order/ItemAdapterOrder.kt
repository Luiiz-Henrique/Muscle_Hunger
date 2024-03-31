package com.luizhenrique.muscle_hunger.screen_order


import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luizhenrique.muscle_hunger.R
import com.luizhenrique.muscle_hunger.models.Item

class ItemAdapterOrder (val items: MutableList<Item>) : RecyclerView.Adapter<ItemAdapterOrder.ItemViewHolder>() {
    // pegará a view que será implementada junto com os dados fornecidos.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_content_screen_order, parent, false)
        return ItemViewHolder(viewItem)
    }

    // Tamanho da lista (quantidade de produtos)
    override fun getItemCount(): Int = items.size

    // posição do item (essa função é individual de cada elemento da lista)
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        // implementará os dados na view do item
        holder.bind(items[position])

        // resgatando algumas views do layout de screenOrder
        val btnSubtractQuantity = holder.itemView.findViewById<ImageView>(R.id.icon_subtract_screen_order)
        val btnSumQuantity = holder.itemView.findViewById<ImageView>(R.id.icon_sum_screen_order)
        val textQuantityItem = holder.itemView.findViewById<TextView>(R.id.quantity_item_screen_order)
        val priceTotalItems = holder.itemView.findViewById<TextView>(R.id.price_item_screen_order)
        val btnExCoText = holder.itemView.findViewById<ImageView>(R.id.icon_exco_screen_order)

        // sobrescrevendo o texto resgatado na view
        val priceItem = priceTotalItems.text.toString().substring(3).replace(",", ".").toFloat() / textQuantityItem.text.toString().toFloat()

        // condicional para trocar de icone
        if (textQuantityItem.text.toString().toInt() <= 1) {
            btnSubtractQuantity.setImageResource(R.drawable.icon_trash)
        }
        else {
            btnSubtractQuantity.setImageResource(R.drawable.icon_subtract)
        }

        // botão para acrescentar na quantidade do item na tela de pedido
        btnSumQuantity.setOnClickListener {

            // condicional para trocar icone
            if (textQuantityItem.text.toString().toInt() == 1) {
                btnSubtractQuantity.setImageResource(R.drawable.icon_subtract)
            }

            // somando um na quantidade que foi resgatado na view
            val sumQuantity = textQuantityItem.text.toString().toInt() + 1

            //estabelecendo novo preço total
            val priceTotalItem = priceItem * sumQuantity

            // trocando os valores no data do item
            items[position].price = priceTotalItem
            items[position].quantity = sumQuantity

            // trocando os valores na view do item
            priceTotalItems.text = "R$ ${"%.2f".format(priceTotalItem)}"
            textQuantityItem.text = sumQuantity.toString()
        }


        // botão para decrescentar a quantidade do item na tela de pedido
        btnSubtractQuantity.setOnClickListener {

            if (textQuantityItem.text.toString().toInt() == 2) {
                btnSubtractQuantity.setImageResource(R.drawable.icon_trash)
            }

            // condicional para determinar quando decrescentar ou deletar um item na lista
            if (textQuantityItem.text.toString().toInt() > 1) {

                // subtraindo um na quantidade que foi resgatado na view
                val subtractQuantity = textQuantityItem.text.toString().toInt() - 1

                // estabelecendo o novo preço total dos itens
                val priceTotalItem = priceItem * subtractQuantity

                // atualizando os dados no data do item
                items[position].price = priceTotalItem
                items[position].quantity = subtractQuantity

                // iserindo os novos valores na view do item
                priceTotalItems.text = "R$ ${"%.2f".format(priceTotalItem)}"
                textQuantityItem.text = subtractQuantity.toString()
            }
            else {
                // chamando a função delete da interface para deletar um item caso tenha somente um item do mesmo
                itemClickListener?.onDeleteItem(items[position])
            }
        }

        // botão de expandir e retrair o texto mudando a imagem
        btnExCoText.setOnClickListener {
            val ingredients = holder.itemView.findViewById<TextView>(R.id.ingredients_items_screen_order)
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

    // classe responsável por implementação de dados de cada item
    inner class ItemViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        val itemName = itemView.findViewById<TextView>(R.id.title_item_screen_order)
        val price = itemView.findViewById<TextView>(R.id.price_item_screen_order)
        val time = itemView.findViewById<TextView>(R.id.time_item_screen_order)
        val photo = itemView.findViewById<ImageView>(R.id.img_item_screen_order)
        val ingredients = itemView.findViewById<TextView>(R.id.ingredients_items_screen_order)
        val quantity = itemView.findViewById<TextView>(R.id.quantity_item_screen_order)

        // setando os dados em cada item
        fun bind (item: Item){
            itemName.text = item.name
            price.text = "R$ ${"%.2f".format(item.price)}"
            time.text = "${item.time["min"]} - ${item.time["max"]} min"
            ingredients.text = item.ingredients
            quantity.text = item.quantity.toString()

            Glide.with(itemView.context)
                .load(item.photo)
                .into(photo)

        }
    }

    //interface para ser compartilhada com outros arquivos
    interface ItemClickListener {
        // função que será reescrita no arquivo ContentScreenOrder
        fun onDeleteItem(item: Item)
    }

    private var itemClickListener: ItemClickListener? = null

    // função para determinar um ouvinte na interface quando implementada
    fun setItemClickListener(listener: ItemClickListener) {
        itemClickListener = listener
    }
}

