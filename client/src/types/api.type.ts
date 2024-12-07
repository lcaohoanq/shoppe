export type ApiResponse<Data> = {
  message: string
  data: Data
  status_code: number
  is_success: boolean
  reason: string
}
