package net.ddns.ajefferiss.waundle.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.Screen

@Composable
fun HomeView(
    navController: NavController,
    viewModel: WaundleViewModel
) {
    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            AppBarView(
                title = stringResource(id = R.string.app_name),
                navController = navController
            )
        },
        containerColor = Color.White
    ) {

        val walkedHills = viewModel.walkedHills.collectAsState(
            initial = listOf()
        )
        val showProgressBar = viewModel.loading.collectAsState(initial = true)

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

            if (walkedHills.value.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.no_walked_hills),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    textAlign = TextAlign.Center
                )
            } else {
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
}
