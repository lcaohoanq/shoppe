import { Divider } from '@mui/material'
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
    <>
      <div>
        <div className='m-40'>
          {data ? (
            <div className='grid grid-cols-6'>
              {data.map((product) => (
                <div className='mb-3 p-3' key={product.id}>
                  <h1>{product.name}</h1>
                  <p>{product.description}</p>
                  <p>Price: ${product.price}</p>
                  <p>Sold: {product.sold ? 'Yes' : 'No'}</p>
                  <p>Rating: {product.rating}</p>
                </div>
              ))}
            </div>
          ) : (
            <Loading />
          )}
          <div className='bg-[#808080]'>
            <p className='text text-center text-3xl mb-3 text-[#FFA500]'>Goi y hom nay</p>
            <Divider />
          </div>
        </div>
      </div>
    </>
  )
}
