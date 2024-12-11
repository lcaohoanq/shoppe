import { Divider, Typography } from '@mui/material'
import axios from 'axios'
import React, { useEffect, useState } from 'react'

type SubcategoryResponse = {
  id: number
  name: string
  // category_id: number
  created_at: string
  updated_at: string
}

type CategoryResponse = {
  id: number
  name: string
  subcategories: SubcategoryResponse[]
  created_at: string
  updated_at: string
}

type ApiResponse<T> = {
  message: string
  data: T
  status_code: number
  is_success: boolean
}

function CategoryList() {
  const [categories, setCategories] = useState<CategoryResponse[]>([])

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await axios.get<ApiResponse<CategoryResponse[]>>('http://localhost:8080/api/v1/categories')
        setCategories(response.data.data)
      } catch (error) {
        console.error('Failed to fetch categories: ', error)
      }
    }

    fetchCategories()
  }, [])

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
        {categories.map((category) => (
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
            <ul className='list-disc list-inside text-gray-600'>
              {category.subcategories.map((subcategory) => (
                <li key={subcategory.id} className='mb-1'>
                  {subcategory.name}
                </li>
              ))}
            </ul>
          </div>
        ))}
      </div>
    </div>
  )
}

export default CategoryList
