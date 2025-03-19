import { HttpHandler } from 'msw'
import { getAllCategoryRequest } from './category.msw'
import { getAllProductRequest } from './product.msw'

export const handlers: HttpHandler[] = [getAllCategoryRequest, getAllProductRequest]
