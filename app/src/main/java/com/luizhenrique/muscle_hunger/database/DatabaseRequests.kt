package com.luizhenrique.muscle_hunger.database

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore
import com.luizhenrique.muscle_hunger.R
import com.luizhenrique.muscle_hunger.models.Item

class DatabaseRequests {
    // estanciando o firebase firestore
    val db = Firebase.firestore

    fun getDataItems(category: String, completion: (MutableList<Item>) -> Unit) {
        // completion é uma função passada como parâmetro e após será passada uma função lambda como argumento

        // Estou resgatando um documento específico (entries, drinks, main fishes ou desserts) na coleção Categories
        db.collection("Categories").document(category)
            .get()
            .addOnSuccessListener { documentSnapshot ->

                // Antes de manipular o documento obtido, temos que verificar se ele existe realmente
                if (documentSnapshot.exists()) {

                    val listItems = mutableListOf<Item>()
                    val itemsCount = documentSnapshot.data?.size ?: 0
                    var itemsProcessed = 0

                    //O for irá percorrer todos os campos existentes no documentos
                    for ((item, productReference) in documentSnapshot.data!!) {
                        if (productReference is DocumentReference) {

                        //e cada interação com um campo que tem uma referência, irá ser solicitado o documento desta referência
                        //db.collection("Products").document("")

                            productReference.get()
                            .addOnSuccessListener { itemSnapshot ->
                                if (itemSnapshot.exists()) {

                                    // Depois de verificar que o documento(item) existe, iremos manipular
                                    val name = itemSnapshot.data?.get("name").toString()
                                    val time = itemSnapshot.data?.get("time") as Map<String, Int>
                                    val price = itemSnapshot.data?.get("price").toString().toFloat()
                                    val ingredients =
                                        itemSnapshot.data?.get("ingredients").toString()
                                    val photo = itemSnapshot.data?.get("photo").toString()

                                    val item = Item(ingredients, name, photo, price, time)
                                    listItems.add(item)

                                }
                                else {
                                    Log.d(TAG, R.string.message_error_document_not_found.toString())
                                }

                                itemsProcessed++
                                if (itemsProcessed == itemsCount) {
                                    // Todas as operações de leitura foram concluídas
                                    completion(listItems)
                                }
                            }
                            .addOnFailureListener { exception ->
                                Log.d(TAG, R.string.message_error_getting_document.toString(), exception)
                                itemsProcessed++
                                if (itemsProcessed == itemsCount) {
                                    // Todas as operações de leitura foram concluídas
                                    completion(listItems)
                                }
                            }
                        }
                        else {
                            Log.d(TAG, R.string.message_error_document_not_found.toString())
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, R.string.message_error_getting_document.toString(), exception)
            }
    }
}