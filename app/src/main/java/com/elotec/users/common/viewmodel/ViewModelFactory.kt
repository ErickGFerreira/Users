package com.elotec.users.common.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import dagger.MapKey
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

class ViewModelFactory @Inject constructor(
    private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModels[modelClass]?.get() as T
}

class SavedStateViewModelFactory<out VM : ViewModel>(
    private val viewModelFactory: SavedStateViewModelAssistedFactory<VM>
) : ViewModelProvider.Factory {
//
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        val savedStateHandle = extras.createSavedStateHandle()
        return viewModelFactory.create(savedStateHandle) as T
    }
}

interface SavedStateViewModelAssistedFactory<VM : ViewModel> {
    fun create(handle: SavedStateHandle): VM
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Composable
inline fun <reified T : ViewModel> ViewModelProvider.Factory.composeViewModel(
    key: String? = null,
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)
): T = androidx.lifecycle.viewmodel.compose.viewModel(
    modelClass = T::class.java,
    viewModelStoreOwner = viewModelStoreOwner,
    key = key,
    factory = this
)