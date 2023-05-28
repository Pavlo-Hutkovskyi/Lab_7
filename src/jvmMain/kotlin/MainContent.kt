import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import data.Product


@Composable
internal fun MainContent(
    modifier: Modifier = Modifier,
    items: List<Product?>,
    inputText: String,
    onItemClicked: (id: Int?) -> Unit,
    onItemDeleteClicked: (id: Int?) -> Unit,
    onAddItemClicked: () -> Unit,
    onInputTextChanged: (String) -> Unit,
    onCategorySelected: (String) -> Unit,
    onToggleSelected: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Product List") },
                    actions = {
                        CategoryDropdown(items = items, onCategorySelected = onCategorySelected)
                        ToggleDropdown(onToggleSelected = onToggleSelected)
                    }
            )
        }
    ) {
        Column(modifier) {
            Box(Modifier.weight(1F)) {
                ListContent(
                    items = items,
                    onItemClicked = onItemClicked,
                    onItemDeleteClicked = onItemDeleteClicked
                )
            }

            Input(
                text = inputText,
                onAddClicked = onAddItemClicked,
                onTextChanged = onInputTextChanged
            )
        }
    }
}

@Composable
private fun CategoryDropdown(items: List<Product?>,
                             onCategorySelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    val uniqueCategories = repo.getAll().mapNotNull { it?.category }.distinct()

    Box(modifier = Modifier.wrapContentSize()) {
        TextButton(
                onClick = { expanded = true },
                modifier = Modifier.padding(end = 8.dp).background(Color.White)
        ) {
            Text(text = "Categories")
        }

        DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
        ) {
            CompositionLocalProvider(LocalContentColor provides Color.Black) {
                (listOf("All") + uniqueCategories).forEach { category ->
                    DropdownMenuItem(
                            onClick = {
                                onCategorySelected(category)
                                expanded = false
                            }
                    ) {
                        Text(text = category, color = Color.Black)
                    }
                }
            }
        }
    }
}

@Composable
private fun ToggleDropdown(onToggleSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.wrapContentSize()) {
        TextButton(
                onClick = { expanded = true },
                modifier = Modifier.padding(end = 8.dp).background(Color.White)
        ) {
            Text(text = "Toggle")
        }

        DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
        ) {
            CompositionLocalProvider(LocalContentColor provides Color.Black) {
                DropdownMenuItem(
                        onClick = {
                            onToggleSelected("True")
                            expanded = false
                        }
                ) {
                    Text(text = "True", color = Color.Black)
                }
                DropdownMenuItem(
                        onClick = {
                            onToggleSelected("False")
                            expanded = false
                        }
                ) {
                    Text(text = "False", color = Color.Black)
                }
                DropdownMenuItem(
                        onClick = {
                            onToggleSelected("All")
                            expanded = false
                        }
                ) {
                    Text(text = "All", color = Color.Black)
                }
            }
        }
    }
}

@Composable
private fun ListContent(
    items: List<Product?>,
    onItemClicked: (id: Int?) -> Unit,
    onItemDeleteClicked: (id: Int?) -> Unit,
) {
    Box {
        val listState = rememberLazyListState()

        LazyColumn(state = listState) {
            items(items) { item ->
                if (item != null) {
                    Item(
                        item = item,
                        onClicked = { onItemClicked(item.id) },
                        onDeleteClicked = { onItemDeleteClicked(item.id) }
                    )
                }

                Divider()
            }
        }

        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(scrollState = listState)
        )
    }
}

@Composable
private fun Item(
    item: Product,
    onClicked: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    Row(modifier = Modifier.clickable(onClick = onClicked)) {
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = AnnotatedString(item.toString()),
            modifier = Modifier.weight(1F).align(Alignment.CenterVertically),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = onDeleteClicked) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.width(10.dp))
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Input(
    text: String,
    onTextChanged: (String) -> Unit,
    onAddClicked: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
        OutlinedTextField(
            value = text,
            modifier = Modifier
                .weight(weight = 1F),
            onValueChange = onTextChanged,
            label = { Text(text = "Add a todo") }
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = onAddClicked) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
    }
}
