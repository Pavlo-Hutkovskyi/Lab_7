import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import data.Product

@Composable
fun RootContent(modifier: Modifier = Modifier) {
    val model = remember { RootStore() }
    val state = model.state

    MainContent(
        modifier = modifier,
        items = state.items,
        inputText = state.inputText,
        onItemClicked = model::onItemClicked,
        onItemDeleteClicked = model::onItemDeleteClicked,
        onAddItemClicked = model::onAddItemClicked,
        onInputTextChanged = model::onInputTextChanged,
        onCategorySelected = model::onCategorySelected,
        onToggleSelected = model::onToggleSelected,
    )

    state.editingItem?.also { item ->
        EditDialog(
            item = item,
            onCloseClicked = model::onEditorCloseClicked,
            onTextChanged = model::onEditorTextChanged,
        )
    }
}

private val RootStore.RootState.editingItem: Product?
    get() = editingItemId?.let(items::firstById)

private fun List<Product?>.firstById(id: Int): Product? =
    firstOrNull { it?.id == id }
