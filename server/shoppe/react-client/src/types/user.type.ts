type Role = 'User' | 'Admin'

export interface User {
  _id: string
  roles: Role[]
  email: string
  name?: string
  date_of_birth?: string // ISO 8610
  avatar?: string
  address?: string
  phone?: string
  createdAt: string
  updatedAt: string
}

export type UserResponse = {
  id: number
  email: string
  name: string
  gender: string
  is_active: boolean
  status: string
  date_of_birth: string
  phone_number: string
  address: string
  avatar: string
  role_name: string
  preferred_language: string
  preferred_currency: string
  created_at: string
  updated_at: string
}

export type LoginResponse = {
  access_token: string
  refresh_token: string
  expires_refresh_token: string
  expires: string
  user: UserResponse
}
