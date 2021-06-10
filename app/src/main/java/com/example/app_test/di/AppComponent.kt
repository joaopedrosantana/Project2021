package com.example.app_test.di

import com.example.app_test.di.DataModules.dataModules
import com.example.app_test.di.DataModules.serviceModules
import com.example.app_test.di.ViewModelModules.viewModelModules
import org.koin.core.module.Module

object AppComponent {
    fun getAllModules(): List<Module> =
        listOf(*getViewModelModules(), *getDataModules())

    private fun getViewModelModules(): Array<Module> {
        return arrayOf(viewModelModules)
    }

    private fun getDataModules(): Array<Module> {
        return arrayOf(serviceModules, dataModules)
    }
}