import { ReactKeycloakProvider } from '@react-keycloak/web'
import { useState, useEffect } from 'react'
import keycloak from '.'
import KeycloakLoading from 'src/components/KeycloakLoading'

interface Props {
  children: React.ReactNode
  onEvent: (event: string, error: any) => void
}

const KeycloakProviderWithInit = ({ children, onEvent }: Props) => {
  // This will help us determine if this is first load or a refresh
  const [hasInitializedBefore, setHasInitializedBefore] = useState(() => {
    return sessionStorage.getItem('keycloak_initialized') === 'true'
  })

  // Set a flag in sessionStorage when Keycloak is initialized
  useEffect(() => {
    const handleInitialized = () => {
      sessionStorage.setItem('keycloak_initialized', 'true')
      setHasInitializedBefore(true)
    }

    // Use event to detect when initialization is complete
    window.addEventListener('keycloak-initialized', handleInitialized)

    return () => {
      window.removeEventListener('keycloak-initialized', handleInitialized)
    }
  }, [])

  // Custom event handler that wraps the provided onEvent
  const handleEvent = (event: string, error: any) => {
    // Call the original onEvent handler
    onEvent(event, error)

    // If it's an initialization event, dispatch our custom event
    if (event === 'onReady' || event === 'onInitSuccess') {
      window.dispatchEvent(new Event('keycloak-initialized'))
    }
  }

  // For refreshes, use a minimal loading indicator to avoid disruption
  const LoadingComponent = hasInitializedBefore
    ? () => {
        console.log('Keycloak refreshing...')
        return null // Or a very minimal indicator
      }
    : KeycloakLoading // Show full loading screen only on first visit

  return (
    <ReactKeycloakProvider
      authClient={keycloak}
      initOptions={{
        onLoad: 'check-sso',
        pkceMethod: 'S256',
        silentCheckSsoRedirectUri: window.location.origin + '/silent-check-sso.html',
        checkLoginIframe: false // Reduce unnecessary checks
      }}
      LoadingComponent={<LoadingComponent />}
      onEvent={handleEvent}
    >
      {children}
    </ReactKeycloakProvider>
  )
}

export default KeycloakProviderWithInit
