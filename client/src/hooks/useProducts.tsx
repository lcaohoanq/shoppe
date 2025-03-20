import { useQuery } from '@tanstack/react-query'
import axios from 'axios'
import { API_URL, GRAPHQL_URL } from 'src/env/env.config'
import { ApiResponse } from 'src/types/api.type'
import { ProductResponse, ProductResponseWithPagination } from 'src/types/product.type'

const mockUrl = '/api/products'
const url = `${API_URL}/products`

const fetchProducts_GRAPHQL = async () => {
  const response = await axios.post<{ data: { products: ProductResponse[] } }>(`${GRAPHQL_URL}`, {
    query: ` 
        query {
          products {
            id
            name
            description
            price
            sold
            rating
          }
        }
      `
  })
  return response.data.data.products
}

const fetchProducts_REST = async () => {
  const response = await axios.get<ProductResponseWithPagination>(mockUrl)
  return response.data
}

const useProducts = () => {
  return useQuery({
    queryKey: ['productsHome'],
    queryFn: fetchProducts_REST,
    keepPreviousData: true
  })
}

export const useProductDetail = (id: number) => {
  return useQuery({
    queryKey: ['productDetail', id],
    queryFn: () => {
      if (isNaN(id)) throw new Error('Invalid product id')
      return axios.get<ProductResponse>(`${mockUrl}/${id}`)
    },
    enabled: !isNaN(id)
  })
}

export default useProducts
