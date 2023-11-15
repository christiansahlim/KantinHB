package com.rpll.kantinhb.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rpll.kantinhb.data.repository.KantinHBRepository
import com.rpll.kantinhb.ui.screen.auth.login.LoginViewModel
import com.rpll.kantinhb.ui.screen.auth.register.RegisterViewModel
import com.rpll.kantinhb.ui.screen.cart.CartViewModel
import com.rpll.kantinhb.ui.screen.category.CategoryViewModel
import com.rpll.kantinhb.ui.screen.detail.DetailViewModel
import com.rpll.kantinhb.ui.screen.home.HomeViewModel
import com.rpll.kantinhb.ui.screen.my_favorite.MyFavoriteViewModel
import com.rpll.kantinhb.ui.screen.profile.ProfileViewModel

class ViewModelFactory(private val repository: KantinHBRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(MyFavoriteViewModel::class.java)) {
            return MyFavoriteViewModel(repository) as T
        }
//        else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
//            return LoginViewModel(repository) as T
//        }else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
//            return RegisterViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}