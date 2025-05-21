package org.langchain4j.apithrottling.configs

import com.lcaohoanq.common.enums.UserEnum
import java.util.concurrent.TimeUnit

val roleQuotaConfig: Map<UserEnum.Role, Pair<Int, Long>> = mapOf(
    UserEnum.Role.MEMBER to Pair(10, TimeUnit.HOURS.toMillis(1)),  // 10 requests, reset every 1 hour
    UserEnum.Role.STAFF to Pair(20, TimeUnit.HOURS.toMillis(1)),   // 20 requests, reset every 1 hour
    UserEnum.Role.ADMIN to Pair(50, TimeUnit.DAYS.toMillis(1))     // 50 requests, reset every 1 day
)
