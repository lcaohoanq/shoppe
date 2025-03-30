import { http, HttpResponse } from 'msw'
import { HeadquarterRes } from 'src/types/headquarter.type'

const headquarterRes: HeadquarterRes = {
  message: 'Get all headquarters successfully',
  data: [
    {
      id: 1,
      region: 185,
      domain_url: 'https://shopee.sg'
    },
    {
      id: 2,
      region: 93,
      domain_url: 'https://shopee.co.id'
    },
    {
      id: 3,
      region: 203,
      domain_url: 'https://shopee.co.th'
    },
    {
      id: 4,
      region: 122,
      domain_url: 'https://shopee.co.my'
    },
    {
      id: 5,
      region: 222,
      domain_url: 'https://shopee.vn'
    },
    {
      id: 6,
      region: 164,
      domain_url: 'https://shopee.ph'
    },
    {
      id: 7,
      region: 27,
      domain_url: 'https://shopee.br'
    },
    {
      id: 8,
      region: 131,
      domain_url: 'https://shopee.mx'
    },
    {
      id: 9,
      region: 43,
      domain_url: 'https://shopee.com.co'
    },
    {
      id: 10,
      region: 40,
      domain_url: 'https://shopee.cl'
    },
    {
      id: 11,
      region: 200,
      domain_url: 'https://shopee.tw'
    }
  ],
  status_code: 200,
  is_success: true
}

export const getAllHeadquarter = () => {
  return http.get('/api/headquarters', () => {
    return HttpResponse.json(headquarterRes)
  })
}
