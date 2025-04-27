package com.lcaohoanq.keycloak.userextra

import com.lcaohoanq.keycloak.userextra.model.UserExtra
import org.springframework.data.jpa.repository.JpaRepository

interface UserExtraRepository : JpaRepository<UserExtra, String>
