import axios from 'axios'
import { AuthResponse } from 'src/types/auth.type'
import http from 'src/utils/http'

export const URL_LOGIN = 'auth/login'
export const URL_REGISTER = 'auth/register'
export const URL_LOGOUT = 'auth/logout'
export const URL_REFRESH_TOKEN = 'refresh-access-token'

const authApi = {
  registerAccount(body: { email: string; password: string }) {
    return http.post<AuthResponse>(URL_REGISTER, body)
  },
  login(body: { email: string; password: string }) {
    return http.post<AuthResponse>(URL_LOGIN, body)
  },
  loginMock(body: { email: string; password: string }) {
    return axios.post<AuthResponse>('/api/auth/login', body)
  },
  logout() {
    return http.post(URL_LOGOUT)
  }
}

export default authApi
