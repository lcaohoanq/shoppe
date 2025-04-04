package com.lcaohoanq.chat.domain.user

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User() {
    @Id
    @Column(name = "nick_name")
    var nickName: String = ""

    @Column(name = "full_name")
    var fullName: String = ""

    @Column(name = "status")
    var status: ActivityStatus = ActivityStatus.OFFLINE

    // Enum to represent user status
    enum class ActivityStatus {
        ONLINE, OFFLINE
    }

    // Custom toString() method for easier logging and debugging
    override fun toString(): String {
        return "User(nickName='$nickName', fullName='$fullName', status=$status)"
    }

    // Optional: If you are using Jackson, you may need to use @JsonCreator for custom deserialization
    @JsonCreator
    constructor(
        @JsonProperty("nickName") nickName: String,
        @JsonProperty("fullName") fullName: String,
        @JsonProperty("status") status: ActivityStatus
    ) : this() {
        this.nickName = nickName
        this.fullName = fullName
        this.status = status
    }
}
