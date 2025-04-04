package com.lcaohoanq.chat.domain.user

import org.springframework.stereotype.Service

@Service
class UserService(
    private val repository: UserRepository
) : IUserService {
    
    override fun saveUser(user: User) {
        with(user){
            status = User.ActivityStatus.ONLINE
        }
        
        repository.save(user)
    }

    override fun disconnect(user: User) {
        val storedUser = repository.findById(user.nickName).orElse(null)
        if (storedUser != null) {
            storedUser.status = User.ActivityStatus.OFFLINE
            repository.save(storedUser)
        }
    }

    override fun findConnectedUsers(): List<User> {
        return repository.findAllByActivityStatus(User.ActivityStatus.ONLINE)
    }
}
