import { Box, Container, Grid, Typography } from '@mui/material'
import { memo, useMemo } from 'react'
import { CategoryEntity } from 'src/types/category.type'
import 'swiper/css'
import 'swiper/css/navigation'
import 'swiper/css/pagination'
import { Autoplay, Navigation, Pagination } from 'swiper/modules'
import { Swiper, SwiperSlide } from 'swiper/react'
import '../HomeSlider/Swiper.css'
import useCategories from 'src/hooks/useCategories'

// Create a utility function to generate image paths based on category name
const getCategoryImage = (categoryName: string): string => {
  // Convert category name to slug format for image mapping
  const slug = categoryName
    .toLowerCase()
    .normalize('NFD')
    .replace(/[\u0300-\u036f]/g, '')
    .replace(/[đĐ]/g, 'd')
    .replace(/\s+/g, '_')
    .replace(/[&]/g, 'and')

  // Map of common category keywords to image names
  const keywordMap: Record<string, string> = {
    thoi_trang_nam: 'men_fashion',
    thoi_trang_nu: 'women_fashion',
    dien_thoai: 'phone_accessories',
    phu_kien: 'phone_accessories',
    me_be: 'mom_child',
    thiet_bi_dien_tu: 'electronic_devices',
    nha_cua: 'home_lifestyle',
    doi_song: 'home_lifestyle',
    may_tinh: 'laptop',
    laptop: 'laptop',
    sac_dep: 'beauty',
    my_pham: 'beauty',
    may_anh: 'camera',
    may_quay: 'camera',
    suc_khoe: 'health',
    dong_ho: 'watch',
    giay_nu: 'women_shoes',
    giay_nam: 'men_shoes',
    tui_nu: 'women_bag',
    vi_nu: 'women_bag',
    thiet_bi_dien: 'household_electrical_appliances',
    gia_dung: 'household_electrical_appliances',
    phu_kien_nu: 'women_accessories',
    trang_suc: 'women_accessories',
    the_thao: 'sport_travel',
    du_lich: 'sport_travel',
    bach_hoa: 'online_store',
    o_to: 'vehicle',
    xe_may: 'vehicle',
    xe_dap: 'vehicle',
    sach: 'online_bookstore',
    balo: 'men_bag',
    tui_nam: 'men_bag',
    vi_nam: 'men_bag',
    tre_em: 'kid_fashion',
    do_choi: 'toys',
    giat_giu: 'laundry',
    cham_soc_nha: 'laundry',
    thu_cung: 'pet_care',
    voucher: 'voucher',
    dich_vu: 'voucher',
    dung_cu: 'utilities',
    thiet_bi_tien_ich: 'utilities'
  }

  // Try to match category with known keywords
  for (const [keyword, imageName] of Object.entries(keywordMap)) {
    if (slug.includes(keyword)) {
      return `/categories/${imageName}.webp`
    }
  }

  // Default fallback image
  return '/categories/utilities.webp'
}

const CategoryGridSlider = () => {
  const { data: categories, isLoading, error } = useCategories()

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
            <Grid container spacing={0}>
              {chunk.map((category) => (
                <Grid key={category.id}>
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
