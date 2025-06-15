package com.lcaohoanq.chatservice.domain.chat

import com.lcaohoanq.chatservice.domain.chatroom.IChatRoomService
import org.springframework.stereotype.Service

@Service
class ChatMessageService(
    private val repository: ChatMessageRepository,
    private val chatRoomService: IChatRoomService
) : IChatMessageService {

    override fun save(chatMessage: ChatMessage): ChatMessage {
        val chatId = chatRoomService
            .getChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true) ?: ""
        chatMessage.setChatId(chatId)
        repository.save(chatMessage)
        return chatMessage
    }

    override fun findChatMessages(senderId: String, recipientId: String): List<ChatMessage> {
        // Get the chatRoomId (returns null if not found and createNewRoomIfNotExists is false)
        val chatId = chatRoomService.getChatRoomId(senderId, recipientId, false) ?: return emptyList()

        // Use chatId to find messages
        return repository.findByChatId(chatId)
    }

}
