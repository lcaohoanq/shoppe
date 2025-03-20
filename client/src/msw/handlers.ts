import { HttpHandler } from 'msw'
import { getAllCategoryRequest } from './category.msw'
import { getAllHeadquarter } from './headquarter.msw'
import { getAllProductRequest, getProductDetailRequest } from './product.msw'

export const handlers: HttpHandler[] = [
  getAllCategoryRequest(),
  getAllProductRequest(),
  getAllHeadquarter(),
  getProductDetailRequest(1)
]
