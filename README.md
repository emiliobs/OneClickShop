# 🛍️ OneClickShop

A native Android e-commerce application built with Kotlin and Jetpack Compose.
Developed as coursework for **CS6051 - Mobile Applications** at London Metropolitan University.

> **Student:** Emilio Antonio Barrera Sepúlveda · **ID:** 22047055 · **Academic Year:** 2025–2026

---

## 📸 Screenshots

> _Screenshots will be added once the UI is complete._

| Home Screen | Categories | Product Detail |
|:-----------:|:----------:|:--------------:|
| `coming soon` | `coming soon` | `coming soon` |

| Cart | Checkout | Order History |
|:----:|:--------:|:-------------:|
| `coming soon` | `coming soon` | `coming soon` |

---

## 📱 About the App

OneClickShop is a customer-side Android shopping app that allows users to:

- Browse products fetched from a live REST API
- Search products by keyword
- Filter products by category
- View detailed product information with images
- Add products to a local shopping cart
- Complete a checkout with shipping details
- View past order history stored locally

---

## 🏗️ Architecture

The app follows **MVVM (Model-View-ViewModel)** architecture with a clean separation of layers:

```
UI (Jetpack Compose)
      ↕
ViewModel (StateFlow)
      ↕
Repository (single source of truth)
      ↕
API (Retrofit)  +  Local DB (Room)
```

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| **Kotlin** | Main programming language |
| **Jetpack Compose** | Modern declarative UI |
| **Material 3** | Design system and components |
| **Retrofit** | REST API communication |
| **Room** | Local database (cart + orders) |
| **Coil** | Async image loading from URLs |
| **Navigation Compose** | Multi-screen navigation |
| **ViewModel + StateFlow** | UI state management |
| **Coroutines** | Background operations |

---

## 🌐 API

The app connects to a custom REST API:

```
Base URL: https://oneclick.runasp.net/
```

| Endpoint | Description |
|---|---|
| `GET /api/Products` | Fetch all products |
| `GET /api/Products/{id}` | Fetch product by ID |
| `GET /api/Products/ByCategory/{id}` | Fetch products by category |
| `GET /api/Categories` | Fetch all categories |

---

## 🗄️ Local Database (Room)

Three tables stored locally on device:

| Table | Purpose |
|---|---|
| `cart_items` | Shopping cart contents |
| `orders` | Completed order records |
| `order_items` | Individual product lines per order |

---

## 📁 Project Structure

```
com.emilio.oneclickshop/
├── data/
│   ├── model/          ← Data classes (Product, Category, CartItem, Order)
│   ├── remote/         ← Retrofit + ApiService
│   ├── local/          ← Room DAOs + AppDatabase
│   └── repository/     ← ProductRepository, CartRepository, OrderRepository
├── ui/
│   ├── screens/        ← One folder per screen
│   ├── components/     ← Reusable UI components
│   └── theme/          ← Colors, typography, Material 3 theme
├── viewmodel/          ← ProductViewModel, CartViewModel, OrderViewModel
└── navigation/         ← NavGraph and route definitions
```

---

## 🗺️ Screens

1. **Home Screen** — Featured products, categories, search bar
2. **Categories Screen** — Filter products by category
3. **Product Detail Screen** — Full product info with image and add to cart
4. **Cart Screen** — Manage selected items, update quantities
5. **Checkout Screen** — Enter shipping details, place order
6. **Order History Screen** — View all past orders

---

## 🚀 How to Run

1. Clone this repository
2. Open in **Android Studio** (latest stable)
3. Wait for Gradle sync to complete
4. Run on an emulator or physical device (**API 26+**)

```bash
git clone https://github.com/YOUR_USERNAME/OneClickShop.git
```

---

## 📦 Development Progress

- [x] Paso 0 — Project setup + dependencies
- [x] Paso 1 — Data classes (models)
- [x] Paso 2 — Retrofit + API layer
- [x] Paso 3 — Room local database
- [x] Paso 4 — ViewModels
- [ ] Paso 5 — Theme + UI components
- [ ] Paso 6 — Navigation graph
- [ ] Paso 7 — Home Screen
- [ ] Paso 8 — Categories Screen
- [ ] Paso 9 — Product Detail Screen
- [ ] Paso 10 — Cart Screen
- [ ] Paso 11 — Checkout Screen
- [ ] Paso 12 — Order History Screen

---

## 📄 License

This project is developed for academic purposes at London Metropolitan University.
