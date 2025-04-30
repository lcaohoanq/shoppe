const ENV = {
  API_URL: import.meta.env.VITE_API_URL || 'http://localhost:8080/api/v1',
  GRAPHQL_URL: import.meta.env.VITE_GRAPHQL_URL || 'http://localhost:8080/graphql'
}

export const API_URL: string = ENV.API_URL
export const GRAPHQL_URL: string = ENV.GRAPHQL_URL

const isDev = process.env.NODE_ENV === 'development';

export const config = {
  kc: {
    KEYCLOAK_BASE_URL: "http://localhost:8080",
    REALM_NAME: "shoppe",
    CLIENT_ID: "react-app",
  },
  url: {
    API_BASE_URL: isDev ? "http://localhost:9080/api" : "/api",
    OMDB_BASE_URL: "https://www.omdbapi.com",
    AVATARS_DICEBEAR_URL: "https://api.dicebear.com/6.x"
  }
};
