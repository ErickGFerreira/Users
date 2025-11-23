package com.elotec.users.common.view

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.elotec.users.common.viewmodel.ViewModelFactory
import com.elotec.users.common.viewmodel.composeViewModel
import javax.inject.Inject

abstract class BaseComposeActivity<VM : ViewModel> : BaseActivity() {

    /**
     * Factory responsavel pela criação das ViewModels do fluxo.
     * Pode ser responsavel pela FlowViewModel se o método createFlowViewModelFactory() não for sobrescrito.
     */
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val _flowViewModelFactory: ViewModelProvider.Factory by lazy { createFlowViewModelFactory() }

    private lateinit var _flowViewModel: VM
    protected val flowViewModel get() = _flowViewModel

    protected val viewModelStoreOwner: ViewModelStoreOwner get() = this

    abstract fun viewModelClass(): Class<VM>

    /**
     * Método responsavel por criar o factory da FlowViewModel. Pode ser sobrescrito para customização.
     * Exemplo:
     * Caso seja interesse do fluxo usar o componente SaveStateHandle, para isso deve-se criar um factory customizado.
     */
    protected open fun createFlowViewModelFactory(): ViewModelProvider.Factory = viewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createFlowViewModel()
    }

    /**
     * Tenta buscar no provider uma viewModel já criada, caso contrario, cria usando o factory.
     */
    private fun createFlowViewModel() = try {
        _flowViewModel = ViewModelProvider(this)[viewModelClass()]
    } catch (ex: Exception) {
        _flowViewModel = ViewModelProvider(this, _flowViewModelFactory)[viewModelClass()]
    }

    /**
     * Cria uma ViewModel com ciclo de vida de uma tela compose.
     */
    @Composable
    protected inline fun <reified T : ViewModel> composeViewModel() =
        viewModelFactory.composeViewModel<T>()

    /**
     * Cria uma ViewModel com ciclo de vida da activity.
     * Dessa forma é possível compartilhar a VM entre 2 ou mais telas compose.
     */
    @Composable
    protected inline fun <reified T : ViewModel> sharedComposeViewModel() =
        viewModelFactory.composeViewModel<T>(viewModelStoreOwner = viewModelStoreOwner)
}
