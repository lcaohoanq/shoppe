import { Box, Grid, Typography } from '@mui/material'
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

const HomeShoppeMall = () => {
  return (
    <Box>
      <Box
        sx={{
          display: 'flex',
          justifyContent: 'space-between',
          m: 3
        }}
      >
        <Typography variant='h5'>SHOPPE MALL</Typography>
        <Typography variant='h5'>Trả Hàng Miễn Phí 15 Ngày</Typography>
        <Typography variant='h5'>Hàng chính hãng 100%</Typography>
        <Typography variant='h5'>Trả Hàng Miễn Phí 15 Ngày</Typography>
        <Typography>Xem Tất Cả</Typography>
      </Box>

      <Box
        sx={{
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center'
        }}
      >
        <Box
          component='img'
          sx={{
            height: 463,
            width: 389
          }}
          src='/img/vn-11134258-7ras8-m4iespqewa2vee.png'
        />

        {/* Updated Grid with 4 columns and 2 rows */}
        <Grid
          container
          spacing={2} // Space between grid items
          sx={{
            width: '100%', // You can adjust this to fit your layout
            maxWidth: '900px' // Optionally limit max width for larger screens
          }}
        >
          {/* 8 items will render in 4 columns with 2 rows */}
          {Array.from(Array(8)).map((_, index) => (
            <Grid item xs={12} sm={6} md={3} key={index}>
              <Item>Item {index + 1}</Item>
            </Grid>
          ))}
        </Grid>
      </Box>
    </Box>
  )
}

export default HomeShoppeMall
