export interface SuccessResponse<Data> {
  message: string
  data: Data
  status_code: number
  is_success: boolean
}
export interface ErrorResponse<Data> {
  message: string
  data?: Data
  reason?: string
  status_code?: number
  is_success?: boolean
}

// cú pháp `-?` sẽ loại bỏ undefiend của key optional

export type NoUndefinedField<T> = {
  [P in keyof T]-?: NoUndefinedField<NonNullable<T[P]>>
}
