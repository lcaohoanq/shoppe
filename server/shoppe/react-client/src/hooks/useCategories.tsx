import { useQuery } from '@tanstack/react-query'
import axios from 'axios'
import { API_URL } from 'src/env/env.config'
import { ApiResponse } from 'src/types/api.type'
import { CategoryResponse } from 'src/types/category.type'

const mockUrl = '/api/categories'
const url = `${API_URL}/categories`

const fetchCategoriesList = async () => {
  const response = await axios.get<ApiResponse<CategoryResponse[]>>(mockUrl)
  return response.data.data
}

const useCategories = () => {
  return useQuery<CategoryResponse[]>({ queryKey: ['categories'], queryFn: fetchCategoriesList })
}

export default useCategories
