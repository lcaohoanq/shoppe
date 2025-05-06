import { Autoplay, Navigation, Pagination } from 'swiper/modules'
import { Swiper, SwiperSlide } from 'swiper/react'
import 'swiper/css'
import 'swiper/css/navigation'
import 'swiper/css/pagination'
import React, { memo } from 'react'
import { homeAssets } from 'src/assets/home'

const HomeSliderInner: React.FC = () => {
  return (
    <div style={{ position: 'relative', width: '70%', height: 'auto' }}>
      <Swiper
        modules={[Navigation, Pagination, Autoplay]}
        spaceBetween={0}
        slidesPerView={1}
        navigation
        pagination={{ clickable: true }}
        centeredSlides={true}
        autoplay={{ delay: 10000 }}
        loop={true}
        // className='custom-swiper'
      >
        {homeAssets.top.bannerSlider.map((slide, index) => (
          <SwiperSlide key={index}>
            <img
              src={slide}
              alt={`Slide ${index + 1}`}
              style={{
                width: '100%',
                maxHeight: '400px',
                objectFit: 'cover',
                borderRadius: '3px'
              }}
            />
          </SwiperSlide>
        ))}
      </Swiper>
    </div>
  )
}

const HomeSlider = memo(HomeSliderInner)

export default HomeSlider
