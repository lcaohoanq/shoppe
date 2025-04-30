import {ReactKeycloakProvider} from '@react-keycloak/web'
import Keycloak from 'keycloak-js'
import {BrowserRouter as Router, Navigate, Route, Routes} from 'react-router-dom'
import {config} from "./env/Constants.ts";
import Loader from "./components/loading/LoadingScreen.tsx";
import Navbar from "./components/NavBar/Navbar.tsx";
import Home from "./components/Home/Home.tsx";
import UserDetail from "./components/UserDetail/UserDetail.tsx";
import {AuthClientError} from '@react-keycloak/core';


function App() {

  const keycloak = new Keycloak({
    url: `${config.url.KEYCLOAK_BASE_URL}`,
    realm: "company-services",
    clientId: "movies-app"
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

  return (
    <ReactKeycloakProvider
      authClient={keycloak}
      initOptions={initOptions}
      LoadingComponent={<Loader/>}
      onEvent={(event, error) => handleOnEvent(event, error)}
    >
      <Router>
        <Navbar/>
        <Routes>
          <Route path='/' element={<Home/>}/>
          <Route path='/user/detail' element={<UserDetail/>}/>
          <Route path="*" element={<Navigate to="/"/>}/>
        </Routes>
      </Router>
    </ReactKeycloakProvider>
  )
}

export default App
