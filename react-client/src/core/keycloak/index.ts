import Keycloak from 'keycloak-js'
import { config } from 'src/env/env.config'

const keycloak = new Keycloak({
  url: `${config.kc.KEYCLOAK_BASE_URL}`,
  realm: `${config.kc.REALM_NAME}`,
  clientId: `${config.kc.CLIENT_ID}`
})

export default keycloak
