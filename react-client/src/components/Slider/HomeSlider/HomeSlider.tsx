import { Autoplay, Navigation, Pagination } from 'swiper/modules'
import { Swiper, SwiperSlide } from 'swiper/react'
import 'swiper/css'
import 'swiper/css/navigation'
import 'swiper/css/pagination'
import React, { memo } from 'react'
import './Swiper.css'

const HomeSliderInner: React.FC = () => {
  return (
    <div style={{ position: 'relative', width: '70%', height: 'auto', marginTop: '2rem' }}>
      <Swiper
        modules={[Navigation, Pagination, Autoplay]}
        spaceBetween={0}
        slidesPerView={1}
        navigation
        pagination={{ clickable: true }}
        centeredSlides={true}
        autoplay={{ delay: 10000 }}
        loop={true}
        className='custom-swiper'
      >
        {[
          'https://api-website.cinestar.com.vn/media/MageINIC/bannerslider/1215x365.png',
          'https://api-website.cinestar.com.vn/media/MageINIC/bannerslider/1215wx365h_4_.jpg',
          'https://api-website.cinestar.com.vn/media/MageINIC/bannerslider/2400wx720h.jpg',
          'https://api-website.cinestar.com.vn/media/MageINIC/bannerslider/anh-khong-dau-banner.jpg',
          'https://cdn.galaxycine.vn/media/2025/2/24/interstellar-3_1740390337969.jpg',
          'https://cdn.galaxycine.vn/media/2025/2/28/2048x682-1_1740711284334.jpg',
          'https://api-website.cinestar.com.vn/media/MageINIC/bannerslider/1215wx365h_1_.jpg'
        ].map((slide, index) => (
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
