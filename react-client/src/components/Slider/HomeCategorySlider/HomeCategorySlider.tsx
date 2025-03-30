import { Box, Grid, Typography } from '@mui/material'
import { useQuery } from '@tanstack/react-query'
import axios from 'axios'
import { memo } from 'react'
import { API_URL } from 'src/env/env.config'
import { CategoryEntity, CategoryResponse } from 'src/types/category.type'
import 'swiper/css'
import 'swiper/css/navigation'
import 'swiper/css/pagination'
import { Autoplay, Navigation, Pagination } from 'swiper/modules'
import { Swiper, SwiperSlide } from 'swiper/react'
import '../HomeSlider/Swiper.css'

const CategoryGridSlider = () => {
  const { data: categories } = useQuery<CategoryResponse[]>(['categories'], async () => {
    const response = await axios.get(`${API_URL}/categories`)
    return response.data.data
  })

  // Split categories into chunks of 20 for each SwiperSlide
  const chunkSize = 20
  const categoryChunks = categories
    ? categories.reduce((resultArray, item, index) => {
        const chunkIndex = Math.floor(index / chunkSize)

        if (!resultArray[chunkIndex]) {
          resultArray[chunkIndex] = [] // Start a new chunk
        }

        resultArray[chunkIndex].push({
          ...item,
          id: item.id.toString()
        })

        return resultArray
      }, [] as CategoryEntity[][])
    : []

  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', gap: 3, marginBottom: 3 }}>
      <Typography variant='h5' sx={{ mb: 1 }}>
        Danh Mục
      </Typography>
      <Swiper
        modules={[Navigation, Pagination, Autoplay]}
        spaceBetween={30}
        slidesPerView={1}
        navigation
        pagination={{ clickable: true }}
        loop={false}
        style={{ width: '100%' }} // Add padding for pagination bullets
        className='custom-swiper'
      >
        {categoryChunks.map((chunk, index) => (
          <SwiperSlide key={index}>
            <Grid container spacing={0} sx={{ mx: 0 }}>
              {chunk.map((category) => (
                <Grid
                  item
                  xs={2.4} // 5 items per row on mobile
                  sm={2} // 6 items per row on small screens
                  md={1.5} // 8 items per row on medium screens
                  lg={1.2} // 10 items per row on large screens
                  key={category.id}
                >
                  <Box
                    sx={{
                      border: '1px solid #f1f1f1',
                      overflow: 'hidden',
                      height: '100%',
                      display: 'flex',
                      flexDirection: 'column',
                      alignItems: 'center',
                      p: 1,
                      transition: 'transform 0.3s',
                      '&:hover': {
                        boxShadow: '0 4px 8px rgba(0,0,0,0.1)'
                      }
                    }}
                  >
                    <Box
                      component='img'
                      sx={{
                        height: 60,
                        width: 60,
                        objectFit: 'contain',
                        mb: 1
                      }}
                      src='/img/vn-11134258-7ra0g-m7hndl5ii6pi79_xhdpi.jpg'
                      alt={category.name}
                    />
                    <Typography
                      variant='body2'
                      sx={{
                        textAlign: 'center',
                        overflow: 'hidden',
                        textOverflow: 'ellipsis',
                        display: '-webkit-box',
                        WebkitLineClamp: 2,
                        WebkitBoxOrient: 'vertical',
                        lineHeight: '1.2'
                      }}
                    >
                      {category.name}
                    </Typography>
                  </Box>
                </Grid>
              ))}
            </Grid>
          </SwiperSlide>
        ))}
      </Swiper>
    </Box>
  )
}

const HomeCategorySlider = memo(CategoryGridSlider)

export default HomeCategorySlider
