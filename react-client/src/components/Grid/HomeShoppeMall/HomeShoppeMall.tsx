import { Box, Container, Grid, Typography } from '@mui/material'
import Paper from '@mui/material/Paper'
import { experimentalStyled as styled } from '@mui/material/styles'

const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: '#fff',
  ...theme.typography.body2,
  padding: theme.spacing(2),
  textAlign: 'center',
  color: theme.palette.text.secondary,
  ...theme.applyStyles('dark', {
    backgroundColor: '#1A2027'
  })
}))

const imageStyle = {
  width: 25,
  height: 25,
  borderRadius: '50%',
  mr: 1.25
}

const textStyle = {
  fontSize: '1rem'
}

const mallItems = [
  {
    text: 'Ưu đãi đến 50%',
    image: '/mall/loreal.webp'
  },
  {
    text: 'Mua 1 tặng 1',
    image: '/mall/lifebouy.webp'
  },
  {
    text: 'Mua là có quà',
    image: '/mall/anissa.webp'
  },
  {
    text: 'Quà mọi đơn',
    image: '/mall/larocheposay.webp'
  },
  {
    text: 'Mua 1 tặng 1',
    image: '/mall/cocoon.webp'
  },
  {
    text: 'Mua 1 tặng 1',
    image: '/mall/vaseline.webp'
  },
  {
    text: 'Mua 1 được 2',
    image: '/mall/omo.webp'
  },
  {
    text: 'Mua là có qusà',
    image: '/mall/garnier.webp'
  }
]

const HomeShoppeMall = () => {
  return (
    <Container sx={{ backgroundColor: 'white', p: 1 }}>
      <Box
        sx={{
          display: 'flex',
          justifyContent: 'space-between',
          alignItems: 'center',
          m: 3,
          fontSize: '1rem'
        }}
      >
        <Box sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
          <Typography sx={{ color: 'red', ...textStyle }}>SHOPPE MALL</Typography>

          <Box sx={{ display: 'flex', alignItems: 'center' }}>
            <Box component='img' src='/mall/return_order.png' alt='Logo' sx={imageStyle} />
            <Typography sx={textStyle}>Trả Hàng Miễn Phí 15 Ngày</Typography>
          </Box>

          <Box sx={{ display: 'flex', alignItems: 'center' }}>
            <Box component='img' src='/mall/auth.png' alt='Logo' sx={imageStyle} />
            <Typography sx={textStyle}>Hàng chính hãng 100%</Typography>
          </Box>

          <Box sx={{ display: 'flex', alignItems: 'center' }}>
            <Box component='img' src='/mall/free_ship.png' alt='Logo' sx={imageStyle} />
            <Typography sx={textStyle}>Trả Hàng Miễn Phí 15 Ngày</Typography>
          </Box>
        </Box>

        <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
          <Box component='img' src='/mall/return_order.png' alt='Logo' sx={imageStyle} />
          <Typography sx={{ color: 'red', ...textStyle }}>Xem Tất Cả</Typography>
        </Box>
      </Box>

      <Box
        sx={{
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center'
        }}
      >
        <Box component='img' src='/img/vn-11134258-7ras8-m4iespqewa2vee.png' />

        {/* Grid with mallItems */}
        <Grid
          container
          spacing={2}
          sx={{
            width: '100%',
            maxWidth: '900px'
          }}
        >
          {mallItems.map((item, index) => (
            <Grid item xs={12} sm={6} md={3} key={index}>
              <Box
                sx={{
                  display: 'flex',
                  flexDirection: 'column',
                  alignItems: 'center',
                  textAlign: 'center',
                  p: 2,
                  borderRadius: 2
                }}
              >
                <Box
                  component='img'
                  src={item.image}
                  alt={`icon-${index}`}
                  sx={{
                    borderRadius: '50%',
                    mb: 1
                  }}
                />
                <Typography variant='body1' sx={{ color: 'red' }}>
                  {item.text}
                </Typography>
              </Box>
            </Grid>
          ))}
        </Grid>
      </Box>
    </Container>
  )
}

export default HomeShoppeMall
