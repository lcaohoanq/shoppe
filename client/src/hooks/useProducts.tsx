import { useQuery } from '@tanstack/react-query'
import axios from 'axios'
import { GRAPHQL_URL } from 'src/env/env.config'
import { ProductResponse } from 'src/types/product.type'

const fetchProducts = async () => {
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

const useProducts = () => {
  return useQuery<ProductResponse[]>({ queryKey: ['productsHome'], queryFn: fetchProducts })
}

export default useProducts
