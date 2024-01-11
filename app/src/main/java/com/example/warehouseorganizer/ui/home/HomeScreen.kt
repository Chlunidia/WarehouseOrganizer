package com.example.warehouseorganizer.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.warehouseorganizer.R
import com.example.warehouseorganizer.model.Item
import com.example.warehouseorganizer.navigation.NavigationDestination
import com.example.warehouseorganizer.ui.ViewModelProviderFactory
import com.example.warehouseorganizer.ui.add.WarehouseOrganizerTopAppBar

object DestinasiHome : NavigationDestination {
    override val route = "home"
    override val titleRes = "Item"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Item) -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = ViewModelProviderFactory.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            WarehouseOrganizerTopAppBar(
                title = "Home",
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = ""
                )
            }
        },
    ) { innerPadding ->
        val uiStateItem by viewModel.homeUIState.collectAsState()

        val filteredItems = if (searchQuery.isNotBlank()) {
            uiStateItem.listItems.filter { item ->
                item.name.contains(searchQuery, ignoreCase = true) ||
                        item.rack.contains(searchQuery, ignoreCase = true)
            }
        } else {
            uiStateItem.listItems
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                },
                label = { Text("Search by name or rack") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            if (filteredItems.isEmpty()) {
                Text(
                    text = "No items available",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
            } else {
                ListItem(
                    items = filteredItems,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    onItemClick = onDetailClick
                )
            }
        }
    }
}

@Composable
fun BodyHome(
    items: List<Item>,
    modifier: Modifier = Modifier,
    onItemClick: (Item) -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (items.isEmpty()) {
            Text(
                text = "No items available",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            ListItem(
                items = items,
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun ListItem(
    items: List<Item>,
    modifier: Modifier = Modifier,
    onItemClick: (Item) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items, key = { it.id }) { item ->
            ItemCard(
                item = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(item) }
            )
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun ItemCard(
    item: Item,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
        ) {
            val painter = rememberImagePainter(
                data = item.imageUrl,
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.img)
                }
            )
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .width(120.dp)
                    .height(120.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = item.rack,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = item.quantity.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}