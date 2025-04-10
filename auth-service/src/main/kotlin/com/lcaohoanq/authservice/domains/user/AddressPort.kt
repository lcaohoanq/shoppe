package com.lcaohoanq.authservice.domains.user

interface AddressPort {

    data class AddressResponse(
        val id: Long,
        val nameOfUser: String,
        val phoneNumber: String,
        val address: String,
        val isDefault: Boolean,
    )

}