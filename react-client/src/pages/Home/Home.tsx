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

          <Box className='bg-[#808080]'>
            <p className='text text-center text-3xl mb-3 text-[#FFA500]'>Goi y hom nay</p>
            <Divider />
          </Box>
          {products ? (
            <Box sx={{ backgroundColor: '#f5f5f5', py: 5 }}>
              <Container
                maxWidth='lg'
                sx={{
                  backgroundColor: '#fff',
                  borderRadius: 2,
                  boxShadow: 1,
                  padding: 3
                }}
              >
                <Typography variant='h5' align='center' sx={{ color: '#f53d2d', mb: 3 }}>
                  Gợi ý hôm nay
                </Typography>
                <Divider sx={{ mb: 3 }} />

                <Box
                  sx={{
                    display: 'grid',
                    gridTemplateColumns: 'repeat(auto-fill, minmax(180px, 1fr))',
                    gap: 2
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
                            height: 180,
                            width: '100%',
                            objectFit: 'cover',
                            borderRadius: 1
                          }}
                          alt={product.name}
                          src='https://images.unsplash.com/photo-1512917774080-9991f1c4c750?auto=format&w=350&dpr=2'
                        />
                        <Typography variant='h6' noWrap>
                          {product.name}
                        </Typography>
                        <Typography variant='body2' color='text.secondary' noWrap>
                          {product.description}
                        </Typography>
                        <Typography variant='body1'>${product.price_before_discount}</Typography>
                        <Typography variant='caption'>Đã bán: {product.sold ? '✔' : '✖'}</Typography>
                        <Typography variant='caption'>Đánh giá: {product.rating}</Typography>
                      </CardContent>
                    </Card>
                  ))}
                </Box>
              </Container>
            </Box>
          ) : (
            <Loading />
          )}
        </Box>
      </Box>
    </div>
  )
}
