package net.ddns.ajefferiss.waundle

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.flowOf
import net.ddns.ajefferiss.waundle.data.Hill
import net.ddns.ajefferiss.waundle.view.WaundleViewModel
import java.time.LocalDate

@Composable
fun HomeView(navController: NavController, viewModel: WaundleViewModel) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarView(
                title = stringResource(id = R.string.app_name)
            )
        }
    ) {
//        val walkedHills = viewModel.walkedHills.collectAsState(
//            initial = listOf()
//        )
        val walkedHills = flowOf(
            listOf(
                Hill(id = 1, name = "Test Hil", climbed = LocalDate.now()),
                Hill(id = 2, name = "Another Hill", climbed = LocalDate.of(2023, 12, 1)),
                Hill(id = 3, name = "Final Hill", climbed = LocalDate.of(2024, 3, 24))
            )
        ).collectAsState(initial = listOf())

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            items(walkedHills.value, key = { hill -> hill.id }) { hill ->
                HillItem(hill = hill, onClick = {})
            }
        }
    }
}

@Composable
fun HillItem(hill: Hill, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .clickable { onClick() },
        elevation = 10.dp,
        backgroundColor = Color.White
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = hill.name, fontWeight = FontWeight.ExtraBold)
            Text(text = "Walked on " + hill.climbed.toString())
        }
    }
}