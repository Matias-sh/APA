package com.cocido.apa.data

import android.content.Context
import com.cocido.apa.ui.components.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.InputStream

data class ProductJson(
    val id: String,
    val name: String,
    val price: String,
    val imageRes: String? = null,
    val imageUrl: String? = null
)

data class CartItemJson(
    val productId: String,
    val quantity: Int
)

data class SavedCartJson(
    val id: String,
    val name: String,
    val productCount: Int,
    val items: List<CartItemJson>
)

data class LastPurchaseJson(
    val items: List<CartItemJson>
)

data class AppDataJson(
    val products: List<ProductJson>,
    val savedCarts: List<SavedCartJson>,
    val lastPurchase: LastPurchaseJson?
)

class AppDataRepository(private val context: Context) {
    private val gson = Gson()
    private val dataFileName = "app_data.json"
    private val dataFile: File = File(context.filesDir, dataFileName)

    // Mapeo de nombres de recursos a IDs de drawable
    private fun getDrawableId(resourceName: String?): Int? {
        if (resourceName == null) return null
        return try {
            val resourceId = context.resources.getIdentifier(
                resourceName,
                "drawable",
                context.packageName
            )
            if (resourceId != 0) resourceId else null
        } catch (e: Exception) {
            null
        }
    }

    // Cargar datos iniciales desde assets
    private fun loadInitialData(): AppDataJson {
        return try {
            val inputStream: InputStream = context.assets.open("app_data.json")
            val json = inputStream.bufferedReader().use { it.readText() }
            gson.fromJson(json, AppDataJson::class.java)
        } catch (e: Exception) {
            // Si falla, retornar datos vacíos
            AppDataJson(
                products = emptyList(),
                savedCarts = emptyList(),
                lastPurchase = null
            )
        }
    }

    // Guardar datos al almacenamiento interno
    private fun saveData(data: AppDataJson) {
        try {
            val json = gson.toJson(data)
            dataFile.writeText(json)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Cargar datos desde almacenamiento interno o assets
    private fun loadData(): AppDataJson {
        return if (dataFile.exists()) {
            try {
                val json = dataFile.readText()
                gson.fromJson(json, AppDataJson::class.java)
            } catch (e: Exception) {
                loadInitialData()
            }
        } else {
            val initialData = loadInitialData()
            saveData(initialData) // Guardar copia inicial
            initialData
        }
    }

    // Obtener productos
    fun getProducts(): List<Product> {
        val data = loadData()
        return data.products.map { productJson ->
            Product(
                id = productJson.id,
                name = productJson.name,
                price = productJson.price,
                imageRes = getDrawableId(productJson.imageRes),
                imageUrl = productJson.imageUrl ?: "",
                quantity = 0,
                isInCart = false
            )
        }
    }

    // Obtener carritos guardados
    fun getSavedCarts(): List<SavedCartJson> {
        val data = loadData()
        return data.savedCarts
    }

    // Obtener último carrito guardado (para "repetir última compra")
    fun getLastPurchase(): Map<String, Int> {
        val data = loadData()
        return data.lastPurchase?.items?.associate { it.productId to it.quantity } ?: emptyMap()
    }

    // Cargar un carrito guardado (retorna mapa de productId -> quantity)
    fun loadSavedCart(cartId: String): Map<String, Int> {
        val data = loadData()
        val cart = data.savedCarts.find { it.id == cartId }
        return cart?.items?.associate { it.productId to it.quantity } ?: emptyMap()
    }

    // Guardar un nuevo carrito
    fun saveCart(name: String, items: Map<String, Int>): String {
        val data = loadData()
        val newCartId = (data.savedCarts.maxOfOrNull { it.id.toIntOrNull() ?: 0 } ?: 0) + 1
        val newCart = SavedCartJson(
            id = newCartId.toString(),
            name = name,
            productCount = items.values.sum(),
            items = items.map { (productId, quantity) ->
                CartItemJson(productId, quantity)
            }
        )
        val updatedData = data.copy(
            savedCarts = data.savedCarts + newCart
        )
        saveData(updatedData)
        return newCartId.toString()
    }

    // Guardar última compra
    fun saveLastPurchase(items: Map<String, Int>) {
        val data = loadData()
        val lastPurchase = LastPurchaseJson(
            items = items.map { (productId, quantity) ->
                CartItemJson(productId, quantity)
            }
        )
        val updatedData = data.copy(lastPurchase = lastPurchase)
        saveData(updatedData)
    }

    // Eliminar un carrito guardado
    fun deleteSavedCart(cartId: String) {
        val data = loadData()
        val updatedCarts = data.savedCarts.filter { it.id != cartId }
        val updatedData = data.copy(savedCarts = updatedCarts)
        saveData(updatedData)
    }
}
