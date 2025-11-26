package com.elotec.users.feature

import com.elotec.users.common.viewmodel.FlowDataSavedStateHandle
import com.elotec.users.domain.model.User
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import com.elotec.users.feature.UsersViewModel.FlowData
import com.elotec.users.feature.UsersUiEvent.Navigation.UserDetail
import com.elotec.users.feature.UsersUiEvent.Navigation.UsersList
import com.elotec.users.feature.UsersUiEvent.Navigation.Finish
import org.junit.Assert.assertEquals

class UsersViewModelTest {
    private val navigationEvent: UsersUiEvent = mockk(relaxed = true)

    private lateinit var viewModel: UsersViewModel

    private val handle: FlowDataSavedStateHandle<FlowData> =
        mockk(relaxed = true)

    @Before
    fun setup() {
        viewModel =
            UsersViewModel(
                uiEvent = navigationEvent,
                handle = handle,
            )
    }

    @Test
    fun `when setup, then init flowData with handle value`() {
        // given
        val expected =
            FlowData(
                user = User(),
            )
        every { handle.init(default = FlowData()) } returns FlowData()
        // when
        viewModel.setup()
        // then
        assertThat(viewModel.flowData).isEqualTo(expected)
        verify(exactly = 1) { handle.init(default = FlowData()) }
    }


    @Test
    fun `given USER, when saveUser, then saves data`() {
        // when
        viewModel.saveUser(user = USER)
        // then
        verify(exactly = 1) {
            handle.save(flowData = viewModel.flowData)
        }
        assertEquals(viewModel.flowData.user, USER)
    }

    @Test
    fun `when navigate to user details, then send navigation event`() {
        // when
        viewModel.navigate(navigation = UserDetail)
        // then
        verify(exactly = 1) { navigationEvent.send(event = UserDetail) }
    }

    @Test
    fun `when navigate to finish, then send navigation event`() {
        // when
        viewModel.navigate(navigation = Finish)
        // then
        verify(exactly = 1) { navigationEvent.send(event = Finish) }
    }

    @Test
    fun `when navigate to cancel, then send navigation event`() {
        // when
        viewModel.navigate(navigation = UsersList)
        // then
        verify(exactly = 1) { navigationEvent.send(event = UsersList) }
    }

    private companion object {
        val USER = User.mockUser()
    }

}
