import { useEffect } from 'react'
import { useNavigate, useSearchParams } from 'react-router-dom'
import { useContext } from 'react'
import { AppContext } from 'src/contexts/app.context'

export default function OAuth2Callback() {
  const [searchParams] = useSearchParams()
  const { setIsAuthenticated, setProfile } = useContext(AppContext)
  const navigate = useNavigate()

  useEffect(() => {
    const token = searchParams.get('token')
    const role = searchParams.get('role')
    const name = searchParams.get('username') ?? '' // username trong URL -> name trong profile
    const avatar = searchParams.get('avatar')
    const idParam = searchParams.get('id')

    const id = idParam ? Number.parseInt(idParam, 10) : null

    if (token && id !== null && !Number.isNaN(id)) {
      localStorage.setItem('access_token', token)
      setIsAuthenticated(true)

      // Set full profile
      setProfile({
        id,
        email: '', // chưa có trong URL, bạn cần bổ sung nếu muốn lấy
        name: name,
        gender: '', // default
        is_active: true, // mặc định true sau login
        status: 'active', // hoặc default khác tùy hệ thống bạn
        date_of_birth: '', // chưa có, default rỗng
        phone_number: '',
        address: '',
        avatar: avatar ?? '',
        role_name: role ?? '',
        preferred_language: 'en', // hoặc dùng i18n tự động
        preferred_currency: 'USD',
        created_at: new Date().toISOString(), // hoặc để trống nếu chưa xác định
        updated_at: new Date().toISOString()
      })

      navigate('/')
    } else {
      navigate('/login?error=auth_failed')
    }
  }, [searchParams, setIsAuthenticated, setProfile, navigate])

  return (
    <div className='flex justify-center items-center h-screen'>
      <div className='text-center'>
        <h2 className='text-xl'>Processing Authentication...</h2>
        <p>Please wait while we log you in.</p>
      </div>
    </div>
  )
}
