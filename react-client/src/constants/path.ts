const path = {
  home: '/',
  test: '/home',
  user: '/user',
  profile: '/user/profile',
  changePassword: '/user/password',
  historyPurchase: '/user/purchase',
  login: '/login',
  register: '/register',
  logout: '/logout',
  productDetail: ':nameId',
  cart: '/cart',
  user_detail: {
    notify: '/user/notify',
    bank: '/user/bank',
    address: '/user/address',
    change_password: '/user/password',
    settings_notify: '/user/notify/settings',
    privacy: '/user/privacy',
    voucher: '/user/voucher',
    shopee_xu: '/user/shopee-xu',
    sale : '/user/sale'
  }
} as const

export default path
