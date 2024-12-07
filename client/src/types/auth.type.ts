import { LoginResponse, User } from './user.type'
import { SuccessResponse } from './utils.type'

export type AuthResponse = SuccessResponse<LoginResponse>

export type RefreshTokenReponse = SuccessResponse<{ access_token: string }>
