package com.elotec.users.common.viewmodel

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.savedstate.SavedStateRegistryOwner
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
    private val viewModelFactory: SavedStateViewModelAssistedFactory<VM>,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T = viewModelFactory.create(handle) as T

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