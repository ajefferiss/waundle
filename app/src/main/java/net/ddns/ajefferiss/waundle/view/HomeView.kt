package net.ddns.ajefferiss.waundle.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.Screen
import net.ddns.ajefferiss.waundle.data.Hill

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

        val walkedHills = viewModel.walkedHills.collectAsState(
            initial = listOf()
        )
        val showProgressBar = viewModel.loading.collectAsState(initial = false)

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.walked_hills_description),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                fontWeight = FontWeight.SemiBold
            )

            if (showProgressBar.value) {
                CircularProgressIndicator(modifier = Modifier.width(64.dp))
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                items(walkedHills.value, key = { hill -> hill.id }) { hill ->
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