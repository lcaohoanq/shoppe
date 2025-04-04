package com.lcaohoanq.chatservice.domain.chatroom

import org.springframework.stereotype.Service

@Service
class ChatRoomService(
    private val chatRoomRepository: ChatRoomRepository,
) : IChatRoomService {

    override fun getChatRoomId(
        senderId: String,
        recipientId: String,
        createNewRoomIfNotExists: Boolean
    ): String? {
        // Try to find the chat room by sender and recipient IDs
        val chatRoom = chatRoomRepository.findBySenderIdAndRecipientId(senderId, recipientId)

        // If found, return the chat ID
        return chatRoom?.chatId ?: if (createNewRoomIfNotExists) {
            // If not found and a new room is requested, create and return the new chat ID
            createChatId(senderId, recipientId)
        } else {
            // Return null if the chat room doesn't exist and no new room should be created
            null
        }
    }
    
    override fun createChatId(senderId: String, recipientId: String): String {
        val chatId = String.format("%s_%s", senderId, recipientId)

        chatRoomRepository.save(
            ChatRoom(
                chatId = chatId,
                senderId = senderId,
                recipientId = recipientId
            )
        )
        
        chatRoomRepository.save(
            ChatRoom(
                chatId = chatId,
                senderId = recipientId,
                recipientId = senderId
            )
        )

        return chatId
    }
}
