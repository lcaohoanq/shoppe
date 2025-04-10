import React from 'react'
import { FaFacebook, FaInstagram, FaLinkedin } from 'react-icons/fa'
import Headquarter from '../Headquarter'
import FooterLegal from '../FooterLegal'

const customerService = [
  'Trung Tâm Trợ Giúp Shopee',
  'Shopee Blog',
  'Shopee Mall',
  'Hướng Dẫn Mua Hàng/Đặt Hàng',
  'Hướng Dẫn Bán Hàng',
  'Ví ShopeePay',
  'Shopee Xu',
  'Đơn Hàng',
  'Trả Hàng/Hoàn Tiền',
  'Liên Hệ Shopee',
  'Chính Sách Bảo Hành'
]

const shopeeVietnam = [
  'Về Shopee',
  'Tuyển Dụng',
  'Điều Khoản Shopee',
  'Chính Sách Bảo Mật',
  'Shopee Mall',
  'Kênh Người Bán',
  'Flash Sale',
  'Tiếp Thị Liên Kết',
  'Liên Hệ Truyền Thông'
]

const payments = [
  'visa.png',
  'mastercard.png',
  'jcb.png',
  'amex.png',
  'cod.png',
  'installment.png',
  'spay.png',
  'spaylater.png'
]
const shipping = [
  'spx.png',
  'ghn.png',
  'viettel_post.png',
  'vnpost.png',
  'jtexpress.png',
  'grab.png',
  'ninjavan.png',
  'be.png',
  'ahamove.png'
]
const socialIcons = [
  {
    icon: FaFacebook,
    label: 'Facebook'
  },
  {
    icon: FaInstagram,
    label: 'Instagram'
  },
  {
    icon: FaLinkedin,
    label: 'LinkedIn'
  }
]
const appStores = ['qr.png', 'appstore.png', 'googleplay.png', 'appgallery.png']

const FooterCompact = () => {
  return (
    <footer className='w-full max-w-screen-xl mx-auto bg-white text-sm text-gray-700'>
      <div className='grid grid-cols-5 gap-6 p-8'>
        {/* Dịch vụ khách hàng */}
        <div className='w-full'>
          <h4 className='font-semibold mb-3'>Dịch vụ khách hàng</h4>
          <div className='space-y-1'>
            {customerService.map((item) => (
              <p key={item} className='hover:text-orange-500 cursor-pointer'>
                {item}
              </p>
            ))}
          </div>
        </div>

        {/* Shopee Việt Nam */}
        <div className='w-full'>
          <h4 className='font-semibold mb-3'>Shopee Việt Nam</h4>
          <div className='space-y-1'>
            {shopeeVietnam.map((item) => (
              <p key={item} className='hover:text-orange-500 cursor-pointer'>
                {item}
              </p>
            ))}
          </div>
        </div>

        {/* Thanh toán & Vận chuyển */}
        <div className='w-full'>
          <h4 className='font-semibold mb-3'>Thanh toán</h4>
          <div className='flex flex-wrap gap-2'>
            {payments.map((img) => (
              <div key={img} className='w-12 h-8 flex-shrink-0 shadow-sm border border-gray-100 rounded-md '>
                <img src={`/${img}`} alt={img} className='w-full h-full object-contain' />
              </div>
            ))}
          </div>
          <h4 className='font-semibold mt-4 mb-3'>Đơn vị vận chuyển</h4>
          <div className='flex flex-wrap gap-2'>
            {shipping.map((img) => (
              <div key={img} className='w-12 h-8 flex-shrink-0 shadow-sm border border-gray-100 rounded-md  bg-white'>
                <img src={`/shipping_unit/${img}`} alt={img} className='w-full h-full object-contain' />
              </div>
            ))}
          </div>
        </div>

        {/* Theo dõi Shopee */}
        <div className='w-full'>
          <h4 className='font-semibold mb-3'>Theo dõi Shopee</h4>
          <div className='flex flex-col gap-3'>
            {socialIcons.map((item, idx) => (
              <div key={idx} className='flex items-center gap-2 cursor-pointer'>
                <div className='w-6 h-6 flex-shrink-0 flex items-center justify-cente '>
                  <item.icon className='text-gray-700' />
                </div>
                <span className='hover:text-orange-500'>{item.label}</span>
              </div>
            ))}
          </div>
        </div>

        {/* Ứng dụng Shopee */}
        <div className='w-full'>
          <h4 className='font-semibold mb-3'>Tải ứng dụng Shopee</h4>
          <div className='flex flex-col gap-2'>
            {appStores.map((img) => (
              <div key={img} className='w-24 h-8 flex-shrink-0'>
                <img src={`/ads/${img}`} alt={img} className='w-full h-full object-contain' />
              </div>
            ))}
          </div>
        </div>
      </div>

      {/* <div className='flex'> */}
      <div className='border-t border-gray-200 p-4 text-center text-xs text-gray-500'>
        © 2025 Shopee. Tất cả các quyền được bảo lưu.
      </div>
      {/* <Headquarter /> */}
      {/* </div> */}

      <FooterLegal />
    </footer>
  )
}

export default FooterCompact
