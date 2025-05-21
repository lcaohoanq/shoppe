import { Box, Container, Divider, Typography } from '@mui/material'
import { useEffect, useState } from 'react'
import { homeAssets } from 'src/assets/home'
import BannerModal from 'src/components/BannerModal'
import HomeProductGrid from 'src/components/Grid/HomeProductGrid/HomeProductGrid'
import HomeShoppeMall from 'src/components/Grid/HomeShoppeMall/HomeShoppeMall'
import HomeCategorySlider from 'src/components/Slider/HomeCategorySlider/HomeCategorySlider'
import HomeSlider from 'src/components/Slider/HomeSlider/HomeSlider'

export default function Home() {
  const [openModal, setOpenModal] = useState(true)
  const [randomBanner, setRandomBanner] = useState('')

  useEffect(() => {
    setRandomBanner(homeAssets.top.bannerSlider[Math.floor(Math.random() * homeAssets.top.bannerSlider.length)])
  }, [])

  const handleCloseModal = () => {
    setOpenModal(false)
  }

  return (
    <>
      <BannerModal open={openModal} onClose={handleCloseModal} bannerSrc={randomBanner} />

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

          <HomeProductGrid />
        </Box>
      </Box>
    </>
  )
}
