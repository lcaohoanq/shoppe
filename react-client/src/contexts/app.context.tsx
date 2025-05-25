import React, { createContext, useState } from 'react'
import { KeycloakAuthenticatedData } from 'src/types/keycloak.type'
import { ExtendedPurchase } from 'src/types/purchase.type'
import { User, UserResponse } from 'src/types/user.type'
import { getAccessTokenFromLS, getProfileFromLS } from 'src/utils/auth'

interface AppContextInterface {
  isAuthenticated: boolean
  setIsAuthenticated: React.Dispatch<React.SetStateAction<boolean>>
  profile: UserResponse | null
  setProfile: React.Dispatch<React.SetStateAction<UserResponse | null>>
  extendedPurchases: ExtendedPurchase[]
  setExtendedPurchases: React.Dispatch<React.SetStateAction<ExtendedPurchase[]>>

  //Keycloak Auth
  keycloakAuth: KeycloakAuthenticatedData | null
  setKeycloakAuth: React.Dispatch<React.SetStateAction<KeycloakAuthenticatedData | null>>

  reset: () => void
}

export const getInitialAppContext: () => AppContextInterface = () => ({
  isAuthenticated: Boolean(getAccessTokenFromLS()),
  setIsAuthenticated: () => null,
  profile: getProfileFromLS(),
  setProfile: () => null,
  extendedPurchases: [],
  setExtendedPurchases: () => null,
  keycloakAuth: null,
  setKeycloakAuth: () => null,
  reset: () => null
})

const initialAppContext = getInitialAppContext()

export const AppContext = createContext<AppContextInterface>(initialAppContext)

export const AppProvider = ({
  children,
  defaultValue = initialAppContext
}: {
  children: React.ReactNode
  defaultValue?: AppContextInterface
}) => {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(defaultValue.isAuthenticated)
  const [extendedPurchases, setExtendedPurchases] = useState<ExtendedPurchase[]>(defaultValue.extendedPurchases)
  const [profile, setProfile] = useState<UserResponse | null>(defaultValue.profile)
  const [keycloakAuth, setKeycloakAuth] = useState<KeycloakAuthenticatedData | null>(defaultValue.keycloakAuth)

  const reset = () => {
    setIsAuthenticated(false)
    setExtendedPurchases([])
    setProfile(null)
  }

  return (
    <AppContext.Provider
      value={{
        isAuthenticated,
        setIsAuthenticated,
        profile,
        setProfile,
        extendedPurchases,
        setExtendedPurchases,
        keycloakAuth,
        setKeycloakAuth,
        reset
      }}
    >
      {children}
    </AppContext.Provider>
  )
}
