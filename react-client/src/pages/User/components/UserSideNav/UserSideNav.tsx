import { SetStateAction, useContext, useState } from 'react'
import { Link, NavLink } from 'react-router-dom'
import path from 'src/constants/path'
import { getAvatarUrl } from 'src/utils/utils'
import { AppContext } from 'src/contexts/app.context'
import { useTranslation } from 'react-i18next'

// Define the translation keys type to match exactly what's expected
type TranslationKey =
  | 'side_nav.edit_profile'
  | 'side_nav.notify'
  | 'side_nav.my_account'
  | 'side_nav.history_purchase'
  | 'side_nav.voucher'
  | 'side_nav.shopee_coin'
  | 'side_nav.sale'

interface SubMenuItem {
  title: string
  path: string
}

interface MenuItem {
  titleKey: TranslationKey
  icon: string
  path?: string
  subItems?: SubMenuItem[]
}

export default function UserSideNav() {
  const { profile } = useContext(AppContext)
  const { t } = useTranslation(['user_detail'])
  const [expandedMenu, setExpandedMenu] = useState<number | null>(null)

  const menuItems: MenuItem[] = [
    {
      titleKey: 'side_nav.notify',
      icon: 'https://down-vn.img.susercontent.com/file/e10a43b53ec8605f4829da5618e0717c',
      subItems: [
        { title: 'Cập Nhật Đơn Hàng', path: path.user_detail.notify.order },
        { title: 'Khuyến Mãi', path: path.user_detail.notify.promotion },
        { title: 'Cập Nhật Ví', path: path.user_detail.notify.wallet },
        { title: 'Cập Nhật Shopee', path: path.user_detail.notify.shopee }
      ]
    },
    {
      titleKey: 'side_nav.my_account',
      icon: 'https://cf.shopee.vn/file/ba61750a46794d8847c3f463c5e71cc4',
      subItems: [
        { title: 'Hồ Sơ', path: path.profile },
        { title: 'Ngân Hàng', path: path.user_detail.account.bank },
        { title: 'Địa Chỉ', path: path.user_detail.account.address },
        { title: 'Đổi Mật Khẩu', path: path.user_detail.account.change_password },
        { title: 'Cài Đặt Thông Báo', path: path.user_detail.account.settings_notify },
        { title: 'Những Thiết Lập Riêng Tư', path: path.user_detail.account.privacy }
      ]
    },
    {
      titleKey: 'side_nav.history_purchase',
      icon: 'https://cf.shopee.vn/file/f0049e9df4e536bc3e7f140d071e9078',
      path: path.historyPurchase
    },
    {
      titleKey: 'side_nav.voucher',
      icon: 'https://down-vn.img.susercontent.com/file/84feaa363ce325071c0a66d3c9a88748',
      path: path.user_detail.voucher
    },
    {
      titleKey: 'side_nav.shopee_coin',
      icon: 'https://down-vn.img.susercontent.com/file/a0ef4bd8e16e481b4253bd0eb563f784',
      path: path.user_detail.shopee_xu
    },
    {
      titleKey: 'side_nav.sale',
      icon: 'https://down-vn.img.susercontent.com/file/sg-11134004-7rd5o-m7ul39h98ol498',
      path: path.saleByMonth['1']
    }
  ]

  const toggleExpand = (index: number) => {
    setExpandedMenu(expandedMenu === index ? null : index)
  }

  return (
    <div>
      <div className='flex items-center border-b border-b-gray-200 py-4'>
        <Link to={path.profile} className='h-12 w-12 flex-shrink-0 overflow-hidden rounded-full border border-black/10'>
          <img src={getAvatarUrl(profile?.avatar)} alt='' className='h-full w-full object-cover' />
        </Link>
        <div className='flex-grow pl-4'>
          <div className='mb-1 truncate font-semibold text-black'>{profile?.email || 'Anonymous User'}</div>
          <Link to={path.profile} className='flex items-center capitalize text-gray-500'>
            <svg
              width={12}
              height={12}
              viewBox='0 0 12 12'
              xmlns='http://www.w3.org/2000/svg'
              style={{ marginRight: 4 }}
            >
              <path
                d='M8.54 0L6.987 1.56l3.46 3.48L12 3.48M0 8.52l.073 3.428L3.46 12l6.21-6.18-3.46-3.48'
                fill='#9B9B9B'
                fillRule='evenodd'
              />
            </svg>
            {t('side_nav.edit_profile')}
          </Link>
        </div>
      </div>
      <div className='mt-7'>
        {menuItems.map((item, index) => (
          <div key={index}>
            <div
              onClick={() => (item.subItems ? toggleExpand(index) : null)}
              className='flex items-center cursor-pointer capitalize transition-colors text-gray-600 hover:text-orange mb-3'
            >
              <div className='mr-3 h-[22px] w-[22px]'>
                <img src={item.icon} alt='' className='h-full w-full' />
              </div>
              {item.subItems ? (
                <>
                  <span className="text-black">{t(item.titleKey)}</span>
                  {/*<span className='ml-auto'>{expandedMenu === index ? '▲' : '▼'}</span>*/}
                </>
              ) : (
                <NavLink to={item.path || ''} className='flex-grow text-black'>
                  {t(item.titleKey)}
                </NavLink>
              )}
            </div>
            {item.subItems && expandedMenu === index && (
              <div className='ml-9 mt-3 mb-3'>
                {item.subItems.map((subItem, subIndex) => (
                  <NavLink key={subIndex} to={subItem.path} className='block text-gray-500 hover:text-orange mt-2'>
                    {subItem.title}
                  </NavLink>
                ))}
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  )
}
