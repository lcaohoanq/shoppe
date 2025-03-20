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
    queryFn: fetchHeadquarters_REST
  })
}

export default useHeadquarters
