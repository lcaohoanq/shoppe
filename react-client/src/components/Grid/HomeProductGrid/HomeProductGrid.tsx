import { Box, Card, CardContent, Container, Divider, Typography } from '@mui/material'
import Loading from 'src/components/Loading'
import useProducts from 'src/hooks/useProducts'
const HomeProductGrid = () => {
  const { data: products, error, isLoading } = useProducts()

  if (isLoading) return <Loading />
  if (error) return <div>Error loading products...</div>

  return (
    <>
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
    </>
  )
}

export default HomeProductGrid
