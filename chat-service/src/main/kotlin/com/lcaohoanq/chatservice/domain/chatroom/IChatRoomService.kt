package com.lcaohoanq.chatservice.domain.chatroom

import java.util.*

interface IChatRoomService {
    fun getChatRoomId(
        senderId: String,
        recipientId: String,
        createNewRoomIfNotExists: Boolean
    ): String?

    fun createChatId(senderId: String, recipientId: String): String
}
