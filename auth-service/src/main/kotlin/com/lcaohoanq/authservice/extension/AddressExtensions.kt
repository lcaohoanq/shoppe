package com.lcaohoanq.authservice.extension

import com.lcaohoanq.authservice.domains.user.Address
import com.lcaohoanq.common.dto.AddressPort

fun Address.toAddressResponse(): AddressPort.AddressResponse {
    return AddressPort.AddressResponse(
        id = this.id!!,
        nameOfUser = this.nameOfUser,
        phoneNumber = this.phoneNumber,
        address = this.address,
        isDefault = this.isDefault
    )
}

fun Set<Address>?.toAddressResponseSet(): Set<AddressPort.AddressResponse>? {
    return this?.map { it.toAddressResponse() }?.toSet()
}
