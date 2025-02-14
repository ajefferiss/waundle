package net.ddns.ajefferiss.waundle.view

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.model.WaundleViewModel
import net.ddns.ajefferiss.waundle.util.parseHillBaggingCSV
import java.io.IOException
import java.time.format.DateTimeParseException


@Composable
fun ImportExportView(
    navController: NavController,
    viewModel: WaundleViewModel,
    drawerState: DrawerState,
    context: Context
) {
    val logTag = "ImportExportView"

    val csvFilePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { uri ->
            if (uri.data != null && uri.data!!.data != null) {
                var importMessage = ""
                try {
                    val data = parseHillBaggingCSV(uri.data!!.data!!, context)

                    data.forEach { hill ->
                        viewModel.markHillWalked(hill.hillNumber, hill.climbed)
                    }
                    viewModel.refreshWalkedHills()
                    importMessage = "Hills imported successfully"
                } catch (ioe: IOException) {
                    Log.d(logTag, "Filed to read file for import", ioe)
                    importMessage = "Failed to read the import file"
                } catch (dfe: DateTimeParseException) {
                    Log.d(logTag, "Failed to parse walked dates", dfe)
                    importMessage = "Failed to convert date of walked hill"
                }

                Toast.makeText(context, importMessage, Toast.LENGTH_LONG).show()
            }
        }
    )

    WaundleScaffold(
        navController = navController,
        drawerState = drawerState,
        title = stringResource(id = R.string.import_export)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Text(
                text = stringResource(id = R.string.import_hills),
                style = MaterialTheme.typography.titleLarge
            )
            Text(text = stringResource(id = R.string.import_description))
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "*/*"
                    }
                    csvFilePicker.launch(intent)
                },
                modifier = Modifier
                    .padding(2.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = stringResource(id = R.string.import_hills))
            }

            Text(
                text = stringResource(id = R.string.export_hills),
                style = MaterialTheme.typography.titleLarge
            )
            Text(text = stringResource(id = R.string.export_description))
            Button(
                onClick = {
                    Toast.makeText(context, "TODO", Toast.LENGTH_LONG).show()
                },
                modifier = Modifier
                    .padding(2.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = stringResource(id = R.string.export_hills))
            }
        }
    }
}
