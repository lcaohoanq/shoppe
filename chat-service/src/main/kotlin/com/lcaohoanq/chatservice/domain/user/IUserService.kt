package com.lcaohoanq.chat.domain.user

interface IUserService {
    fun saveUser(user: User)
    fun disconnect(user: User)
    fun findConnectedUsers(): List<User>
}
