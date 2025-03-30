import { ApiResponse } from './api.type'
import { BaseEntity } from './base.type'

export type HeadquarterEntity = {
  id: number
  region: number
  domain_url: string
} & BaseEntity

export type HeadquarterRes = ApiResponse<Omit<HeadquarterEntity, 'created_at' | 'updated_at'>[]>
