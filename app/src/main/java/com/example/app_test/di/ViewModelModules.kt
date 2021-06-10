package com.example.app_test.di

import com.example.app_test.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModules {
    val viewModelModules = module {
        viewModel { MainViewModel(get()) }
    }
}