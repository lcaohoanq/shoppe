package com.lcaohoanq.gateway.security

import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtClaimNames
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import reactor.core.publisher.Mono

@Component
class JwtAuthConverter(
    private val properties: JwtAuthConverterProperties
) : Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    override fun convert(jwt: Jwt): Mono<AbstractAuthenticationToken> {
        val authorities = mutableSetOf<GrantedAuthority>()
        authorities += jwtGrantedAuthoritiesConverter.convert(jwt).orEmpty()
        authorities += extractResourceRoles(jwt)

        val principalClaim = properties.principalAttribute ?: JwtClaimNames.SUB
        val principal = jwt.getClaim<String>(principalClaim)

        val authToken = JwtAuthenticationToken(jwt, authorities, principal)

        return Mono.just(authToken)
    }

    private fun extractResourceRoles(jwt: Jwt): Collection<GrantedAuthority> {
        val resourceAccess = jwt.getClaim<Map<String, Any>?>("resource_access") ?: return emptySet()

        val resource = resourceAccess[properties.resourceId] as? Map<*, *> ?: return emptySet()
        val roles = resource["roles"] as? Collection<*> ?: return emptySet()

        return roles
            .filterIsInstance<String>()
            .map { SimpleGrantedAuthority("ROLE_$it") }
            .toSet()
    }

    private fun extractUserId(jwt: Jwt): String {
        val userIdClaim = properties.principalAttribute ?: JwtClaimNames.SUB
        return jwt.getClaim(userIdClaim)
    }

    companion object {
        private val jwtGrantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()
    }
}

@Validated
@Configuration
@ConfigurationProperties(prefix = "jwt.auth.converter")
class JwtAuthConverterProperties {
    @NotBlank
    lateinit var resourceId: String
    var principalAttribute: String? = null
}
