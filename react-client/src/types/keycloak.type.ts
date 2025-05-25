import { KeycloakTokenParsed } from 'keycloak-js'

type EssentialKeycloakDataParsed = Pick<
  KeycloakTokenParsed,
  'preferred_username' | 'email' | 'name' | 'given_name' | 'family_name' | 'sub' | 'avatar'
>

export type KeycloakAuthenticatedData = {
  isAuthenticated: boolean
  token: string
  refreshToken: string
  userInfo: {
    id: string
    username: string
    fullName: string
    email: string
    emailVerified: boolean
    roles: string[]
  }
}
