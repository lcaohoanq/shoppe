import { ProductImageReponse } from './productimage.type'

export type Product = {
  id: string
  images: ProductImageReponse[]
  price: number
  rating: number
  price_before_discount: number
  quantity: number
  sold: number
  view: number
  name: string
  description: string
  shop_owner_id: number
  category: {
    _id: string
    name: string
  }
  image: string
  createdAt: string
  updatedAt: string
}

export type ProductList = {
  products: Product[]
  pagination: {
    page: number
    limit: number
    page_size: number
  }
}

export type ProductListConfig = {
  page?: number | string
  limit?: number | string
  sort_by?: 'createdAt' | 'view' | 'sold' | 'price'
  order?: 'asc' | 'desc'
  exclude?: string
  rating_filter?: number | string
  price_max?: number | string
  price_min?: number | string
  name?: string
  category?: string
}

export type ProductResponse = {
  id: string
  name: string
  description: string
  price_before_discount: number
  sold: boolean
  rating: number
}
