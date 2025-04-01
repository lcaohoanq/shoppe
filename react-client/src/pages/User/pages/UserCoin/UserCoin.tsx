import React, { useState } from 'react'
import { FaRegClock } from 'react-icons/fa'
import { IoFastFoodOutline } from 'react-icons/io5'
import { BsCoin } from 'react-icons/bs'

type Transaction = {
  id: number
  icon: React.ElementType
  png_icon?: string
  title: string
  date: string
  amount: number
  desc?: string
  type: 'received' | 'spent'
}

// Sample data for all transactions
const allTransactions: Transaction[] = [
  { id: 1, icon: BsCoin, title: 'Hạn sử dụng của Shopee Xu', date: '22:34 01-01-2025', amount: -1000, type: 'spent' },
  { id: 2, icon: BsCoin, title: 'Hạn sử dụng của Shopee Xu', date: '11:08 02-12-2024', amount: -400, type: 'spent' },
  {
    id: 3,
    icon: IoFastFoodOutline,
    png_icon: '/coin/food.png',
    title: 'Gà rán và Mì Ý - Jollibee - EC Nguyễn Duy Trinh 2',
    date: '12:14 28-11-2024',
    amount: 300,
    desc: 'Đơn hoàn thành tại ShopeeFood',
    type: 'received'
  },
  {
    id: 4,
    icon: IoFastFoodOutline,
    title: 'Gà rán và Mì Ý - Jollibee - EC Nguyễn Duy Trinh 2',
    date: '17:34 18-11-2024',
    amount: 700,
    desc: 'Đơn hoàn thành tại ShopeeFood',
    type: 'received'
  },
  { id: 5, icon: BsCoin, title: 'Hạn sử dụng của Shopee Xu', date: '11:10 01-11-2024', amount: -100, type: 'spent' },
  {
    id: 6,
    icon: IoFastFoodOutline,
    title: 'Bún Chả Hà Nội An - Đông Tăng Long',
    date: '18:59 23-10-2024',
    amount: 200,
    desc: 'Đơn hoàn thành tại ShopeeFood',
    type: 'received'
  },
  {
    id: 7,
    icon: IoFastFoodOutline,
    title: 'Gà rán và Mì Ý - Jollibee - EC Nguyễn Duy Trinh 2',
    date: '12:39 19-10-2024',
    amount: 200,
    desc: 'Đơn hoàn thành tại ShopeeFood',
    type: 'received'
  },
  {
    id: 8,
    icon: BsCoin,
    title: 'Đăng nhập mỗi ngày',
    date: '21:14 19-09-2024',
    amount: 100,
    desc: 'Shopee Xu từ Đăng nhập mỗi ngày',
    type: 'received'
  }
]

// Additional sample data for "Đã Nhận" tab
const receivedTransactions = [
  {
    id: 9,
    icon: BsCoin,
    title: 'Mua sắm tại Shopee Mall',
    date: '14:25 05-12-2024',
    amount: 250,
    desc: 'Shopee Xu từ mua sắm tại Shopee Mall',
    type: 'received'
  },
  {
    id: 10,
    icon: BsCoin,
    title: 'Hoàn xu từ voucher',
    date: '09:15 01-12-2024',
    amount: 150,
    desc: 'Hoàn xu từ voucher giảm giá',
    type: 'received'
  },
  {
    id: 11,
    icon: BsCoin,
    title: 'Xu từ đánh giá sản phẩm',
    date: '16:40 25-11-2024',
    amount: 80,
    desc: 'Xu từ đánh giá sản phẩm có hình ảnh',
    type: 'received'
  }
]

// Additional sample data for "Đã Dùng" tab
const spentTransactions = [
  {
    id: 12,
    icon: BsCoin,
    title: 'Đổi voucher giảm giá 20k',
    date: '10:22 10-12-2024',
    amount: -500,
    desc: 'Đổi xu lấy voucher giảm giá',
    type: 'spent'
  },
  {
    id: 13,
    icon: BsCoin,
    title: 'Đổi voucher freeship',
    date: '09:45 05-12-2024',
    amount: -800,
    desc: 'Đổi xu lấy voucher miễn phí vận chuyển',
    type: 'spent'
  },
  {
    id: 14,
    icon: BsCoin,
    title: 'Mua sản phẩm với Shopee Xu',
    date: '20:30 28-11-2024',
    amount: -350,
    desc: 'Thanh toán một phần bằng Shopee Xu',
    type: 'spent'
  }
]

const UserCoin = () => {
  const [activeTab, setActiveTab] = useState('all')

  // Combine all transaction data without duplicates
  const allData = [
    ...allTransactions,
    ...receivedTransactions.filter((item) => !allTransactions.some((tx) => tx.id === item.id)),
    ...spentTransactions.filter((item) => !allTransactions.some((tx) => tx.id === item.id))
  ]

  // Function to get transactions based on active tab
  const getTransactionsByTab = () => {
    switch (activeTab) {
      case 'received':
        return allData
          .filter((tx) => tx.type === 'received')
          .sort(
            (a, b) =>
              new Date(b.date.split(' ')[1].split('-').reverse().join('-')) -
              new Date(a.date.split(' ')[1].split('-').reverse().join('-'))
          )
      case 'spent':
        return allData
          .filter((tx) => tx.type === 'spent')
          .sort(
            (a, b) =>
              new Date(b.date.split(' ')[1].split('-').reverse().join('-')) -
              new Date(a.date.split(' ')[1].split('-').reverse().join('-'))
          )
      default:
        return allData.sort(
          (a, b) =>
            new Date(b.date.split(' ')[1].split('-').reverse().join('-')) -
            new Date(a.date.split(' ')[1].split('-').reverse().join('-'))
        )
    }
  }

  const transactions = getTransactionsByTab()

  // Fixed initial balance
  const currentBalance = 1830 // Set a fixed balance instead of calculating

  // Calculate expiring coins
  const expiringCoins = 1500

  return (
    <div className='max-w-5xl mx-auto p-4 bg-white rounded-lg shadow-md'>
      {/* Header */}
      <div className='flex justify-between items-center border-b pb-2'>
        <div className='flex items-center gap-2 text-orange-500 font-semibold text-lg'>
          <BsCoin size={24} />
          <span className='text-yellow-400'>{currentBalance} Xu</span>
        </div>
        <span className='text-xs text-gray-500'>{expiringCoins} Shopee Xu sẽ hết hạn vào 28-02-2025</span>
      </div>

      {/* Tabs */}
      <div className='flex mt-4 border-b'>
        {[
          { id: 'all', label: 'Tất Cả Lịch Sử' },
          { id: 'received', label: 'Đã Nhận' },
          { id: 'spent', label: 'Đã Dùng' }
        ].map((tab) => (
          <button
            key={tab.id}
            className={`flex-1 text-center py-2 border-b-2 ${
              activeTab === tab.id
                ? 'border-orange-500 font-medium text-orange-500'
                : 'border-transparent text-gray-500 hover:text-gray-700'
            }`}
            onClick={() => setActiveTab(tab.id)}
          >
            {tab.label}
          </button>
        ))}
      </div>

      {/* Transaction List */}
      <div className='mt-4'>
        {transactions.length > 0 ? (
          transactions.map((txn) => (
            <div key={txn.id} className='flex items-center gap-4 p-3 border-b'>
              <div className='w-10 h-10 flex-shrink-0 flex justify-center items-center bg-gray-200 rounded-full'>
                <txn.icon size={24} className='text-gray-700' />
              </div>
              <div className='flex-1 min-w-0'>
                <p className='font-medium truncate'>{txn.title}</p>
                {txn.desc && <p className='text-xs text-gray-500 truncate'>{txn.desc}</p>}
                <p className='text-xs text-gray-400'>{txn.date}</p>
              </div>
              <span className={`font-semibold whitespace-nowrap ${txn.amount > 0 ? 'text-green-500' : 'text-red-500'}`}>
                {txn.amount > 0 ? `+${txn.amount}` : txn.amount}
              </span>
            </div>
          ))
        ) : (
          <div className='text-center py-8 text-gray-500'>
            <FaRegClock className='mx-auto mb-3 text-gray-400' size={32} />
            <p>Không có giao dịch nào trong khoảng thời gian này</p>
          </div>
        )}
      </div>

      {/* Footer */}
      <p className='text-center text-xs text-gray-500 mt-4'>Chỉ hiển thị các giao dịch trong vòng 1 năm qua</p>
    </div>
  )
}

export default UserCoin
