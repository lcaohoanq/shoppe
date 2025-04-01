import React from 'react'

const UserNotificationOrder = () => {
  return (
    <div className='max-w-5xl mx-auto p-4 bg-white rounded-lg shadow-md min-h-[450px] flex flex-col items-center justify-center'>
      <img src='/notifications/order.png' alt='order' width={100} className='mx-auto' />
      <h2 className='text-xl font-semibold text-center mt-4'>Chưa có cập nhật đơn hàng</h2>
    </div>
  )
}

export default UserNotificationOrder
