package net.ddns.ajefferiss.waundle.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.ddns.ajefferiss.waundle.R

@Composable
fun HelpFeedbackView(navController: NavController, drawerState: DrawerState) {
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            AppBarView(
                title = stringResource(id = R.string.settings),
                navController = navController,
                drawerState = drawerState
            )
        },
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Spacer(modifier = Modifier.padding(start = 5.dp))
            Column(modifier = Modifier.padding(start = 16.dp)) {
                WaundleTextField(text = stringResource(id = R.string.help_feedback_description))
                Row(modifier = Modifier.padding(5.dp)) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.padding(2.dp),
                        onClick = {
                            val urlIntent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://github.com/ajefferiss/waundle/issues")
                            )
                            if (urlIntent.resolveActivity(context.packageManager) != null) {
                                context.startActivity(urlIntent)
                            }

                        }) {
                        WaundleTextField(text = stringResource(id = R.string.github))
                    }
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.padding(2.dp),
                        onClick = {
                            val emailIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "*/*"
                                putExtra(Intent.EXTRA_EMAIL, "adam.jefferiss@gmail.com")
                                putExtra(Intent.EXTRA_SUBJECT, "Waundle Support")
                            }
                            if (emailIntent.resolveActivity(context.packageManager) != null) {
                                context.startActivity(emailIntent)
                            }

                        }
                    ) {
                        WaundleTextField(text = stringResource(id = R.string.email))
                    }
                }
            }
        }
    }
}