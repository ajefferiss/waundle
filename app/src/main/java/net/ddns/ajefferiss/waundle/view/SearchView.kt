package net.ddns.ajefferiss.waundle.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.Screen
import net.ddns.ajefferiss.waundle.model.WaundleViewModel
import net.ddns.ajefferiss.waundle.ui.theme.WaundleTheme

@Composable
fun SearchView(
    navController: NavController,
    viewModel: WaundleViewModel,
    drawerState: DrawerState
) {
    var searchText by remember { mutableStateOf("") }
    val searchResult = viewModel.searchBy(searchText).collectAsState(initial = listOf())

    WaundleScaffold(
        navController = navController,
        drawerState = drawerState,
        title = stringResource(id = R.string.search_view)
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            SearchField(onSearchChanged = { it2 -> searchText = it2 })
            if (searchResult.value.isEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(searchResult.value, key = { hill -> hill.id }) { hill ->
                    HillItem(
                        hill = hill,
                        onClick = {
                            navController.navigate(Screen.HillDetailsScreen.route + "/${hill.id}")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SearchField(onSearchChanged: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

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
            .padding(8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun SearchFieldPreview() {
    WaundleTheme {
        SearchField(onSearchChanged = {})
    }
}