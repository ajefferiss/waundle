package net.ddns.ajefferiss.waundle.view

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.ui.theme.WaundleTheme

@Composable
fun SearchView(navController: NavController, viewModel: WaundleViewModel) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    fun onSearchChanged(searchText: String) {
        Toast.makeText(context, "Got: $searchText", Toast.LENGTH_LONG).show()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarView(
                title = stringResource(id = R.string.search_view),
                navController = navController
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            SearchField(onSearchChanged = { searchText -> onSearchChanged(searchText) })
        }
    }
}

@Composable
fun SearchField(onSearchChanged: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    var context = LocalContext.current

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            if (it.length >= 2) {
                onSearchChanged(it)
            }
        },
        singleLine = true,
        label = {
            Text(text = stringResource(id = R.string.search_field_label))
        },
        placeholder = {
            Text(text = stringResource(id = R.string.search_field_placeholder))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search_navigation_icon)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            focusedBorderColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = MaterialTheme.colors.secondary
        )
    )
}

@Preview(showBackground = true)
@Composable
fun SearchFieldPreview() {
    WaundleTheme {
        SearchField(onSearchChanged = {})
    }
}