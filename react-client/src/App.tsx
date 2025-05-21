import { AuthClientError } from '@react-keycloak/core'
import { ReactKeycloakProvider } from '@react-keycloak/web'
import { ReactQueryDevtools } from '@tanstack/react-query-devtools'
import { useContext, useEffect } from 'react'
import { HelmetProvider } from 'react-helmet-async'
import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'
import ErrorBoundary from './components/ErrorBoundary'
import KeycloakLoading from './components/KeycloakLoading'
import { AppContext } from './contexts/app.context'
import { LanguageProvider } from './contexts/LanguageContext'
import keycloak from './core/keycloak'
import useRouteElements from './useRouteElements'
import { LocalStorageEventTarget } from './utils/auth'
import KeycloakProviderWithInit from './core/keycloak/KeycloakProviderWithInit'

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
  const initOptions = { pkceMethod: 'S256' }

  const handleOnEvent = async (event: string, error: AuthClientError | undefined) => {
    try {
      if (event === 'onAuthSuccess') {
        if (keycloak.authenticated) {
          // let response = await moviesApi.getUserExtrasMe(keycloak.token)
          // if (response.status === 404) {
          //   const username = keycloak.tokenParsed.preferred_username
          //   const userExtra = { avatar: username }
          //   response = await moviesApi.saveUserExtrasMe(keycloak.token, userExtra)
          //   console.log('UserExtra created for ' + username)
          // }
          // keycloak['avatar'] = response.data.avatar

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
