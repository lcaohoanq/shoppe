import http from 'src/utils/http'

import { SuccessResponse } from 'src/types/utils.type'
import { CategoryResponse } from 'src/types/category.type'

const URL = 'categories'

const categoryApi = {
  getCategories() {
    return http.get<SuccessResponse<CategoryResponse[]>>(URL)
  },

  getCategoriesV2: async () => {
    const response = await http.get<SuccessResponse<CategoryResponse[]>>(URL)
    return response.data
  }
}

export default categoryApi
