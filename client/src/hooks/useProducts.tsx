import { useQuery } from '@tanstack/react-query'
import axios from 'axios'
import { API_URL, GRAPHQL_URL } from 'src/env/env.config'
import { ApiResponse } from 'src/types/api.type'
import { ProductResponse } from 'src/types/product.type'

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

const fetchProducts_REST = async (): Promise<ProductResponse[]> => {
  const response = await axios.get<ApiResponse<ProductResponse[]>>(`/products?page=1&limit=60`)
  return response.data.data
}

const useProducts = () => {
  return useQuery({ queryKey: ['productsHome'], queryFn: fetchProducts_REST })
}

export default useProducts
