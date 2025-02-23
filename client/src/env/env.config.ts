const ENV = {
  API_URL: import.meta.env.VITE_API_URL || 'http://localhost:8080/api/v1'
}

const API_URL: string = ENV.API_URL

export default API_URL
