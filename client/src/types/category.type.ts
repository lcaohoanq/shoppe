import { ApiResponse } from './api.type'

export interface Category {
  id: string
  name: string
}

export type CategoryResponse = ApiResponse<Category[]>
