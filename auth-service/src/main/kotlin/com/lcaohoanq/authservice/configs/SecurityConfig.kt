package com.lcaohoanq.authservice.configs

import com.lcaohoanq.authservice.repositories.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class SecurityConfig(
    private val userRepository: UserRepository
) {

    @Bean
    fun userDetailsService(): UserDetailsService =
        UserDetailsService { email ->
            userRepository.findByEmail(email)
                ?: throw UsernameNotFoundException("Cannot find user with email $email")
        }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()


    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService())
        authProvider.setUserDetailsService(UserDetailsService { username ->
            if ("swagger" == username) {
                return@UserDetailsService User.withUsername("swagger")
                    .password(passwordEncoder().encode("swagger123"))
                    .roles("SWAGGER_USER")
                    .build()
            }
            userDetailsService().loadUserByUsername(username)
        })
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(
        config: AuthenticationConfiguration
    ): AuthenticationManager = config.authenticationManager


}