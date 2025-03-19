import { Divider, Typography } from '@mui/material'
import { useQuery } from '@tanstack/react-query'
import axios from 'axios'
import API_URL from 'src/env/env.config'
import Loading from '../Loading'

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
  subcategories: SubcategoryResponse[]
  created_at: string
  updated_at: string
}

export type ApiResponse<T> = {
  message: string
  data: T
  status_code: number
  is_success: boolean
}

function CategoryList() {
  const { data, isLoading, error } = useQuery<CategoryResponse[]>(['categories'], async () => {
    const response = await axios.get<ApiResponse<CategoryResponse[]>>(`${API_URL}/categories`)
    return response.data.data
  })

  if (isLoading) return <Loading />
  if (error) return <div>Something went wrong</div>

  return (
    <div>
      <Divider
        sx={{
          margin: '5rem 0'
        }}
      />
      <Typography
        className='text-gray-500'
        variant='body2'
        sx={{
          fontWeight: 'bold'
        }}
      >
        Danh Mục
      </Typography>
      <div className='grid grid-cols-5 gap-6'>
        {data &&
          data.map((category) => (
            <div key={category.id} className='p-4'>
              <Typography
                variant='body2'
                className=' text-gray-500'
                sx={{
                  fontWeight: 'bold',
                  margin: '1rem 0 0.5rem 0'
                }}
              >
                {category.name}
              </Typography>

              <p className='text-gray-700'>
                {category.subcategories.map((subcategory) => subcategory.name).join(' | ')}
              </p>
            </div>
          ))}
      </div>
    </div>
  )
}

export default CategoryList
