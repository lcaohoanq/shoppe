export type ApiResponse<Data> = {
  message: string
  data: Data
  status_code?: number
  is_success?: boolean
  pagination?: PaginationMeta
  reason?: string
}

export type PaginationMeta = {
  total_pages: number
  total_items: number
  current_page: number
  page_size: number
}
