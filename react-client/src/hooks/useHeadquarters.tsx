import { useQuery } from '@tanstack/react-query'
import axios from 'axios'
import { API_URL } from 'src/env/env.config'
import { HeadquarterRes } from 'src/types/headquarter.type'

const mockUrl = '/api/headquarters'
const url = `${API_URL}/headquarters`

const fetchHeadquarters_REST = async () => {
  try {
    const res = await axios.get<HeadquarterRes>(mockUrl)
    return res.data.data
  } catch (err) {
    console.error('Error fetching headquarters:', err)
    throw err // Add this line to properly propagate the error
  }
}

const useHeadquarters = () => {
  return useQuery({
    queryKey: ['headQuarters'],
    queryFn: fetchHeadquarters_REST,
    retry: 3, // thử lại tối đa 3 lần nếu lỗi
    retryDelay: (attemptIndex) => Math.min(1000 * 2 ** attemptIndex, 3000) // exponential backoff
  })
}

export default useHeadquarters
