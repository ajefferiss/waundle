package net.ddns.ajefferiss.waundle.view

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.Screen
import net.ddns.ajefferiss.waundle.ui.theme.Background40

@Composable
fun AppBarView(
    title: String,
    navController: NavController
) {

    val navigationIcon: (@Composable () -> Unit)? =
        if (title != stringResource(id = R.string.app_name)) {
            {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_navigation_arrow)
                    )
                }
            }
        } else {
            null
        }

    TopAppBar(
        title = {
            Text(
                text = title,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 24.dp),
                color = Color.Black
            )
        },
        elevation = 3.dp,
        navigationIcon = navigationIcon,
        backgroundColor = Background40,
        actions = {
            IconButton(onClick = { navController.navigate(Screen.SearchScreen.route) }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(id = R.string.search_navigation_icon)
                )
            }
            IconButton(onClick = { /* TODO */ }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = stringResource(id = R.string.more_options_navigation_icon)
                )
            }
        }
    )
}