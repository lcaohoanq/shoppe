import React, { useState, useEffect } from 'react'
import { Switch } from '@headlessui/react'

function classNames(...classes: string[]) {
  return classes.filter(Boolean).join(' ')
}

const UserSettingNotify = () => {
  // Email group
  const [emailSettings, setEmailSettings] = useState({
    masterEnabled: true,
    order: true,
    promo: true,
    survey: true,
  })

  // SMS group
  const [smsSettings, setSmsSettings] = useState({
    masterEnabled: false,
    promo: false,
  })

  // Zalo group
  const [zaloSettings, setZaloSettings] = useState({
    masterEnabled: false,
    promo: true,
  })

  useEffect(() => {
    if (!emailSettings.masterEnabled) {
      setEmailSettings((prev) => ({ ...prev, order: false, promo: false, survey: false }))
    }
  }, [emailSettings.masterEnabled])

  useEffect(() => {
    if (!smsSettings.masterEnabled) {
      setSmsSettings((prev) => ({ ...prev, promo: false }))
    }
  }, [smsSettings.masterEnabled])

  useEffect(() => {
    if (!zaloSettings.masterEnabled) {
      setZaloSettings((prev) => ({ ...prev, promo: false }))
    }
  }, [zaloSettings.masterEnabled])

  const renderItem = (
    title: string,
    desc: string,
    enabled: boolean,
    setEnabled: (v: boolean) => void,
    masterEnabled: boolean
  ) => (
    <div className={`flex justify-between items-start py-3 border-b border-gray-100 ${!masterEnabled ? 'opacity-50' : ''}`}>
      <div>
        <div className="text-black text-[16px] font-medium">{title}</div>
        <div className="text-sm text-gray-500">{desc}</div>
      </div>
      <Switch
        checked={masterEnabled && enabled}
        onChange={setEnabled}
        disabled={!masterEnabled}
        className={classNames(
          masterEnabled && enabled ? 'bg-green-500' : 'bg-gray-300',
          'relative inline-flex h-6 w-11 items-center rounded-full transition-colors duration-200'
        )}
      >
        <span
          className={classNames(
            masterEnabled && enabled ? 'translate-x-6' : 'translate-x-1',
            'inline-block h-4 w-4 transform rounded-full bg-white transition-transform duration-200'
          )}
        />
      </Switch>
    </div>
  )

  const renderSectionHeader = (
    title: string,
    description: string,
    enabled: boolean,
    toggle: () => void
  ) => (
    <div className="flex justify-between items-start mb-4">
      <div>
        <div className="text-xl text-black mb-1">{title}</div>
        <div className="text-sm text-gray-500">{description}</div>
      </div>
      <Switch
        checked={enabled}
        onChange={toggle}
        className={classNames(
          enabled ? 'bg-green-500' : 'bg-gray-300',
          'relative inline-flex h-6 w-11 items-center rounded-full transition-colors duration-200'
        )}
      >
        <span
          className={classNames(
            enabled ? 'translate-x-6' : 'translate-x-1',
            'inline-block h-4 w-4 transform rounded-full bg-white transition-transform duration-200'
          )}
        />
      </Switch>
    </div>
  )

  return (
    <div className="max-w-5xl mx-auto p-4 bg-white rounded-sm shadow-md min-h-[600px] flex flex-col space-y-6">
      {/* EMAIL */}
      <div>
        {renderSectionHeader(
          'Email thông báo',
          'Thông báo và nhắc nhở quan trọng về tài khoản sẽ không thể bị tắt',
          emailSettings.masterEnabled,
          () => setEmailSettings((prev) => ({ ...prev, masterEnabled: !prev.masterEnabled }))
        )}
        <div className="ml-5 space-y-2">
          {renderItem('Cập nhật đơn hàng', 'Cập nhật về tình trạng vận chuyển của tất cả các đơn hàng', emailSettings.order, (v) => setEmailSettings((prev) => ({ ...prev, order: v })), emailSettings.masterEnabled)}
          {renderItem('Khuyến mãi', 'Cập nhật về các ưu đãi và khuyến mãi sắp tới', emailSettings.promo, (v) => setEmailSettings((prev) => ({ ...prev, promo: v })), emailSettings.masterEnabled)}
          {renderItem('Khảo sát', 'Đồng ý nhận khảo sát để cho chúng tôi được lắng nghe bạn', emailSettings.survey, (v) => setEmailSettings((prev) => ({ ...prev, survey: v })), emailSettings.masterEnabled)}
        </div>
      </div>

      {/* SMS */}
      <div>
        {renderSectionHeader(
          'Thông báo SMS',
          'Thông báo và nhắc nhở quan trọng về tài khoản sẽ không thể bị tắt',
          smsSettings.masterEnabled,
          () => setSmsSettings((prev) => ({ ...prev, masterEnabled: !prev.masterEnabled }))
        )}
        <div className="ml-5 space-y-2">
          {renderItem('Khuyến mãi', 'Cập nhật về các ưu đãi và khuyến mãi sắp tới', smsSettings.promo, (v) => setSmsSettings((prev) => ({ ...prev, promo: v })), smsSettings.masterEnabled)}
        </div>
      </div>

      {/* ZALO */}
      <div>
        {renderSectionHeader(
          'Thông báo Zalo',
          'Thông báo và nhắc nhở quan trọng về tài khoản sẽ không thể bị tắt',
          zaloSettings.masterEnabled,
          () => setZaloSettings((prev) => ({ ...prev, masterEnabled: !prev.masterEnabled }))
        )}
        <div className="ml-5 space-y-2">
          {renderItem('Khuyến mãi (Shopee Việt Nam)', 'Cập nhật về các ưu đãi và khuyến mãi sắp tới', zaloSettings.promo, (v) => setZaloSettings((prev) => ({ ...prev, promo: v })), zaloSettings.masterEnabled)}
        </div>
      </div>
    </div>
  )
}

export default UserSettingNotify
