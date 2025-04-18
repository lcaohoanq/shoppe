import { User } from 'src/types/user.type'
import { SuccessResponse } from 'src/types/utils.type'
import http, { createHttpClient } from 'src/utils/http'
import axios from 'axios'

interface BodyUpdateProfile extends Omit<User, '_id' | 'roles' | 'createdAt' | 'updatedAt' | 'email' | 'username'> {
  password?: string
  newPassword?: string
}

const userApi = {
  getProfile() {
    return http.get<SuccessResponse<User>>('me')
  },
  getProfileV2() {
    return axios.get<SuccessResponse<User>>('/api/me')
  },
  updateProfile(body: BodyUpdateProfile) {
    return http.patch<SuccessResponse<User>>('user', body)
  },
  uploadAvatar(body: FormData) {
    return http.post<SuccessResponse<string>>('user/upload-avatar', body, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
  }
}

export const userServiceClient = createHttpClient('http://localhost:4006/api/v1')

export default userApi
