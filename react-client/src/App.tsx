import { AuthClientError } from '@react-keycloak/core'
import { ReactQueryDevtools } from '@tanstack/react-query-devtools'
import { useContext, useEffect } from 'react'
import { HelmetProvider } from 'react-helmet-async'
import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'
import ErrorBoundary from './components/ErrorBoundary'
import { AppContext } from './contexts/app.context'
import { LanguageProvider } from './contexts/LanguageContext'
import keycloak from './core/keycloak'
import KeycloakProviderWithInit from './core/keycloak/KeycloakProviderWithInit'
import { KeycloakAuthenticatedData } from './types/keycloak.type'
import useRouteElements from './useRouteElements'
import { LocalStorageEventTarget } from './utils/auth'

/**
 * Khi url thay đổi thì các component nào dùng các hook như
 * useRoutes, useParmas, useSearchParams,...
 * sẽ bị re-render.
 * Ví dụ component `App` dưới đây bị re-render khi mà url thay đổi
 * vì dùng `useRouteElements` (đây là customhook của `useRoutes`)
 */

function App() {
  const routeElements = useRouteElements()
  const { reset } = useContext(AppContext)
  const { setKeycloakAuth, setIsAuthenticated } = useContext(AppContext)

  const handleOnEvent = async (event: string, error: AuthClientError | undefined) => {
    try {
      if (event === 'onAuthSuccess' && keycloak.authenticated && keycloak.tokenParsed) {
        if (keycloak.authenticated) {
          // console.log(`Keycloak authenticated data: ${JSON.stringify(keycloak.tokenParsed, null, 2)}`)
          const tokenParsed = keycloak.tokenParsed

          const authData: KeycloakAuthenticatedData = {
            isAuthenticated: keycloak.authenticated ?? false,
            token: keycloak.token ?? '',
            refreshToken: keycloak.refreshToken ?? '',
            userInfo: {
              id: tokenParsed?.sub ?? '',
              username: tokenParsed?.preferred_username ?? '',
              fullName: tokenParsed?.name ?? '',
              email: tokenParsed?.email ?? '',
              emailVerified: tokenParsed?.email_verified ?? false,
              roles: [
                ...(tokenParsed?.realm_access?.roles || []),
                ...(tokenParsed?.resource_access?.['react-app']?.roles || [])
              ]
            }
          }
          setKeycloakAuth(authData)
          setIsAuthenticated(true)

          console.log('User authenticated successfully')
        }
      }
    } catch {
      console.error('Error during authentication', error)
    }
  }

  useEffect(() => {
    LocalStorageEventTarget.addEventListener('clearLS', reset)
    return () => {
      LocalStorageEventTarget.removeEventListener('clearLS', reset)
    }
  }, [reset])

  return (
    <HelmetProvider>
      <ErrorBoundary>
        <LanguageProvider>
          <KeycloakProviderWithInit onEvent={handleOnEvent}>
            {routeElements}
            <ToastContainer />
          </KeycloakProviderWithInit>
        </LanguageProvider>
      </ErrorBoundary>
      <ReactQueryDevtools initialIsOpen={false} />
    </HelmetProvider>
  )
}

export default App
