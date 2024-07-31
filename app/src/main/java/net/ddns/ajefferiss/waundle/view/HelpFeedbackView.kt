package net.ddns.ajefferiss.waundle.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.ddns.ajefferiss.waundle.R

@Composable
fun HelpFeedbackView(navController: NavController, drawerState: DrawerState) {
    val context = LocalContext.current

    WaundleScaffold(
        navController = navController,
        drawerState = drawerState,
        title = stringResource(id = R.string.help_feedback),
        showBottomBar = false
    ) {
        Column(modifier = Modifier.padding(it)) {
            Spacer(modifier = Modifier.padding(start = 5.dp))
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = stringResource(id = R.string.help_feedback_description),
                    color = Color.Black
                )
                Row(modifier = Modifier.padding(5.dp)) {
                    Button(
                        modifier = Modifier.padding(2.dp),
                        onClick = {
                            val urlIntent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://github.com/ajefferiss/waundle/issues")
                            )
                            context.startActivity(urlIntent)
                        }) {
                        Text(text = stringResource(id = R.string.github))
                    }
                    Button(
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
                        Text(text = stringResource(id = R.string.email))
                    }
                }
            }
        }
    }
}