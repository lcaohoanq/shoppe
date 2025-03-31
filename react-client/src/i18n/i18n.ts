import i18n from 'i18next'
import {initReactI18next} from 'react-i18next'
import HOME_EN from 'src/locales/en/home.json'
import PRODUCT_EN from 'src/locales/en/product.json'
import HOME_VI from 'src/locales/vi/home.json'
import PRODUCT_VI from 'src/locales/vi/product.json'

// user_detail
import USER_DETAIL_EN from 'src/locales/en/user_detail.json'
import USER_DETAIL_VI from 'src/locales/vi/user_detail.json'
import USER_DETAIL_JP from 'src/locales/jp/user_detail.json'

export const locales = {
  en: 'English',
  vi: 'Tiếng Việt',
  jp: '日本語'
} as const

export const resources = {
  en: {
    home: HOME_EN,
    product: PRODUCT_EN,
    user_detail: USER_DETAIL_EN
  },
  vi: {
    home: HOME_VI,
    product: PRODUCT_VI,
    user_detail: USER_DETAIL_VI
  },
  jp: {
    user_detail: USER_DETAIL_JP
  }
} as const

export const defaultNS = 'product'

if(!i18n.isInitialized){
  i18n.use(initReactI18next).init({
    resources,
    lng: 'vi',
    ns: ['home', 'product', 'user_detail'],
    fallbackLng: 'vi',
    defaultNS,
    interpolation: {
      escapeValue: false // react already safes from xss
    }
  })
}

export default i18n;