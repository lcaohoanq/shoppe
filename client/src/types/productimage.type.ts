export type ProductImageReponse = {
  id: number
  product_id: number
  media_meta: MediaMeta
}

type MediaMeta = {
  file_name: string
  file_type: string
  file_size: number
  image_url: string
  video_url: string
  created_at: string
  updated_at: string
}
