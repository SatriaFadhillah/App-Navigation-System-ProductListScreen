        package com.example.padilsholawat
    
        import androidx.compose.foundation.layout.*
        import androidx.compose.foundation.lazy.LazyColumn
        import androidx.compose.foundation.lazy.items
        import androidx.compose.foundation.rememberScrollState
        import androidx.compose.foundation.verticalScroll
        import androidx.compose.material.icons.Icons
        import androidx.compose.material.icons.automirrored.filled.ArrowBack
        import androidx.compose.material.icons.filled.Delete
        import androidx.compose.material3.*
        import androidx.compose.runtime.*
        import androidx.compose.runtime.collectAsState
        import androidx.compose.runtime.getValue
        import androidx.compose.ui.Alignment
        import androidx.compose.ui.Modifier
        import androidx.compose.ui.graphics.Color
        import androidx.compose.ui.res.painterResource
        import androidx.compose.ui.text.font.FontWeight
        import androidx.compose.ui.unit.dp
        import androidx.lifecycle.viewmodel.compose.viewModel
        import androidx.navigation.NavController
    
        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun CartScreen(
            navController: NavController,
            viewModel: ShopViewModel = viewModel()
        ) {
            // Mengambil data cart items dari ViewModel secara reactive
            val cartItems by viewModel.CarItem.collectAsState()
            val totalPrice = viewModel.getTotalPrice()
    
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Shopping Cart") },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = Color.White,
                            navigationIconContentColor = Color.White
                        ),
                        navigationIcon = {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    "Back"
                                )
                            }
                        }
                    )
                }
            ) { paddingValues ->
                if (cartItems.isEmpty()) {
                    // Tampilan keranjang kosong
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Menggunakan icon cart.png untuk keranjang kosong
                            Icon(
                                painter = painterResource(id = R.drawable.troli),
                                contentDescription = "Empty Cart",
                                modifier = Modifier.size(80.dp),
                                tint = Color.Gray
                            )
                            Text(
                                "Your cart is empty",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Button(onClick = { navController.navigateUp() }) {
                                Text("Continue Shopping")
                            }
                        }
                    }
                } else {
                    // Keranjang dengan item - Menambahkan scroll pada seluruh konten
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Daftar item keranjang
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(
                                items = cartItems,
                                key = { item -> item.product.id }
                            ) { cartItem ->
                                CartItemCard(
                                    cartItem = cartItem,
                                    onRemove = { viewModel.removeFromCart(cartItem.product.id) }
                                )
                            }
                        }
    
                        // Total dan checkout
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shadowElevation = 8.dp
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(32.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        "Total:",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        "Rp ${formatPrice(totalPrice)}",
                                        style = MaterialTheme.typography.titleLarge,
                                        color = Color(0xFF2E7D32),
                                        fontWeight = FontWeight.Bold
                                    )
                                }
    
                                Button(
                                    onClick = { /* TODO: Checkout - perlu implementasi autentikasi */ },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.troli),
                                        contentDescription = "Checkout",
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Checkout", style = MaterialTheme.typography.titleMedium)
                                }
                            }
                        }
                    }
                }
            }
        }
    
        /**
         * CartItemCard - Komponen untuk menampilkan satu item di keranjang
         * @param cartItem: CartItem data item keranjang (produk dan quantity)
         * @param onRemove: Callback saat tombol hapus ditekan
         */
        @Composable
        fun CartItemCard(
            cartItem: CarItem,
            onRemove: () -> Unit
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Gambar produk spesifik dari resource drawable
                    Surface(
                        modifier = Modifier.size(60.dp),
                        color = Color(0xFFF0F0F0),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                painter = painterResource(id = cartItem.product.imageResId), // Gambar spesifik per produk
                                contentDescription = cartItem.product.name,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
    
                    // Informasi produk
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = cartItem.product.name,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Rp ${formatPrice(cartItem.product.price)}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF2E7D32)
                        )
                        Text(
                            text = "Qty: ${cartItem.quantity}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
    
                    // Tombol hapus
                    IconButton(onClick = onRemove) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Remove",
                            tint = Color(0xFFE53935)
                        )
                    }
                }
            }
        }
