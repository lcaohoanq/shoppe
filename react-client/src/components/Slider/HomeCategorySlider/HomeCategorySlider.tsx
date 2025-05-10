import { Box, Container, Grid, Typography } from '@mui/material'
import { useQuery } from '@tanstack/react-query'
import axios from 'axios'
import { memo, useMemo } from 'react'
import { API_URL } from 'src/env/env.config'
import { CategoryEntity, CategoryResponse } from 'src/types/category.type'
import 'swiper/css'
import 'swiper/css/navigation'
import 'swiper/css/pagination'
import { Autoplay, Navigation, Pagination } from 'swiper/modules'
import { Swiper, SwiperSlide } from 'swiper/react'
import '../HomeSlider/Swiper.css'

const categoryImageData = [
  {
    id: 1,
    name: 'Thời Trang Nam',
    img: '/categories/men_fashion.webp'
  },
  {
    id: 2,
    name: 'Thời Trang Nữ',
    img: '/categories/women_fashion.webp'
  },
  {
    id: 3,
    name: 'Điện thoại & Phụ kiện',
    img: '/categories/phone_accessories.webp'
  },
  {
    id: 4,
    name: 'Mẹ & Bé',
    img: '/categories/mom_child.webp'
  },
  {
    id: 5,
    name: 'Thiết Bị Điện Tử',
    img: '/categories/electronic_devices.webp'
  },
  {
    id: 6,
    name: 'Nhà Cửa & Đời Sống',
    img: '/categories/home_lifestyle.webp'
  },
  {
    id: 7,
    name: 'Máy Tính & Laptop',
    img: '/categories/laptop.webp'
  },
  {
    id: 8,
    name: 'Sắc Đẹp',
    img: '/categories/beauty.webp'
  },
  {
    id: 9,
    name: 'Máy Ảnh & Máy Quay Phim',
    img: '/categories/camera.webp'
  },
  {
    id: 10,
    name: 'Sức Khỏe',
    img: '/categories/health.webp'
  },
  {
    id: 11,
    name: 'Đồng Hồ',
    img: '/categories/watch.webp'
  },
  {
    id: 12,
    name: 'Giày Dép Nữ',
    img: '/categories/women_shoes.webp'
  },
  {
    id: 13,
    name: 'Giày Dép Nam',
    img: '/categories/men_shoes.webp'
  },
  {
    id: 14,
    name: 'Túi ví Nữ',
    img: '/categories/women_bag.webp'
  },
  {
    id: 15,
    name: 'Thiết Bị Điện Gia Dụng',
    img: '/categories/household_electrical_appliances.webp'
  },
  {
    id: 16,
    name: 'Phụ Kiện & Trang Sức Nữ',
    img: '/categories/women_accessories.webp'
  },
  {
    id: 17,
    name: 'Thể Thao & Du Lịch',
    img: '/categories/sport_travel.webp'
  },
  {
    id: 18,
    name: 'Bách Hóa Online',
    img: '/categories/online_store.webp'
  },
  {
    id: 19,
    name: 'Ô Tô & Xe Máy & Xe Đạp',
    img: '/categories/vehicle.webp'
  },
  {
    id: 20,
    name: 'Nhà Sách Online',
    img: '/categories/online_bookstore.webp'
  },
  {
    id: 21,
    name: 'Balo & Túi Ví Nam',
    img: '/categories/men_bag.webp'
  },
  {
    id: 22,
    name: 'Thời Trang Trẻ Em',
    img: '/categories/kid_fashion.webp'
  },
  {
    id: 23,
    name: 'Đồ Chơi',
    img: '/categories/toys.webp'
  },
  {
    id: 24,
    name: 'Giặt Giũ & Chăm Sóc Nhà Cửa',
    img: '/categories/laundry.webp'
  },
  {
    id: 25,
    name: 'Chăm Sóc Thú Cưng',
    img: '/categories/pet_care.webp'
  },
  {
    id: 26,
    name: 'Voucher & Dịch Vụ',
    img: '/categories/voucher.webp'
  },
  {
    id: 27,
    name: 'Dụng cụ và thiết bị tiện ích',
    img: '/categories/utilities.webp'
  }
]

const CategoryGridSlider = () => {
  const { data: categories } = useQuery<CategoryResponse[]>(['categories'], async () => {
    const response = await axios.get(`${API_URL}/categories`)
    return response.data.data
  })

  // Create a normalized map of category names to image paths for case-insensitive lookup
  const categoryImageMap = useMemo(() => {
    const map = new Map()
    categoryImageData.forEach((item) => {
      // Store both original name and lowercase version
      map.set(item.name.toLowerCase(), item.img)
    })
    return map
  }, [])

  // Function to find the image path for a category
  const getCategoryImage = (categoryName: string) => {
    const normalizedName = categoryName.toLowerCase()

    // Try to find an exact match first (case-insensitive)
    if (categoryImageMap.has(normalizedName)) {
      return categoryImageMap.get(normalizedName)
    }

    // Check for case-insensitive partial matches by comparing normalized strings
    // First, check if any map key contains the category name
    for (const [name, img] of categoryImageMap.entries()) {
      // For example, "Thời Trang Nam" from API should match "thời trang nam" in our data
      if (name.includes(normalizedName) || normalizedName.includes(name)) {
        return img
      }
    }

    // Try word-by-word matching for multi-word categories
    const categoryWords = normalizedName.split(' ').filter((word) => word.length > 2)
    if (categoryWords.length > 0) {
      for (const [name, img] of categoryImageMap.entries()) {
        for (const word of categoryWords) {
          if (name.includes(word)) {
            return img
          }
        }
      }
    }

    // Default fallback image if no match found
    return '/categories/utilities.webp'
  }

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
    <Container
      sx={{ backgroundColor: '#F5F5F5', display: 'flex', flexDirection: 'column', gap: 3, marginBottom: 3, p: 3 }}
    >
      <Box
        sx={{
          backgroundColor: 'white',
          padding: '1rem'
        }}
      >
        <Typography variant='h5' sx={{ color: 'grey' }}>
          Danh Mục
        </Typography>
      </Box>
      <Swiper
        modules={[Navigation, Pagination, Autoplay]}
        spaceBetween={30}
        slidesPerView={1}
        navigation
        pagination={{ clickable: true }}
        loop={false}
        style={{ width: '100%' }}
      >
        {categoryChunks.map((chunk, index) => (
          <SwiperSlide key={index}>
            <Grid container spacing={0} sx={{ mx: 0 }}>
              {chunk.map((category) => (
                <Grid item xs={2.4} sm={2} md={1.5} lg={1.2} key={category.id}>
                  <Box
                    sx={{
                      border: '1px solid #f1f1f1',
                      overflow: 'hidden',
                      height: '100%',
                      display: 'flex',
                      flexDirection: 'column',
                      alignItems: 'center',
                      backgroundColor: 'white',
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
                        mb: 1,
                        backgroundColor: 'white'
                      }}
                      src={getCategoryImage(category.name)}
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
    </Container>
  )
}

const HomeCategorySlider = memo(CategoryGridSlider)

export default HomeCategorySlider
