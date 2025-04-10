package com.lcaohoanq.authservice.enums

class UserEnum {

    enum class Status {
        UNVERIFIED, VERIFIED, BLOCKED
    }

    enum class Role {
        MEMBER, STAFF, ADMIN
    }

    enum class Gender {
        MALE, FEMALE, OTHERS
    }

}