import { Box, Card, CardActions, CardContent, Container, Divider, IconButton, Modal, Typography } from '@mui/material'
import HomeShoppeMall from 'src/components/Grid/HomeShoppeMall/HomeShoppeMall'
import HomeSlider from 'src/components/Slider/HomeSlider/HomeSlider'
import Loading from 'src/components/Loading'
import HomeCategorySlider from 'src/components/Slider/HomeCategorySlider/HomeCategorySlider'
import useProducts from 'src/hooks/useProducts'
import { useEffect, useState } from 'react'
import CloseIcon from '@mui/icons-material/Close'
import { styles } from './style'
import { homeAssets } from 'src/assets/home'
import CardOverflow from '@mui/joy/CardOverflow'
import AspectRatio from '@mui/joy/AspectRatio'

export default function Home() {
  const [openModal, setOpenModal] = useState(true)
  const [randomBanner, setRandomBanner] = useState('')
  const { data: products, error, isLoading } = useProducts()

  useEffect(() => {
    setRandomBanner(homeAssets.top.bannerSlider[Math.floor(Math.random() * homeAssets.top.bannerSlider.length)])
  }, [])

  const handleCloseModal = () => {
    setOpenModal(false)
  }

  if (isLoading) return <Loading />
  if (error) return <div>Error loading products...</div>

  return (
    <div>
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

      <Box>
        <Container>
          <div className='flex justify-between gap-2 mt-7'>
            <HomeSlider />
            <div className='flex flex-col gap-1 '>
              <Box
                component='img'
                sx={{
                  height: 120,
                  width: 350,
                  borderRadius: '3px'
                  // maxHeight: { xs: 233, md: 167 },
                  // maxWidth: { xs: 350, md: 250 }
                }}
                src={homeAssets.top.rightSlider[0]}
              />
              <Box
                component='img'
                sx={{
                  height: 120,
                  width: 350,
                  borderRadius: '3px'
                  // maxHeight: { xs: 233, md: 167 },
                  // maxWidth: { xs: 350, md: 250 }
                }}
                src={homeAssets.top.rightSlider[1]}
              />
            </div>
          </div>

          <div className='flex justify-between items-center gap-2 mt-[2rem] mb-5'>
            {homeAssets.top.section_banner.map((src, index) => (
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
                <Typography
                  sx={{
                    fontSize: '0.8rem'
                  }}
                >
                  {src.title}
                </Typography>
              </div>
            ))}
          </div>
        </Container>

        <Box sx={{ backgroundColor: '#f5f5f5', width: '100%' }}>
          <Divider />

          <HomeCategorySlider />

          <HomeShoppeMall />

          {products ? (
            <Container sx={{ backgroundColor: '#f5f5f5', py: 5 }}>
              <Box sx={{ backgroundColor: 'white', pt: 2 }}>
                <Typography variant='body1' align='center' sx={{ color: '#f53d2d', mb: 2, textTransform: 'uppercase' }}>
                  Gợi ý hôm nay
                </Typography>
                <Divider sx={{ backgroundColor: '#f53d2d', borderBottomWidth: 5 }} />
              </Box>

              <Box
                sx={{
                  mt: 3,
                  display: 'grid',
                  gridTemplateColumns: 'repeat(auto-fill, minmax(180px, 1fr))',
                  gap: 1.5,
                  backgroundColor: '#f5f5f5'
                }}
              >
                {products.data.map((product) => (
                  <Card
                    sx={{
                      ':hover': {
                        border: '1px solid #f53d2d'
                      },
                      p: 0, // Xóa padding mặc định nếu muốn ảnh chiếm toàn bộ card
                      borderRadius: 0
                    }}
                    key={product.id}
                  >
                    <Box
                      component='img'
                      sx={{
                        width: '100%',
                        height: 180,
                        objectFit: 'cover',
                        display: 'block',
                        borderTopLeftRadius: 'inherit',
                        borderTopRightRadius: 'inherit'
                      }}
                      alt={product.name}
                      src='https://images.unsplash.com/photo-1512917774080-9991f1c4c750?auto=format&w=350&dpr=2'
                    />

                    <CardContent sx={{ padding: 1 }}>
                      <Typography variant='body2' noWrap>
                        {product.name}
                      </Typography>
                      <Typography variant='body2' color='text.secondary' noWrap>
                        {product.description}
                      </Typography>
                      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                        <Typography variant='body1'>${product.price_before_discount}</Typography>
                        <Typography variant='caption'>Đã bán: {product.sold ? '✔' : '✖'}</Typography>
                      </Box>
                    </CardContent>
                  </Card>
                ))}
              </Box>
            </Container>
          ) : (
            <Loading />
          )}
        </Box>
      </Box>
    </div>
  )
}
