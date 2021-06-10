package com.example.app_test.presentation

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinComponent

open class BaseViewModel : ViewModel(), LifecycleObserver, KoinComponent {
    val uiScope = CoroutineScope(Dispatchers.IO)
}