package com.rpll.kantinhb.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rpll.kantinhb.model.ProductItem
import com.rpll.kantinhb.ui.screen.home.HomeScreen
import com.rpll.kantinhb.ui.screen.detail.DetailScreen
import com.rpll.kantinhb.ui.screen.profile.ProfileScreen
import com.rpll.kantinhb.ui.screen.category.CategoryScreen
import com.rpll.kantinhb.ui.screen.cart.CartScreen
import com.rpll.kantinhb.ui.screen.successAddToCart.SuccessAddToCart
import com.rpll.kantinhb.ui.screen.favorite.FavoriteStatus
import com.rpll.kantinhb.ui.screen.my_favorite.MyFavoriteScreen

@Composable
fun NavigationBuilder() {
    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = KantinHBScreen.HomeScreen.route){
        composable(route = KantinHBScreen.HomeScreen.route){ previousBackStackEntry ->
            val addedNewItem = previousBackStackEntry.arguments?.getBoolean("addedNewItem")
            if (addedNewItem != null){
                HomeScreen(navController = navigationController, isAddedNewItem = addedNewItem)
            }else {
                HomeScreen(navController = navigationController)
            }
        }
        composable(route = KantinHBScreen.DetailScreen.route){ previousBackStackEntry ->
            val product = previousBackStackEntry.arguments?.getParcelable<ProductItem>("product")
            if (product != null) {
                DetailScreen(
                    navController = navigationController,
                    product = product
                )
            }
        }
        composable(route = KantinHBScreen.ProfileScreen.route){
            ProfileScreen(navController = navigationController)
        }
        composable(route = KantinHBScreen.CategoryScreen.route){ previousBackStackEntry ->
            val selectedCategoryId = previousBackStackEntry.arguments?.getInt("categoryId")
            if (selectedCategoryId != null) {
                CategoryScreen(navController = navigationController, selectedCategoryId)
            }
        }
        composable(route = KantinHBScreen.CartScreen.route){
            CartScreen(navController = navigationController)
        }
        composable(route = KantinHBScreen.SuccessAddToCartScreen.route){ previousBackStackEntry ->
            val isUpdate = previousBackStackEntry.arguments?.getBoolean("isUpdate")
            SuccessAddToCart(navController = navigationController, isUpdate = isUpdate)
        }
        composable(route = KantinHBScreen.FavoriteStatusScreen.route){ previousBackStackEntry ->
            val isAdd = previousBackStackEntry.arguments?.getBoolean("isAdd")
            if (isAdd != null) {
                FavoriteStatus(navController = navigationController, isAdd = isAdd)
            }
        }
        composable(route = KantinHBScreen.MyFavoriteScreen.route){
            MyFavoriteScreen(navController = navigationController)
        }
    }
}
