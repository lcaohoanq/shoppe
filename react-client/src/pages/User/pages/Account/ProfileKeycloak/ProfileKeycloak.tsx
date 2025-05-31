// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-nocheck
import { yupResolver } from '@hookform/resolvers/yup'
import { Fragment, useContext, useEffect, useMemo, useState } from 'react'
import { useForm, Controller, FormProvider, useFormContext } from 'react-hook-form'
import { toast } from 'react-toastify'
import Button from 'src/components/Button'
import Input from 'src/components/Input'
import InputFile from 'src/components/InputFile'
import InputNumber from 'src/components/InputNumber'
import { AppContext } from 'src/contexts/app.context'
import { userSchema, UserSchema } from 'src/utils/rules'
import { getAvatarUrl } from 'src/utils/utils'
import DateSelect from '../../../components/DateSelect'
import { Gender } from '../../../../../types/user.type'
import keycloak from 'src/core/keycloak'

function Info() {
  const {
    register,
    control,
    formState: { errors }
  } = useFormContext<FormData>()

  return (
    <Fragment>
      <div className='mt-6 flex flex-col flex-wrap sm:flex-row'>
        <div className='truncate pt-3 capitalize sm:w-[20%] sm:text-right'>Tên</div>
        <div className='sm:w-[80%] sm:pl-5'>
          <Input
            classNameInput='w-full rounded-sm border border-gray-300 px-3 py-2 outline-none focus:border-gray-500 focus:shadow-sm bg-gray-100'
            register={register}
            name='name'
            placeholder='Tên'
            errorMessage={errors.name?.message}
            disabled // Keycloak managed field - cannot be edited here
          />
          <div className='mt-1 text-xs text-gray-500'>
            Để thay đổi tên, vui lòng cập nhật trong hệ thống quản lý tài khoản
          </div>
        </div>
      </div>
      <div className='mt-2 flex flex-col flex-wrap sm:flex-row'>
        <div className='truncate pt-3 capitalize sm:w-[20%] sm:text-right'>Số điện thoại</div>
        <div className='sm:w-[80%] sm:pl-5'>
          <Controller
            control={control}
            name='phone'
            render={({ field }) => (
              <InputNumber
                classNameInput='w-full rounded-sm border border-gray-300 px-3 py-2 outline-none focus:border-gray-500 focus:shadow-sm'
                placeholder='Số điện thoại'
                errorMessage={errors.phone?.message}
                {...field}
              />
            )}
          />
        </div>
      </div>
    </Fragment>
  )
}

type FormData = {
  name: string
  username: string
  email: string
  phone: string
  date_of_birth: Date
  avatar: string
  gender: string
}

// Schema for local data only (non-Keycloak managed fields)
const profileSchema = userSchema.pick(['phone', 'date_of_birth', 'avatar', 'gender'])

export default function ProfileKeycloak() {
  const { keycloakAuth } = useContext(AppContext)
  const [file, setFile] = useState<File>()
  const [localProfileData, setLocalProfileData] = useState({
    phone: '',
    avatar: '',
    gender: '',
    date_of_birth: new Date(1990, 0, 1)
  })

  const previewImage = useMemo(() => {
    return file ? URL.createObjectURL(file) : ''
  }, [file])

  const methods = useForm<FormData>({
    defaultValues: {
      username: '',
      name: '',
      email: '',
      phone: '',
      avatar: '',
      gender: '',
      date_of_birth: new Date(1990, 0, 1)
    },
    resolver: yupResolver<FormData>(profileSchema)
  })

  const {
    register,
    control,
    formState: { errors },
    handleSubmit,
    setValue,
    watch,
    setError
  } = methods

  const avatar = watch('avatar')

  // Load Keycloak user data and local profile data
  useEffect(() => {
    if (keycloakAuth?.userInfo) {
      console.log(`Keycloak user info: ${JSON.stringify(keycloakAuth.userInfo, null, 2)}`)

      // Set Keycloak managed fields (read-only)
      setValue('username', keycloakAuth.userInfo.username)
      setValue('name', keycloakAuth.userInfo.fullName)
      setValue('email', keycloakAuth.userInfo.email)
      setValue('phone', keycloakAuth.userInfo.phone || '')

      // Load local profile data from localStorage or your preferred storage
      const savedLocalData = localStorage.getItem(`profile_${keycloakAuth.userInfo.id}`)
      if (savedLocalData) {
        const parsedData = JSON.parse(savedLocalData)
        setLocalProfileData(parsedData)
        // setValue('phone', parsedData.phone || '')
        setValue('avatar', parsedData.avatar || '')
        setValue('gender', parsedData.gender || '')
        setValue('date_of_birth', parsedData.date_of_birth ? new Date(parsedData.date_of_birth) : new Date(1990, 0, 1))
      }
    }
  }, [keycloakAuth, setValue])

  const onSubmit = handleSubmit(async (data) => {
    try {
      if (!keycloakAuth?.userInfo?.id) {
        toast.error('Không thể xác định người dùng')
        return
      }

      let avatarName = avatar

      // Handle avatar upload if a new file is selected
      if (file) {
        // Here you would typically upload to your file storage service
        // For now, we'll simulate this
        const form = new FormData()
        form.append('image', file)

        // Replace this with your actual upload logic
        // const uploadRes = await fetch('/api/upload-avatar', {
        //   method: 'POST',
        //   body: form,
        //   headers: {
        //     'Authorization': `Bearer ${keycloakAuth.token}`
        //   }
        // })
        // const result = await uploadRes.json()
        // avatarName = result.data

        // For demo purposes, we'll use the object URL
        avatarName = URL.createObjectURL(file)
        setValue('avatar', avatarName)
      }

      // Save local profile data
      const localData = {
        phone: data.phone,
        avatar: avatarName,
        gender: data.gender,
        date_of_birth: data.date_of_birth?.toISOString(),
        updatedAt: new Date().toISOString()
      }

      // Store in localStorage (you might want to use a more robust storage solution)
      localStorage.setItem(`profile_${keycloakAuth.userInfo.id}`, JSON.stringify(localData))
      setLocalProfileData(localData)

      toast.success('Cập nhật hồ sơ thành công!')
    } catch (error) {
      console.error('Error updating profile:', error)
      toast.error('Có lỗi xảy ra khi cập nhật hồ sơ')
    }
  })

  const handleChangeFile = (file?: File) => {
    setFile(file)
  }

  const handleAccountManagement = () => {
    // Redirect to Keycloak account management
    keycloak.accountManagement()
  }

  if (!keycloakAuth?.isAuthenticated) {
    return (
      <div className='rounded-sm bg-white px-2 pb-10 shadow md:px-7 md:pb-20'>
        <div className='text-center py-10'>
          <p>Vui lòng đăng nhập để xem hồ sơ</p>
        </div>
      </div>
    )
  }

  return (
    <div className='rounded-sm bg-white px-2 pb-10 shadow md:px-7 md:pb-20'>
      <div className='border-b border-b-gray-200 py-6'>
        <h1 className='text-lg font-medium capitalize text-gray-900'>Hồ Sơ Của Tôi</h1>
        <div className='mt-1 text-sm text-gray-700'>Quản lý thông tin hồ sơ để bảo mật tài khoản</div>
        <div className='mt-2'>
          <Button
            onClick={handleAccountManagement}
            className='text-sm text-orange hover:text-orange/80 underline'
            type='button'
          >
            Quản lý tài khoản Keycloak
          </Button>
        </div>
      </div>

      <FormProvider {...methods}>
        <form className='mt-8 flex flex-col-reverse md:flex-row md:items-start' onSubmit={onSubmit}>
          <div className='mt-6 flex-grow md:mt-0 md:pr-12'>
            <div className='flex flex-col flex-wrap sm:flex-row'>
              <div className='truncate pt-3 capitalize sm:w-[20%] sm:text-right'>Tên đăng nhập</div>
              <div className='sm:w-[80%] sm:pl-5'>
                <div className='pt-3 text-gray-700'>{keycloakAuth.userInfo.username}</div>
              </div>
            </div>

            <div className='flex flex-col flex-wrap sm:flex-row'>
              <div className='truncate pt-3 capitalize sm:w-[20%] sm:text-right'>Email</div>
              <div className='sm:w-[80%] sm:pl-5'>
                <div className='pt-3 text-gray-700 flex items-center gap-2'>
                  {keycloakAuth.userInfo.email}
                  {keycloakAuth.userInfo.emailVerified && (
                    <span className='text-green-600 text-xs bg-green-100 px-2 py-1 rounded'>Đã xác minh</span>
                  )}
                </div>
              </div>
            </div>

            <Info />

            <div className='mt-2 flex flex-col flex-wrap sm:flex-row mb-6'>
              <div className='truncate capitalize sm:w-[20%] sm:text-right'>Giới tính</div>
              <div className='sm:w-[80%] sm:pl-5'>
                <Controller
                  control={control}
                  name='gender'
                  render={({ field }) => (
                    <div className='flex gap-8'>
                      <div className='flex items-center'>
                        <input
                          id='gender-male'
                          type='radio'
                          className='h-4 w-4 accent-orange'
                          checked={field.value === Gender.MALE}
                          onChange={() => field.onChange(Gender.MALE)}
                        />
                        <label htmlFor='gender-male' className='ml-2 cursor-pointer'>
                          Nam
                        </label>
                      </div>
                      <div className='flex items-center'>
                        <input
                          id='gender-female'
                          type='radio'
                          className='h-4 w-4 accent-orange'
                          checked={field.value === Gender.FEMALE}
                          onChange={() => field.onChange(Gender.FEMALE)}
                        />
                        <label htmlFor='gender-female' className='ml-2 cursor-pointer'>
                          Nữ
                        </label>
                      </div>
                      <div className='flex items-center'>
                        <input
                          id='gender-others'
                          type='radio'
                          className='h-4 w-4 accent-orange'
                          checked={field.value === Gender.OTHERS}
                          onChange={() => field.onChange(Gender.OTHERS)}
                        />
                        <label htmlFor='gender-others' className='ml-2 cursor-pointer'>
                          Khác
                        </label>
                      </div>
                    </div>
                  )}
                />
              </div>
            </div>

            <Controller
              control={control}
              name='date_of_birth'
              render={({ field }) => (
                <DateSelect
                  errorMessage={errors.date_of_birth?.message}
                  value={field.value}
                  onChange={field.onChange}
                />
              )}
            />

            <div className='mt-2 flex flex-col flex-wrap sm:flex-row'>
              <div className='truncate pt-3 capitalize sm:w-[20%] sm:text-right' />
              <div className='sm:w-[80%] sm:pl-5'>
                <Button
                  className='flex h-9 items-center rounded-sm bg-orange px-5 text-center text-sm text-white hover:bg-orange/80'
                  type='submit'
                >
                  Lưu
                </Button>
              </div>
            </div>
          </div>

          <div className='flex justify-center md:w-72 md:border-l md:border-l-gray-200'>
            <div className='flex flex-col items-center'>
              <div className='my-5 h-24 w-24'>
                <img
                  src={previewImage || getAvatarUrl(avatar) || '/default-avatar.png'}
                  alt='Avatar'
                  className='h-full w-full rounded-full object-cover'
                />
              </div>
              <InputFile onChange={handleChangeFile} />
              <div className='mt-3 text-gray-400'>
                <div>Dung lượng file tối đa 1 MB</div>
                <div>Định dạng: .JPEG, .PNG</div>
              </div>
            </div>
          </div>
        </form>
      </FormProvider>
    </div>
  )
}
