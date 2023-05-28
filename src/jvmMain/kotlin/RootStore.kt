import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.Product
import data.ProductStorage
import data.Repository

val repo: Repository = ProductStorage()

internal class RootStore {

    var state: RootState by mutableStateOf(initialState())
        private set

    fun onItemClicked(id: Int?) {
        setState { copy(editingItemId = id) }
    }

    fun onItemDeleteClicked(id: Int?) {
        setState { copy(items = items.filterNot { it?.id == id }) }
    }

    fun onAddItemClicked() {
        setState {
            val newItem = Product.parseFromString(inputText)
            copy(items = items + newItem, inputText = "")
        }
    }

    fun onInputTextChanged(text: String) {
        setState { copy(inputText = text) }
    }

    fun onEditorCloseClicked() {
        setState { copy(editingItemId = null) }
    }

    fun onEditorTextChanged(text: String) {
        setState {
            updateItem(id = requireNotNull(editingItemId)) { Product.parseFromString(text) }
        }
    }

    fun onCategorySelected(text: String) {
        if (text == "All") {
            setState {
                copy(items = repo.getAll())
            }
        } else {
            setState {
                copy(items = repo.getAllByCategory(text))
            }
        }
    }

    fun onToggleSelected(text: String) {
        if (text == "True") {
            setState {
                copy(items = repo.getProductsIsNotTheStorage())
            }
        } else if (text == "False") {
            setState {
                copy(items = repo.getProductsIsTheStorage())
            }
        } else {
            setState {
                copy(items = repo.getAll())
            }
        }
    }

    private fun RootState.updateItem(id: Int, transformer: (Product) -> Product): RootState =
            copy(items = items.updateItem(id = id, transformer = transformer))

    private fun List<Product?>.updateItem(id: Int, transformer: (Product) -> Product): List<Product?> =
            map { item -> if (item?.id == id) transformer(item) else item }

    private fun initialState(): RootState =
            RootState(
                    items = repo.getAll()
            )

    private inline fun setState(update: RootState.() -> RootState) {
        state = state.update()
    }

//    private fun RootState.filterItems(): RootState {
//        val filteredItems = items.filter { item ->
//            val categoryMatches = selectedCategory.value?.let { item?.category == it } ?: true
//            val storageMatches = filterByStorage.value?.let { item?.isOnStorage == it } ?: true
//            categoryMatches && storageMatches
//        }
//        return copy(filteredItems = filteredItems)
//    }

    data class RootState(
            val items: List<Product?> = emptyList(),
            val inputText: String = "",
            val editingItemId: Int? = null,
    )
}
