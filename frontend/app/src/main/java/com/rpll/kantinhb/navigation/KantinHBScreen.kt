package com.rpll.kantinhb.navigation

sealed class KantinHBScreen (val route: String) {
    object RegisterScreen: KantinHBScreen("register_screen")
    object LoginScreen: KantinHBScreen("login_screen")
    object HomeScreen: KantinHBScreen("home_screen")
    object DetailScreen: KantinHBScreen("detail_screen")
    object CartScreen: KantinHBScreen("cart_screen")
    object PaymentScreen: KantinHBScreen("payment_screen")
    object ProfileScreen: KantinHBScreen("profile_screen")
    object CategoryScreen: KantinHBScreen("category_screen")
    object SuccessAddToCartScreen: KantinHBScreen("success_add_to_cart_screen")
    object SuccessPayment: KantinHBScreen("success_payment_screen")
    object FavoriteStatusScreen: KantinHBScreen("favorite_status_screen")
    object MyFavoriteScreen: KantinHBScreen("my_favorite_screen")

}
