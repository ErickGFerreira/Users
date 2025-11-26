package com.elotec.users.inject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elotec.users.common.viewmodel.ViewModelFactory
import com.elotec.users.common.viewmodel.ViewModelKey
import com.elotec.users.feature.details.UserDetailViewModel
import com.elotec.users.feature.list.UsersListViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(UsersListViewModel::class)
    internal abstract fun bindUsersListViewModel(
        viewModel: UsersListViewModel,
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserDetailViewModel::class)
    internal abstract fun bindUserDetailViewModel(
        viewModel: UserDetailViewModel,
    ): ViewModel
}