import { Box, Card, CardActions, CardContent, Container, Divider, Typography } from '@mui/material'
import axios from 'axios'
import { useEffect, useState } from 'react'
import Loading from 'src/components/Loading'
import { Category } from 'src/types/category.type'
import { Product } from 'src/types/product.type'
import { User } from 'src/types/user.type'

type GraphQLResponse<T> = {
  data: T
  errors: Object | null
}

type ProductResponse = {
  id: string
  name: string
  description: string
  price: number
  priceBeforeDiscount: number
  quantity: number
  sold: boolean
  view: number
  rating: number
  status: ProductStatus
  isActive: boolean
  category: Category
  shopOwner: User
  wareHouse: WareHouse
  images: ProductImage
  cartItems: CartItem
}

type ProductImage = {
  id: string
  product: Product
  fileName: string
  fileType: string
  fileSize: string
  imageUrl: string
  videoUrl: string
}

type CartItem = {
  id: string
}

type WareHouse = {
  id: string
  name: WarehouseName
  quantity: number
  reserved: number
  reorderPoint: number
}
enum WarehouseName {
  NORTH,
  CENTRAL,
  SOUTH
}

enum ProductStatus {
  UNVERIFIED,
  VERIFIED,
  REJECTED
}

export default function Home() {
  const [data, setData] = useState<ProductResponse[] | undefined>()

  const getProduct = async () => {
    const response = await axios.post<{ data: { products: ProductResponse[] } }>('http://localhost:8080/graphql', {
      query: ` 
          query {
            products {
              id
              name
              description
              price
              priceBeforeDiscount
              quantity
              sold
              view
              rating
              status
              isActive
            }
          }
        `
    })
    return response.data.data.products
  }

  useEffect(() => {
    const fetchData = async () => {
      const products = await getProduct()
      setData(products)
    }
    fetchData()
  }, [])

  return (
    <Container maxWidth='lg' sx={{ mt: 3 }}>
      {data ? (
        <Box
          sx={{
            display: 'grid',
            gridTemplateColumns: 'repeat(6, 1fr)',
            gap: 2
          }}
        >
          {data.map((product) => (
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
                  alt='The house from the offer.'
                  src='https://images.unsplash.com/photo-1512917774080-9991f1c4c750?auto=format&w=350&dpr=2'
                />
                <Typography variant='h5' component='div'>
                  {product.name}
                </Typography>
                <Typography variant='body2' color='text.secondary'>
                  {product.description}
                </Typography>
                <Typography variant='body1'>Price: ${product.price}</Typography>
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
      <Box className='bg-[#808080]'>
        <p className='text text-center text-3xl mb-3 text-[#FFA500]'>Goi y hom nay</p>
        <Divider />
      </Box>
    </Container>
  )
}
