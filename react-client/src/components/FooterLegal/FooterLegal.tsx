import React from 'react'

const FooterLegal = () => {
  return (
    <div className='w-full bg-white text-gray-600 text-xs text-center py-6 border-t border-gray-200'>
      <div className='flex justify-center space-x-4 mb-4 text-gray-500'>
        <span className='cursor-pointer hover:text-orange-500'>CHÍNH SÁCH BẢO MẬT</span>
        <span>|</span>
        <span className='cursor-pointer hover:text-orange-500'>QUY CHẾ HOẠT ĐỘNG</span>
        <span>|</span>
        <span className='cursor-pointer hover:text-orange-500'>CHÍNH SÁCH VẬN CHUYỂN</span>
        <span>|</span>
        <span className='cursor-pointer hover:text-orange-500'>CHÍNH SÁCH TRẢ HÀNG VÀ HOÀN TIỀN</span>
      </div>

      <div className='flex justify-center space-x-4 mb-4'>
        <img
          src='http://online.gov.vn/HomePage/WebsiteDisplay.aspx?DocId=18375'
          alt='Certification 1'
          className='h-10'
        />
        <img src='http://online.gov.vn/HomePage/AppDisplay.aspx?DocId=29' alt='Certification 2' className='h-10' />
        <img src='https://shopee.vn/docs/170' alt='Certification 3' className='h-10' />
      </div>

      <p className='font-semibold'>Công ty TNHH Shopee</p>
      <p className='max-w-3xl mx-auto leading-relaxed'>
        Địa chỉ: Tầng 4-5-6-8, Tòa nhà Capital Place, số 29 đường Liễu Giai, Phường Ngọc Khánh, Quận Ba Đình, Thành phố
        Hà Nội, Việt Nam. Chăm sóc khách hàng: Gọi tổng đài Shopee (miễn phí) hoặc Trò chuyện với Shopee ngay trên Trung
        tâm trợ giúp.
      </p>
      <p>Chịu Trách Nhiệm Quản Lý Nội Dung: Nguyễn Bá Anh Tuấn</p>
      <p>Mã số doanh nghiệp: 0106773786 do Sở Kế hoạch và Đầu tư TP Hà Nội cấp lần đầu ngày 10/02/2015</p>
      <p>© 2015 - Bản quyền thuộc về Công ty TNHH Shopee</p>
    </div>
  )
}

export default FooterLegal
