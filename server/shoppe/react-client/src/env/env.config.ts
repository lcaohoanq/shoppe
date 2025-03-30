const ENV = {
  API_URL: import.meta.env.VITE_API_URL || 'http://localhost:8080/api/v1',
  GRAPHQL_URL: import.meta.env.VITE_GRAPHQL_URL || 'http://localhost:8080/graphql'
}

export const API_URL: string = ENV.API_URL
export const GRAPHQL_URL: string = ENV.GRAPHQL_URL
