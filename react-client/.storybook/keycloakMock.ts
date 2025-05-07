import Keycloak from 'keycloak-js'

export const keycloakMock: Keycloak = {
  init: () => Promise.resolve(true),
  login: () => Promise.resolve(),
  logout: () => Promise.resolve(),
  updateToken: () => Promise.resolve(true),
  onAuthSuccess: undefined,
  onAuthError: undefined,
  onAuthRefreshSuccess: undefined,
  onAuthRefreshError: undefined,
  onTokenExpired: undefined,
  onAuthLogout: undefined,
  authenticated: true,
  token: 'fake-token',
  realmAccess: {
    roles: ['user']
  },
  didInitialize: true,
  register: () => Promise.resolve(),
  accountManagement: () => Promise.resolve(),
  createLoginUrl: () => Promise.resolve(''),
  createLogoutUrl: () => '',
  createRegisterUrl: () => Promise.resolve(''),
  createAccountUrl: () => '',
  loadUserProfile: () => Promise.resolve({}),
  loadUserInfo: () => Promise.resolve({}),
  hasRealmRole: () => false,
  hasResourceRole: () => false,
  clearToken: () => {},
  isTokenExpired: function (minValidity?: number): boolean {
    throw new Error('Function not implemented.')
  }
}
