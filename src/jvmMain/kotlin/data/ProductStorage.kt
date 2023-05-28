package data

import java.time.LocalDate

class ProductStorage : Repository {
    private val products: MutableList<Product> = ArrayList()

    init {
        products.add(
            Product(
                1,
                "Bread",
                "Food",
                "Description1",
                45.5,
                true,
                12,
                LocalDate.now().minusYears((Math.random() * 10).toLong())
            )
        )
        products.add(
            Product(
                2,
                "Butter",
                "Food",
                "Description2",
                145.0,
                true,
                4,
                LocalDate.now().minusYears((Math.random() * 10).toLong())
            )
        )
        products.add(
            Product(
                3,
                "Fanta",
                "Drink",
                "Description3",
                13.5,
                false,
                45,
                LocalDate.now().minusYears((Math.random() * 10).toLong())
            )
        )
        products.add(
            Product(
                4,
                "Sprite",
                "Drink",
                "Description4",
                54.6,
                true,
                65,
                LocalDate.now().minusYears((Math.random() * 10).toLong())
            )
        )
        products.add(
            Product(
                5,
                "Pizza",
                "Food",
                "Description5",
                123.0,
                false,
                34,
                LocalDate.now().minusYears((Math.random() * 10).toLong())
            )
        )
        products.add(
            Product(
                6,
                "Water",
                "Drink",
                "Description6",
                15.57,
                false,
                65,
                LocalDate.now().minusYears((Math.random() * 10).toLong())
            )
        )
        products.add(
            Product(
                7,
                "Orange",
                "Fruit",
                "Description7",
                235.58,
                true,
                182,
                LocalDate.now().minusYears((Math.random() * 10).toLong())
            )
        )
        products.add(
            Product(
                8,
                "Coca Cola",
                "Drink",
                "Description8",
                55.75,
                true,
                76,
                LocalDate.now().minusYears((Math.random() * 10).toLong())
            )
        )
        products.add(
            Product(
                9,
                "Apple",
                "Fruit",
                "Description9",
                23.61,
                false,
                54,
                LocalDate.now().minusYears((Math.random() * 10).toLong())
            )
        )
    }

    override fun getAll(): List<Product?> {
        return products;
    }

    override fun getById(id: Int): Product {
        var product: Product? = null
        product = products.stream()
            .filter { p: Product -> p.id == id }
            .findFirst()
            .get()
        return product
    }

    override fun getAllByCategory(category: String?): List<Product?> {
        return products.filter { it.category == category }.toList()
    }

    override fun getProductsIsNotTheStorage(): List<Product?> {
        return products.filter { !it.isOnStorage }.toList()
    }

    override fun getProductsIsTheStorage(): List<Product?> {
        return products.filter { it.isOnStorage }.toList()
    }

    override fun addProduct(product: Product?): Boolean {
        var maxId = products.stream()
            .mapToInt { p: Product -> p.id }
            .max()
            .asInt
        if (product != null) {
            product.id = ++maxId
            products.add(product)
            return true
        }
        return false
    }

    override fun updateProduct(id: Int, product: Product?): Boolean {
        val index = products.indexOfFirst { it.id == id }
        if (index != -1 && product != null) {
            products[index] = product
            return true
        }
        return false
    }

    override fun deleteProduct(id: Int): Boolean {
        val toDelete = getById(id) ?: return false
        products.remove(toDelete)
        return true
    }

}