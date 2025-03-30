import { Divider, Typography } from '@mui/material'
import useCategories from 'src/hooks/useCategories'
import Loading from '../Loading'

function CategoryList() {
  const { data, isLoading, error } = useCategories()

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
                {category.subcategories?.map((subcategory) => subcategory.name).join(' | ')}
              </p>
            </div>
          ))}
      </div>
    </div>
  )
}

export default CategoryList
