import useRouteElements from './useRouteElements'
import {ToastContainer} from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'
import {useContext, useEffect} from 'react'
import {LocalStorageEventTarget} from './utils/auth'
import {AppContext} from './contexts/app.context'
import {ReactQueryDevtools} from '@tanstack/react-query-devtools'
import ErrorBoundary from './components/ErrorBoundary'
import {HelmetProvider} from 'react-helmet-async'
import {LanguageProvider} from "./contexts/LanguageContext";
import {config} from "./env/env.config";
import {ReactKeycloakProvider} from "@react-keycloak/web";
import Loader from "./components/LoadingV2/LoadingScreen";
import {AuthClientError} from '@react-keycloak/core';
import Keycloak from 'keycloak-js'

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

  const keycloak = new Keycloak({
    url: `${config.kc.KEYCLOAK_BASE_URL}`,
    realm: `${config.kc.REALM_NAME}`,
    clientId: `${config.kc.CLIENT_ID}`,
  })
  const initOptions = {pkceMethod: 'S256'}

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

          console.log("User authenticated successfully")
        }
      }
    } catch {
      console.error("Error during authentication", error)
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
          <ReactKeycloakProvider
            authClient={keycloak}
            initOptions={initOptions}
            LoadingComponent={<Loader/>}
            onEvent={(event, error) => handleOnEvent(event, error)}
          >
          {routeElements}
          <ToastContainer />
          </ReactKeycloakProvider>
        </LanguageProvider>
      </ErrorBoundary>
      <ReactQueryDevtools initialIsOpen={false} />
    </HelmetProvider>
  )
}

export default App
