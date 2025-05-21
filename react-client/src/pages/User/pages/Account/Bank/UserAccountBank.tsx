import React from 'react'

const UserAccountBank = () => {
  return (
    <div
      className="max-w-5xl mx-auto p-4 bg-white rounded-sm shadow-md min-h-[600px] flex flex-col justify-between">
      <div className="flex flex-col flex-1 p-2">
        <div className="flex justify-between items-center mb-4">
          <div className="text-xl text-black">Thẻ Tín dụng/Ghi nợ</div>
          <button className="bg-orange p-3 text-white">+ Thêm Thẻ Mới</button>
        </div>
        <div className='w-full mt-2 border-t-2 py-2 border-gray-100'></div>
        <div className="flex-1 flex items-center justify-center text-lg text-gray-500">
          Bạn chưa liên kết thẻ.
        </div>
      </div>

      <div className="flex flex-col flex-1 p-2 mt-4">
        <div className="flex justify-between items-center mb-4">
          <div className="text-xl text-black">Tài khoản ngân hàng của tôi</div>
          <button className="bg-orange p-3 text-white">+ Thêm Ngân Hàng Liên Kết</button>
        </div>
        <div className='w-full mt-2 border-t-2 py-2 border-gray-100'></div>
        <div className="flex-1 flex items-center justify-center text-lg text-gray-500">
          Bạn chưa có tài khoản ngân hàng.
        </div>
      </div>
    </div>
  )
}

export default UserAccountBank
