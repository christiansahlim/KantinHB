package com.rpll.kantinhb.navigation

sealed class KantinHBScreen (val route: String) {
    object HomeScreen: KantinHBScreen("home_screen")
    object DetailScreen: KantinHBScreen("detail_screen")
    object CartScreen: KantinHBScreen("cart_screen")
    object ProfileScreen: KantinHBScreen("profile_screen")
    object CategoryScreen: KantinHBScreen("category_screen")
    object SuccessAddToCartScreen: KantinHBScreen("success_add_to_cart_screen")
    object FavoriteStatusScreen: KantinHBScreen("favorite_status_screen")
    object MyFavoriteScreen: KantinHBScreen("my_favorite_screen")
}
