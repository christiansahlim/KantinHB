package com.rpll.kantinhb.di

import com.rpll.kantinhb.data.repository.KantinHBRepository

object Injection {
    fun provideRepository(): KantinHBRepository {
        return KantinHBRepository.getInstance()
    }
}