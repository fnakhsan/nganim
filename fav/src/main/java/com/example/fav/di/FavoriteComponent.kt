package com.example.fav.di

import android.content.Context
import com.example.fav.ui.FavoriteFragment
import com.example.nganim.di.FavoriteModuleDependencies
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {

    fun inject(fragment: FavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}