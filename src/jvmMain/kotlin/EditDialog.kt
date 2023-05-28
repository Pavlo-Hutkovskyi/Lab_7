import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import data.Product

@Composable
internal fun EditDialog(
    item: Product,
    onCloseClicked: () -> Unit,
    onTextChanged: (String) -> Unit,
) {
    Dialog(
        title = "Edit product",
        onCloseRequest = onCloseClicked,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(
                value = item.toString(),
                modifier = Modifier.weight(1F).fillMaxWidth().sizeIn(minHeight = 192.dp),
                label = { Text("Todo text") },
                onValueChange = onTextChanged,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onCloseClicked,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Save")
            }
        }
    }
}

