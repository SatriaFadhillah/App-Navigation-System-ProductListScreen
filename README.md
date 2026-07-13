<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin">
  <img src="https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpack-compose&logoColor=white" alt="Jetpack Compose">
  <img src="https://img.shields.io/badge/Material_3-757575?style=for-the-badge&logo=material-design&logoColor=white" alt="Material 3">
  <img src="https://img.shields.io/badge/Architecture-MVVM-4CAF50?style=for-the-badge" alt="MVVM">
</p>

# PadilSHOLAWAT

A mini Android e-commerce app for premium, gold-themed Islamic worship accessories (prayer mats, prayer beads, robes, turbans). Built with Kotlin, Jetpack Compose, and MVVM, featuring a product catalog, product details, and a working shopping cart with adaptive navigation.

---

## Tech Stack

- **Kotlin** + **Jetpack Compose** — declarative UI
- **MVVM Architecture** — View / ViewModel / Model separation
- **StateFlow** — reactive, unidirectional state management
- **Navigation Compose** — `NavHost` + `NavController` routing
- **NavigationSuiteScaffold** — adaptive nav (bottom bar on phone, rail on tablet)
- **Material Design 3** — theming, dynamic color, edge-to-edge UI

---

## Architecture & Flow

```
MainActivity → NavigationSuiteScaffold (Home / Favorite / Profile)
                        │
                        ▼
              ProductListScreen (catalog, 4 items)
                   │              │
                   ▼              ▼
        ProductDetailScreen   CartScreen
        (Add to Cart)         (items, total, checkout)
```

- **`Product.kt`** — data model: `Product` (id, name, price, description, image) and `CarItem` (product + quantity, cart line item)
- **`ShopViewModel.kt`** — single source of truth. Holds `selectedProduct` and `carItems` as `StateFlow`, plus `addToCart()`, `removeFromCart()`, `getTotalPrice()`, and `getCartItemCount()`. `addToCart()` checks if the product already exists in the cart — increments quantity if so, otherwise appends a new item.
- **`Navigation.kt`** — one shared `ShopViewModel` instance across 3 routes (`productList`, `productDetail`, `cart`) via `viewModel()`, so cart state stays consistent app-wide.
- **`ProductListScreen.kt`** — `LazyColumn` catalog with a live cart-count badge in the `TopAppBar`; tapping a card sets `selectedProduct` and navigates to the detail screen.
- **`ProductDetailScreen.kt`** — shows product info + "Add to Cart", which triggers a self-dismissing "Added to cart!" toast (`LaunchedEffect` + `delay(2000)`).
- **`CartScreen.kt`** — lists cart items with remove actions and a running total; empty and populated states are handled separately. Checkout is currently a placeholder.

**Why MVVM + StateFlow:** UI recomposes automatically via `collectAsState()`, business logic stays out of Composables, and `ViewModel` survives configuration changes (e.g. screen rotation) while `rememberSaveable` handles local UI state like the active nav tab.

---

## Features

| Feature | Status |
|---|---|
| Product catalog & detail pages | ✅ Done |
| Add / remove cart items, live total & badge | ✅ Done |
| Adaptive navigation, edge-to-edge UI | ✅ Done |
| Checkout | 🚧 Placeholder |
| Favorites / Profile tabs | 🚧 Not implemented |
| Auth, persistence, remote API | ❌ Not implemented — data is hardcoded |

---

## Getting Started

```bash
git clone https://github.com/SatriaFadhillah/PadilSHOLAWAT.git
```
Open in Android Studio, let Gradle sync, then run on an emulator or device.

---

## License

MIT — feel free to use, modify, and distribute as needed.
