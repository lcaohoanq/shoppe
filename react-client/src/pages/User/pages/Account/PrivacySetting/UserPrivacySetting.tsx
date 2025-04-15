import React, { useContext } from 'react'
import { AppContext } from '../../../../../contexts/app.context'
import { userServiceClient } from '../../../../../apis/user.api'

const UserPrivacySetting = () => {
  const { profile } = useContext(AppContext)

  const handleDisableAccount = async () => {
    try {
      const res = await userServiceClient.delete(`/user/disable-account/${profile?.id || '1'}`)
      if (res.status === 200) {
        alert('Your account has been disabled.')
      }
    } catch (e) {
      console.log(`Error: ${e}`)
    }
  }

  return (
    <div className='max-w-5xl mx-auto p-4 bg-white rounded-sm shadow-md min-h-[600px] flex flex-col justify-between'>
      <div className='flex flex-col flex-1 p-2'>
        <div className='flex justify-between items-center mb-4'>
          <div className='text-xl text-black'>Request Disable Account</div>
        </div>
        <div className='w-full mt-2 border-t-2 py-2 border-gray-100'></div>
        <button onClick={handleDisableAccount} className='w-1/5 bg-orange p-3 text-white'>
          Disable Account
        </button>
      </div>
    </div>
  )
}

export default UserPrivacySetting
