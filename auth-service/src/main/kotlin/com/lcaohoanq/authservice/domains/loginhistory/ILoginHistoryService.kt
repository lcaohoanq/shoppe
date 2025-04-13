package com.lcaohoanq.authservice.domains.loginhistory

import com.lcaohoanq.authservice.domains.user.User

interface ILoginHistoryService {

    fun recordLogin(user: User, ipAddress: String, userAgent: String)

}