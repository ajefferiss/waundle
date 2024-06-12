package net.ddns.ajefferiss.waundle

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.ddns.ajefferiss.waundle.ui.theme.Green40

@Composable
fun AppBarView(title: String, onBackNavClicked: () -> Unit = {}) {

    val navigationIcon: (@Composable () -> Unit)? =
        if (title != stringResource(id = R.string.app_name)) {
            {
                IconButton(onClick = { onBackNavClicked() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
                color = Color.DarkGray
            )
        },
        elevation = 3.dp,
        navigationIcon = navigationIcon,
        backgroundColor = Green40
    )
}