// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-nocheck
import { useQuery } from '@tanstack/react-query'
import { Helmet } from 'react-helmet-async'
import categoryApi from 'src/apis/category.api'
import productApi from 'src/apis/product.api'
import Pagination from 'src/components/Pagination'
import useQueryConfig from 'src/hooks/useQueryConfig'
import { ProductListConfig } from 'src/types/product.type'
import AsideFilter from './components/AsideFilter'
import Product from './components/Product/Product'
import SortProductList from './components/SortProductList'
import useCategories from 'src/hooks/useCategories'
import Loading from 'src/components/Loading'
import useProducts from 'src/hooks/useProducts'

export default function ProductList() {
  const queryConfig = useQueryConfig()

  // const { data: productsData, isLoading } = useQuery({
  //   queryKey: ['products', queryConfig],
  //   queryFn: () => {
  //     return productApi.getProducts(queryConfig as ProductListConfig)
  //   },
  //   keepPreviousData: true,
  //   staleTime: 3 * 60 * 1000
  // })
  const { data: productsData, isLoading, error } = useProducts()

  const { data: categoriesData } = useCategories()

  if (isLoading) return <Loading />
  if (error) return <div>Error when load Product List</div>

  return (
    <div className='bg-gray-200 py-6'>
      <Helmet>
        <title>Trang chủ | Shopee Clone</title>
        <meta name='description' content='Trang chủ dự án Shopee Clone' />
      </Helmet>
      <div className='container'>
        {productsData && (
          <div className='grid grid-cols-12 gap-6'>
            <div className='col-span-3'>
              <AsideFilter queryConfig={queryConfig} categories={categoriesData || []} />
            </div>
            <div className='col-span-9'>
              <SortProductList queryConfig={queryConfig} pageSize={productsData?.pagination?.page_size || 0} />
              <div className='mt-6 grid grid-cols-2 gap-3 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5'>
                {productsData.data.map((product, index: number) => (
                  <div className='col-span-1' key={product.id + index}>
                    <Product product={product} />
                  </div>
                ))}
              </div>
              <Pagination queryConfig={queryConfig} pageSize={productsData?.pagination?.page_size || 0} />
            </div>
          </div>
        )}
      </div>
    </div>
  )
}
