import { Box, Card, CardActions, CardContent, Container, Divider, IconButton, Modal, Typography } from '@mui/material'
import HomeShoppeMall from 'src/components/Grid/HomeShoppeMall/HomeShoppeMall'
import HomeSlider from 'src/components/Slider/HomeSlider/HomeSlider'
import Loading from 'src/components/Loading'
import HomeCategorySlider from 'src/components/Slider/HomeCategorySlider/HomeCategorySlider'
import useProducts from 'src/hooks/useProducts'
import { useEffect, useState } from 'react'
import CloseIcon from '@mui/icons-material/Close'
import { styles } from './style'

const images = [
  {
    title: 'Hàng Chọn Giá Hời',
    icon: 'https://down-vn.img.susercontent.com/file/vn-11134258-7ras8-m20rc1wk8926cf'
  },
  {
    title: 'Mã Giảm Giá',
    icon: 'https://down-vn.img.susercontent.com/file/vn-11134258-7ras8-m20rc1wk8926cf'
  },
  {
    title: 'Miễn Hết Phí Ship Cho Mọi Đơn',
    icon: 'https://down-vn.img.susercontent.com/file/vn-11134258-7ras8-m20rc1wk8926cf'
  },
  {
    title: 'Shoppe Style Voucher 30%',
    icon: 'https://down-vn.img.susercontent.com/file/vn-11134258-7ras8-m20rc1wk8926cf'
  },
  {
    title: 'Voucher Giảm đến 1 Triệu',
    icon: 'https://down-vn.img.susercontent.com/file/vn-11134258-7ras8-m20rc1wk8926cf'
  },
  {
    title: 'Hàng Quốc Tế',
    icon: 'https://down-vn.img.susercontent.com/file/vn-11134258-7ras8-m20rc1wk8926cf'
  },
  {
    title: 'Nạp Thẻ, Dịch Vụ & Hóa Đơn',
    icon: 'https://down-vn.img.susercontent.com/file/vn-11134258-7ras8-m20rc1wk8926cf'
  }
]

export const bannerslider = [
  'https://api-website.cinestar.com.vn/media/MageINIC/bannerslider/1215x365.png',
  'https://api-website.cinestar.com.vn/media/MageINIC/bannerslider/1215wx365h_4_.jpg',
  'https://api-website.cinestar.com.vn/media/MageINIC/bannerslider/2400wx720h.jpg',
  'https://api-website.cinestar.com.vn/media/MageINIC/bannerslider/anh-khong-dau-banner.jpg',
  'https://api-website.cinestar.com.vn/media/MageINIC/bannerslider/MAIN_2_ADL_1215x365.png',
  'https://api-website.cinestar.com.vn/media/MageINIC/bannerslider/love-lies.jpg',
  'https://api-website.cinestar.com.vn/media/MageINIC/bannerslider/1215wx365h_1_.jpg'
]

export default function Home() {
  const [openModal, setOpenModal] = useState(true)
  const [randomBanner, setRandomBanner] = useState('')
  const { data: products, error, isLoading } = useProducts()

  useEffect(() => {
    setRandomBanner(bannerslider[Math.floor(Math.random() * bannerslider.length)])
  }, [])

  const handleCloseModal = () => {
    setOpenModal(false)
  }

  if (isLoading) return <Loading />
  if (error) return <div>Error loading products...</div>

  return (
    <Container>
      <Modal
        open={openModal}
        onClose={handleCloseModal}
        aria-labelledby='movie-preview-modal'
        aria-describedby='movie-banner-preview'
        sx={styles.modal}
      >
        <Box sx={styles.modalBox}>
          <IconButton onClick={handleCloseModal} sx={styles.closeButton}>
            <CloseIcon />
          </IconButton>
          {randomBanner && (
            <img
              src={randomBanner}
              alt='Movie Banner'
              loading='lazy'
              style={styles.bannerImage as React.CSSProperties}
            />
          )}
        </Box>
      </Modal>

      <div>
        <div className='flex justify-between gap-2'>
          <HomeSlider />
          <div className='mt-[2rem]'>
            <Box
              component='img'
              sx={{
                height: 233,
                width: 350,
                maxHeight: { xs: 233, md: 167 },
                maxWidth: { xs: 350, md: 250 }
              }}
              src='/img/vn-11134258-7ra0g-m7hncaye3xo341_xhdpi.jpg'
            />
            <Box
              component='img'
              sx={{
                height: 233,
                width: 350,
                maxHeight: { xs: 233, md: 167 },
                maxWidth: { xs: 350, md: 250 }
              }}
              src='/img/vn-11134258-7ra0g-m7hndl5ii6pi79_xhdpi.jpg'
            />
          </div>
        </div>

        <div className='flex justify-center items-center gap-2 mt-[2rem]'>
          {images.map((src, index) => (
            <div className='flex flex-col justify-center items-center gap-2' key={index}>
              <Box
                key={index}
                component='img'
                sx={{
                  height: 50,
                  width: 50,
                  maxHeight: { xs: 233, md: 167 },
                  maxWidth: { xs: 350, md: 250 }
                }}
                src={src.icon}
                alt={`Image ${index + 1}`}
              />
              <Typography>{src.title}</Typography>
            </div>
          ))}
        </div>
      </div>
      <Divider />

      <HomeCategorySlider />

      <HomeShoppeMall />

      <Box className='bg-[#808080]'>
        <p className='text text-center text-3xl mb-3 text-[#FFA500]'>Goi y hom nay</p>
        <Divider />
      </Box>
      {products ? (
        <Box
          sx={{
            display: 'grid',
            gridTemplateColumns: 'repeat(6, 1fr)',
            gap: 2,
            marginTop: 3,
            backgroundColor: '#f5f5f5'
          }}
        >
          {products.data.map((product) => (
            <Card
              sx={{
                ':hover': {
                  border: '1px solid #f53d2d'
                }
              }}
              key={product.id}
            >
              <CardContent>
                <Box
                  component='img'
                  sx={{
                    height: 233,
                    width: 350,
                    maxHeight: { xs: 233, md: 167 },
                    maxWidth: { xs: 350, md: 250 }
                  }}
                  alt={product.name}
                  src='https://images.unsplash.com/photo-1512917774080-9991f1c4c750?auto=format&w=350&dpr=2'
                />
                <Typography variant='h5' component='div'>
                  {product.name}
                </Typography>
                <Typography variant='body2' color='text.secondary'>
                  {product.description}
                </Typography>
                <Typography variant='body1'>Price: ${product.price_before_discount}</Typography>
                <Typography variant='body1'>Sold: {product.sold ? 'Yes' : 'No'}</Typography>
                <Typography variant='body1'>Rating: {product.rating}</Typography>
              </CardContent>
              <CardActions>{/* Add actions here if needed */}</CardActions>
            </Card>
          ))}
        </Box>
      ) : (
        <Loading />
      )}
    </Container>
  )
}
