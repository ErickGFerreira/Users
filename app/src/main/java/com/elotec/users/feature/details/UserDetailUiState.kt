package com.elotec.users.feature.details

import com.elotec.users.domain.model.User
import com.elotec.users.utils.EMPTY_STRING
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class UserDetailUiState @Inject constructor() {
    val presentation = MutableStateFlow(value = Presentation())

    fun showUserDetail(user: User) {
        presentation.value = user.toPresentation()
    }

    private fun User.toPresentation() =
        Presentation(
            name = name,
            username = username,
            email = email,
            street = address.street,
            suite = address.suite,
            city = address.city,
            zipcode = address.zipcode,
            lat = address.geo.lat,
            lng = address.geo.lng,
            phone = phone,
            website = website,
            companyName = company.name,
            catchphrase = company.catchPhrase,
            bs = company.bs,
        )

    data class Presentation(
        val name: String = EMPTY_STRING,
        val username: String = EMPTY_STRING,
        val email: String = EMPTY_STRING,
        val street: String = EMPTY_STRING,
        val suite: String = EMPTY_STRING,
        val city: String = EMPTY_STRING,
        val zipcode: String = EMPTY_STRING,
        val lat: String = EMPTY_STRING,
        val lng: String = EMPTY_STRING,
        val phone: String = EMPTY_STRING,
        val website: String = EMPTY_STRING,
        val companyName: String = EMPTY_STRING,
        val catchphrase: String = EMPTY_STRING,
        val bs: String = EMPTY_STRING,
    )
}