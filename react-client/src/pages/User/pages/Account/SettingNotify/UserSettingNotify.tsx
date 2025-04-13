import React, { useState, useEffect } from 'react'
import { Switch } from '@headlessui/react'

function classNames(...classes: string[]) {
  return classes.filter(Boolean).join(' ')
}

const UserSettingNotify = () => {
  // Master toggles for sections
  const [emailMasterEnabled, setEmailMasterEnabled] = useState(true)
  const [smsMasterEnabled, setSmsMasterEnabled] = useState(true)
  const [zaloMasterEnabled, setZaloMasterEnabled] = useState(true)

  // Individual notification states
  const [emailOrder, setEmailOrder] = useState(true)
  const [emailPromo, setEmailPromo] = useState(true)
  const [emailSurvey, setEmailSurvey] = useState(true)

  const [smsNotify, setSmsNotify] = useState(true)
  const [smsPromo, setSmsPromo] = useState(false)

  const [zaloNotify, setZaloNotify] = useState(true)
  const [zaloPromo, setZaloPromo] = useState(true)

  // Update individual settings when master toggle changes
  useEffect(() => {
    if (!emailMasterEnabled) {
      setEmailOrder(false)
      setEmailPromo(false)
      setEmailSurvey(false)
    }
  }, [emailMasterEnabled])

  useEffect(() => {
    if (!smsMasterEnabled) {
      setSmsNotify(false)
      setSmsPromo(false)
    }
  }, [smsMasterEnabled])

  useEffect(() => {
    if (!zaloMasterEnabled) {
      setZaloNotify(false)
      setZaloPromo(false)
    }
  }, [zaloMasterEnabled])

  const renderItem = (title: string, desc: string, enabled: boolean, setEnabled: (v: boolean) => void, masterEnabled: boolean) => (
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

  const renderSectionHeader = (title: string, description: string, enabled: boolean, setEnabled: (v: boolean) => void) => (
    <div className="flex justify-between items-start mb-4">
      <div>
        <div className="text-xl text-black mb-1">{title}</div>
        <div className="text-sm text-gray-500">
          {description}
        </div>
      </div>
      <Switch
        checked={enabled}
        onChange={setEnabled}
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
          emailMasterEnabled,
          setEmailMasterEnabled
        )}
        <div className="ml-5 space-y-2">
          {renderItem('Cập nhật đơn hàng', 'Cập nhật về tình trạng vận chuyển của tất cả các đơn hàng', emailOrder, setEmailOrder, emailMasterEnabled)}
          {renderItem('Khuyến mãi', 'Cập nhật về các ưu đãi và khuyến mãi sắp tới', emailPromo, setEmailPromo, emailMasterEnabled)}
          {renderItem('Khảo sát', 'Đồng ý nhận khảo sát để cho chúng tôi được lắng nghe bạn', emailSurvey, setEmailSurvey, emailMasterEnabled)}
        </div>
      </div>

      {/* SMS */}
      <div>
        {renderSectionHeader(
          'Thông báo SMS',
          'Thông báo và nhắc nhở quan trọng về tài khoản sẽ không thể bị tắt',
          smsMasterEnabled,
          setSmsMasterEnabled
        )}
        <div className="ml-5 space-y-2">
          {renderItem('Khuyến mãi', 'Cập nhật về các ưu đãi và khuyến mãi sắp tới', smsPromo, setSmsPromo, smsMasterEnabled)}
        </div>
      </div>

      {/* ZALO */}
      <div>
        {renderSectionHeader(
          'Thông báo Zalo',
          'Thông báo và nhắc nhở quan trọng về tài khoản sẽ không thể bị tắt',
          zaloMasterEnabled,
          setZaloMasterEnabled
        )}
        <div className="ml-5 space-y-2">
          {renderItem('Khuyến mãi (Shopee Việt Nam)', 'Cập nhật về các ưu đãi và khuyến mãi sắp tới', zaloPromo, setZaloPromo, zaloMasterEnabled)}
        </div>
      </div>
    </div>
  )
}

export default UserSettingNotify