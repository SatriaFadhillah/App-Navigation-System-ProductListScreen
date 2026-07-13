package com.example.padilsholawat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.padilsholawat.ShopViewModel
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navController: NavController,
    viewModel: ShopViewModel = viewModel()
) {
    val selectedProduct = viewModel.selectedProduct.collectAsState()
    var showAddedToast by remember { mutableStateOf(false) }

    // Menampilkan toast selama 2 detik
    if (showAddedToast) {
        LaunchedEffect(Unit) {
            delay(2000)
            showAddedToast = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Detail") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp()}) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            selectedProduct.let { p ->
                // Konten utama dengan scroll
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Gambar produk spesifik dari resource drawable
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        color = Color(0xFFF5F5F5),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                painter = painterResource(id = p.value?.imageResId ?: 0), // Gambar spesifik per produk
                                contentDescription = p.value?.name,
                                modifier = Modifier.size(120.dp)
                            )
                        }
                    }

                    // Nama produk
                    Text(
                        text = p.value?.name ?: "",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    // Harga
                    Text(
                        text = "Rp ${formatPrice(p.value?.price ?: 0)}",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFF2E7D32),
                        fontWeight = FontWeight.Bold
                    )

                    // Horizontal divider
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = Color.Gray.copy(alpha = 0.3f)
                    )

                    // Deskripsi
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = p.value?.description ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Tombol add to cart
                    Button(
                        onClick = {
                            viewModel.addToCart(p.value!!)
                            showAddedToast = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Icon(Icons.Default.ShoppingCart, "Cart")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Add to Cart", style = MaterialTheme.typography.titleMedium)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Pesan sukses di TENGAH LAYAR (overlay)
                if (showAddedToast) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF4CAF50)
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                        ) {
                            Text(
                                text = "Added to cart!",
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                color = Color.White,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            } ?: run {
                // Tidak ada produk yang dipilih
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No product selected")
                }
            }
        }
    }
}
