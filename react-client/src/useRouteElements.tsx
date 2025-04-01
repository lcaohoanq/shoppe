import { lazy, Suspense, useContext } from 'react'
import { Navigate, Outlet, useRoutes } from 'react-router-dom'
import path from 'src/constants/path'
import { AppContext } from './contexts/app.context'
import MainLayout from './layouts/MainLayout'
import RegisterLayout from './layouts/RegisterLayout'
// import Login from './pages/Login'
// import ProductList from './pages/ProductList'
// import Profile from './pages/User/pages/Profile'
// import Register from './pages/Register'
// import ProductDetail from './pages/ProductDetail'
// import Cart from './pages/Cart'
import CartLayout from './layouts/CartLayout'
import MainLayoutCompact from './layouts/MainLayoutCompact'
import UserLayout from './pages/User/layouts/UserLayout'
// import ChangePassword from './pages/User/pages/ChangePassword'
// import HistoryPurchase from './pages/User/pages/HistoryPurchase'
// import NotFound from './pages/NotFound'

const Login = lazy(() => import('./pages/Login'))
const ProductList = lazy(() => import('./pages/ProductList'))
const Register = lazy(() => import('./pages/Register'))
const ProductDetail = lazy(() => import('./pages/ProductDetail'))
const Cart = lazy(() => import('./pages/Cart'))
const HistoryPurchase = lazy(() => import('./pages/User/pages/HistoryPurchase'))
const NotFound = lazy(() => import('./pages/NotFound'))
const Home = lazy(() => import('./pages/Home'))

//UserPage
/** Account */
const ChangePassword = lazy(() => import('./pages/User/pages/Account/ChangePassword'))
const Profile = lazy(() => import('./pages/User/pages/Account/Profile'))
const UserAddress = lazy(() => import('./pages/User/pages/Account/Address/UserAddress'))
const UserBank = lazy(() => import('./pages/User/pages/Account/Bank/UserAccountBank'))
const UserSettingNotify = lazy(() => import('./pages/User/pages/Account/SettingNotify/UserSettingNotify'))
const UserPrivacySetting = lazy(() => import('./pages/User/pages/Account/PrivacySetting/UserPrivacySetting'))

const UserNotificationPromotion = lazy(() => import('./pages/User/pages/Notification/Promotion'))
const UserNotificationOrder = lazy(() => import('./pages/User/pages/Notification/Order'))
const UserNotificationWallet = lazy(() => import('./pages/User/pages/Notification/Wallet'))
const UserNotificationShopeeUpdate = lazy(() => import('./pages/User/pages/Notification/ShopeeUpdate'))

/** Coin */
const UserCoin = lazy(() => import('./pages/User/pages/UserCoin'))

/** Voucher */
const UserVoucher = lazy(() => import('./pages/User/pages/Voucher'))

/**
 * Để tối ưu re-render thì nên ưu tiên dùng <Outlet /> thay cho {children}
 * Lưu ý là <Outlet /> nên đặt ngay trong component `element` thì mới có tác dụng tối ưu
 * Chứ không phải đặt bên trong children của component `element`
 */

//  ✅ Tối ưu re-render
// export default memo(function RegisterLayout({ children }: Props) {
//  return (
//    <div>
//      <RegisterHeader />
//      {children}
//      <Outlet />
//      <Footer />
//    </div>
//  )
//  })

//  ❌ Không tối ưu được vì <Outlet /> đặt vào vị trí children
// Khi <Outlet /> thay đổi tức là children thay đổi
// Dẫn đến component `RegisterLayout` bị re-render dù cho có dùng React.memo như trên
// <RegisterLayout>
//   <Outlet />
// </RegisterLayout>

function ProtectedRoute() {
  const { isAuthenticated } = useContext(AppContext)
  return isAuthenticated ? <Outlet /> : <Navigate to='/login' />
}

function RejectedRoute() {
  const { isAuthenticated } = useContext(AppContext)

  return !isAuthenticated ? <Outlet /> : <Navigate to='/' />
}

export default function useRouteElements() {
  const routeElements = useRoutes([
    {
      path: '',
      element: <RejectedRoute />,
      children: [
        {
          path: '',
          element: <RegisterLayout />,
          children: [
            {
              path: path.login,
              element: (
                <Suspense>
                  <Login />
                </Suspense>
              )
            },
            {
              path: path.register,
              element: (
                <Suspense>
                  <Register />
                </Suspense>
              )
            }
          ]
        }
      ]
    },
    {
      path: '',
      // element: <ProtectedRoute />,
      children: [
        {
          path: path.cart,
          element: (
            <CartLayout>
              <Suspense>
                <Cart />
              </Suspense>
            </CartLayout>
          )
        },
        {
          path: path.user,
          element: <MainLayoutCompact />,
          children: [
            {
              path: '',
              element: <UserLayout />,
              children: [
                {
                  path: path.profile,
                  element: (
                    <Suspense>
                      <Profile />
                    </Suspense>
                  )
                },
                {
                  path: path.user_detail.shopee_xu,
                  element: (
                    <Suspense>
                      <UserCoin />
                    </Suspense>
                  )
                },
                {
                  path: path.user_detail.voucher,
                  element: (
                    <Suspense>
                      <UserVoucher />
                    </Suspense>
                  )
                },
                {
                  path: path.user_detail.notify.order,
                  element: (
                    <Suspense>
                      <UserNotificationOrder />
                    </Suspense>
                  )
                },
                {
                  path: path.user_detail.notify.promotion,
                  element: (
                    <Suspense>
                      <UserNotificationPromotion />
                    </Suspense>
                  )
                },
                {
                  path: path.user_detail.notify.wallet,
                  element: (
                    <Suspense>
                      <UserNotificationWallet />
                    </Suspense>
                  )
                },
                {
                  path: path.user_detail.notify.shopee,
                  element: (
                    <Suspense>
                      <UserNotificationShopeeUpdate />
                    </Suspense>
                  )
                },

                {
                  path: path.user_detail.account.address,
                  element: (
                    <Suspense>
                      <UserAddress />
                    </Suspense>
                  )
                },

                {
                  path: path.user_detail.account.bank,
                  element: (
                    <Suspense>
                      <UserBank />
                    </Suspense>
                  )
                },

                {
                  path: path.user_detail.account.settings_notify,
                  element: (
                    <Suspense>
                      <UserSettingNotify />
                    </Suspense>
                  )
                },

                {
                  path: path.user_detail.account.privacy,
                  element: (
                    <Suspense>
                      <UserPrivacySetting />
                    </Suspense>
                  )
                },

                {
                  path: path.changePassword,
                  element: (
                    <Suspense>
                      <ChangePassword />
                    </Suspense>
                  )
                },
                {
                  path: path.historyPurchase,
                  element: (
                    <Suspense>
                      <HistoryPurchase />
                    </Suspense>
                  )
                }
              ]
            }
          ]
        }
      ]
    },
    {
      path: '',
      element: <MainLayout />,
      children: [
        {
          path: 'home',
          element: (
            <Suspense>
              <Home />
            </Suspense>
          )
        },
        {
          path: path.productDetail,
          element: (
            <Suspense>
              <ProductDetail />
            </Suspense>
          )
        },
        {
          path: '',
          index: true,
          element: (
            <Suspense>
              <ProductList />
            </Suspense>
          )
        },
        {
          path: '*',
          element: (
            <Suspense>
              <NotFound />
            </Suspense>
          )
        }
      ]
    }
    // },
    // {
    //   path: path.productDetail,
    //   element: (
    //     <MainLayout>
    //       <Suspense>
    //         <ProductDetail />
    //       </Suspense>
    //     </MainLayout>
    //   )
    // },
    // {
    //   path: '',
    //   index: true,
    //   element: (
    //     <MainLayout>
    //       <Suspense>
    //         <ProductList />
    //       </Suspense>
    //     </MainLayout>
    //   )
    // },
    // {
    //   path: '*',
    //   element: (
    //     <MainLayout>
    //       <Suspense>
    //         <NotFound />
    //       </Suspense>
    //     </MainLayout>
    //   )
    // }
  ])
  return routeElements
}
