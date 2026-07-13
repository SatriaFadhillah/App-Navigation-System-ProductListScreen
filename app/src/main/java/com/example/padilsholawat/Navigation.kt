package com.example.padilsholawat

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: ShopViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = "productList" // Screen pertama yang muncul
    ) {
        composable("productList") {
            ProductListScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable("productDetail") {
            ProductDetailScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable("cart") {
            CartScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}