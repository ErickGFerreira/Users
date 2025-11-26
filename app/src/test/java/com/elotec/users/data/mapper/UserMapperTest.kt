package com.elotec.users.data.mapper

import com.elotec.users.data.local.entity.UserEntity
import com.elotec.users.data.mapper.UserDataMapper.toDomain
import com.elotec.users.data.mapper.UserEntityMapper.toDomain
import com.elotec.users.data.response.UserResponse
import com.elotec.users.domain.model.User
import org.junit.Assert.assertEquals
import org.junit.Test

class CreditCardOptionMapperTest {
    @Test
    fun `given a user response, when toDomain called, then map to user`() {
        // given
        val response = UserResponse.mockUser()
        val expectedResult = User.mockUser()

        // when
        val result = response.toDomain()

        // then
        assertEquals(expectedResult, result)
    }

    @Test
    fun `given a user entity, when toDomain, then map to user`() {
        // given
        val entity = UserEntity.mockUser()
        val expectedResult = User.mockUser()

        // when
        val result = entity.toDomain()

        // then
        assertEquals(expectedResult, result)
    }
}