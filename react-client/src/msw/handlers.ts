import { HttpHandler } from 'msw'
import { getAllCategoryRequest } from './category.msw'
import { getAllHeadquarter } from './headquarter.msw'
import { getAllProductRequest, getProductDetailRequest } from './product.msw'
import {handleLogin} from "./auth.msw";
import {getMeRequest_Mock} from "./user.msw";

export const handlers: HttpHandler[] = [
  getAllCategoryRequest(),
  getAllProductRequest(),
  getAllHeadquarter(),
  getProductDetailRequest(1),
  handleLogin(),
  getMeRequest_Mock()
]
