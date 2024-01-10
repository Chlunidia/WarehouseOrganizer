package com.example.warehouseorganizer.ui.add

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.warehouseorganizer.navigation.NavigationDestination
import com.example.warehouseorganizer.ui.ViewModelProviderFactory
import kotlinx.coroutines.launch
import java.io.IOException

object DestinasiEntry : NavigationDestination {
    override val route = "item_entry"
    override val titleRes = "Entry"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    navigateBack: () -> Unit,
    context: Context,
    modifier: Modifier = Modifier,
    addViewModel: AddViewModel = viewModel(factory = ViewModelProviderFactory.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            WarehouseOrganizerTopAppBar(
                title = DestinasiEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            addUIState = addViewModel.addUIState,
            onItemValueChange = addViewModel::updateAddUIState,
            onSaveClick = { bitmap, name, rack, quantity ->
                coroutineScope.launch {
                    if (bitmap != null) {
                        addViewModel.saveItem(bitmap, name, rack, quantity)
                        navigateBack()
                        Toast.makeText(context, "Data Added", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )

    }
}

@Composable
fun EntryBody(
    addUIState: AddUIState,
    onItemValueChange: (AddEvent) -> Unit,
    onSaveClick: (Bitmap?, String, String, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            addEvent = addUIState.addEvent,
            onValueChange = onItemValueChange,
            onSaveClick = onSaveClick,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                onSaveClick(
                    addUIState.addEvent.imageBitmap,
                    addUIState.addEvent.name,
                    addUIState.addEvent.rack,
                    addUIState.addEvent.quantity
                )
            },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    addEvent: AddEvent,
    onSaveClick: (Bitmap?, String, String, Int) -> Unit,
    modifier: Modifier = Modifier,
    onValueChange: (AddEvent) -> Unit = {}
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<String?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    val launcherGallery = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            imageUri = uri.toString()
            imageUri?.let {
                bitmap = context.getBitmapFromUri(Uri.parse(it))
                onValueChange(addEvent.copy(imageBitmap = bitmap)) // Update AddEvent with Bitmap
            }
        }
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(
                shape = RoundedCornerShape(15.dp)
            )
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(15.dp)
            )
            .clickable {
                launcherGallery.launch("image/*")
            }
        ) {
            if (bitmap != null) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    bitmap = bitmap!!.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(text = "Click To Add Image", modifier = Modifier.align(Alignment.Center))
            }
        }
        OutlinedTextField(
            value = addEvent.name,
            onValueChange = { onValueChange(addEvent.copy(name = it)) },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = addEvent.rack,
            onValueChange = { onValueChange(addEvent.copy(rack = it)) },
            label = { Text("Rack") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = addEvent.quantity.toString(),
            onValueChange = { onValueChange(addEvent.copy(quantity = it.toIntOrNull() ?: 0)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Quantity") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = addEvent.quantity.toString().isNotEmpty() && !addEvent.quantity.toString()
                .matches(Regex("\\d+"))
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarehouseOrganizerTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = ""
                    )
                }
            }
        }
    )
}

fun Context.getBitmapFromUri(uri: Uri): Bitmap? {
    return try {
        val inputStream = contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}