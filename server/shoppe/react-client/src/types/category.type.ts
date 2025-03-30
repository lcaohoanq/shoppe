import { ApiResponse } from './api.type'

export type CategoryEntity = {
  id: string
  name: string
}

type SubcategoryResponse = {
  id: number
  name: string
  // category_id: number
  created_at: string
  updated_at: string
}

export type CategoryResponse = {
  id: number
  name: string
  subcategories?: SubcategoryResponse[]
  created_at?: string
  updated_at?: string
}
