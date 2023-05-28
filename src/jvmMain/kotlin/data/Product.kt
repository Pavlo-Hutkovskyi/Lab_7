package data

import java.io.Serializable
import java.time.LocalDate

class Product : Serializable {
    @JvmField
    var id = 0
    var nameProduct: String? = null
    var category: String? = null
    var description: String? = null
    var price = 0.0
    var isOnStorage = false
    var amount = 0
    var deliveryDate: LocalDate? = null

    constructor()

    constructor(id: Int, nameProduct: String?, category: String?, description: String?, price: Double, isOnStorage: Boolean, amount: Int, deliveryDate: LocalDate?) {
        this.id = id
        this.nameProduct = nameProduct
        this.category = category
        this.description = description
        this.price = price
        this.isOnStorage = isOnStorage
        this.amount = amount
        this.deliveryDate = deliveryDate
    }

    constructor(nameProduct: String?, category: String?, description: String?, price: Double, isOnStorage: Boolean, amount: Int, deliveryDate: LocalDate?) {
        this.nameProduct = nameProduct
        this.category = category
        this.description = description
        this.price = price
        this.isOnStorage = isOnStorage
        this.amount = amount
        this.deliveryDate = deliveryDate
    }

    override fun toString(): String {
        return id.toString() + ") Назва: " + nameProduct + ", категорія: " + category +
                ", опис: " + description +
                ", ціна за одиницю: " + price + ", чи є в наявності: " + isOnStorage + ", кількість: " + amount + ", дата доставки: " + deliveryDate
    }

    companion object {
        const val serialVersionUID = 4748325733388567091L

        fun parseFromString(input: String): Product {
            val parts = input.split(")", limit = 2)
            val id = parts[0].trim().toIntOrNull() ?: 0
            val properties = parts[1].split(",").map { it.trim() }

            val nameProduct = properties[0].substringAfter("Назва:").trim()
            val category = properties[1].substringAfter("категорія:").trim()
            val description = properties[2].substringAfter("опис:").trim()
            val price = properties[3].substringAfter("ціна за одиницю:").trim().toDoubleOrNull() ?: 0.0
            val isOnStorage = properties[4].substringAfter("чи є в наявності:").trim().toBoolean()
            val amount = properties[5].substringAfter("кількість:").trim().toIntOrNull() ?: 0
            val deliveryDate = properties[6].substringAfter("дата доставки:").trim().let {
                if (it.isBlank()) null else LocalDate.parse(it)
            }

            return Product(id, nameProduct, category, description, price, isOnStorage, amount, deliveryDate)
        }

    }
}