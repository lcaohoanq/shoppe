const path = {
  home: '/',
  test: '/home',
  user: '/user',
  profile: '/user/account/profile',
  changePassword: '/user/password',
  historyPurchase: '/user/purchase',
  login: '/login',
  register: '/register',
  logout: '/logout',
  productDetail: ':nameId',
  saleByMonth: {
    '1': '/m/1-1',
    '2': '/m/2-2',
    '3': '/m/3-3',
    '4': '/m/4-4',
    '5': '/m/5-5',
    '6': '/m/6-6',
    '7': '/m/7-7',
    '8': '/m/8-8',
    '9': '/m/9-9',
    '10': '/m/10-10',
    '11': '/m/11-11',
    '12': '/m/12-12'
  },
  cart: '/cart',
  user_detail: {
    notify: {
      order: '/user/notifications/order',
      promotion: '/user/notifications/promotion',
      wallet: '/user/notifications/wallet',
      shopee: '/user/notifications/shopee'
    },
    account: {
      bank: '/user/bank',
      address: '/user/address',
      change_password: '/user/password',
      settings_notify: '/user/notifications/settings',
      privacy: '/user/privacy'
    },
    voucher: '/user/voucher-wallet',
    shopee_xu: '/user/coin'
  }
} as const

export default path
