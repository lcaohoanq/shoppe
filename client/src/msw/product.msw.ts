// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-nocheck
import { http, HttpResponse } from 'msw'
import config from 'src/constants/config'
import HttpStatusCode from 'src/constants/httpStatusCode.enum'
import { API_URL } from 'src/env/env.config'
import { ApiResponse } from 'src/types/api.type'
import { ProductResponse } from 'src/types/product.type'

const productsRes = {
  message: 'Lấy các sản phẩm thành công',
  data: {
    products: [
      {
        _id: '60ad02422fb52902585972a9',
        images: [
          'https://api-ecom.duthanhduoc.com/images/08b79b1d-169d-4de1-85a2-4e5e8ff535b7.jpg',
          'https://api-ecom.duthanhduoc.com/images/182d6e25-65fa-4abe-b822-70d87550bf4e.jpg',
          'https://api-ecom.duthanhduoc.com/images/827e675d-e553-497e-9b15-d2df5fc7192d.jpg',
          'https://api-ecom.duthanhduoc.com/images/b6425e3f-3cc3-4696-94f7-5053afca2c71.jpg',
          'https://api-ecom.duthanhduoc.com/images/4d80b312-e605-4508-ab80-14dd75f6d23d.jpg',
          'https://api-ecom.duthanhduoc.com/images/9e628716-0b94-44d8-850c-e96adc4b1c8f.jpg',
          'https://api-ecom.duthanhduoc.com/images/20a1a8e5-1b49-4854-a221-0f96130b5fd8.jpg'
        ],
        price: 279000,
        rating: 5,
        price_before_discount: 315000,
        quantity: 1959,
        sold: 36,
        view: 44,
        name: '[Mã FAMALLT5 giảm 15% đơn 150K] Áo thun tay lỡ GENZ phông Unisex nam nữ Cotton oversize form rộng Racing Genz GZT021',
        category: {
          _id: '60aba4e24efcc70f8892e1c6',
          name: 'Áo thun',
          __v: 0
        },
        image: 'https://api-ecom.duthanhduoc.com/images/08b79b1d-169d-4de1-85a2-4e5e8ff535b7.jpg',
        createdAt: '2021-05-25T13:57:22.760Z',
        updatedAt: '2022-11-30T10:19:23.942Z'
      },
      {
        _id: '60ad01102fb52902585972a7',
        images: [
          'https://api-ecom.duthanhduoc.com/images/05f090b8-736e-4100-b4f4-7a09f48e718a.jpg',
          'https://api-ecom.duthanhduoc.com/images/594a2d08-04f4-4902-873f-e9ba865bc497.jpg',
          'https://api-ecom.duthanhduoc.com/images/0f906616-bc7f-4661-9bbe-5570b5d902b7.jpg',
          'https://api-ecom.duthanhduoc.com/images/e129d0b1-8a45-463a-a668-d619461ae984.jpg',
          'https://api-ecom.duthanhduoc.com/images/780a9d1a-74e9-4d8c-bbae-ed3d5eca8b97.jpg',
          'https://api-ecom.duthanhduoc.com/images/986f2f5b-23cc-498f-9adb-9d07e6923eb3.jpg',
          'https://api-ecom.duthanhduoc.com/images/e383805f-e875-4398-800d-ae07f2d2e8ce.jpg',
          'https://api-ecom.duthanhduoc.com/images/c3df0eba-05f8-4ba8-9ef4-f67a28fa4b81.jpg',
          'https://api-ecom.duthanhduoc.com/images/7cefd5f0-6ded-443d-821b-4909592e6490.jpg'
        ],
        price: 53000,
        rating: 5,
        price_before_discount: 106000,
        quantity: 16746,
        sold: 2255,
        view: 97,
        name: 'Áo thun nam nữ tay lỡ YINXX, áo phông nam nữ form rộng A304',
        category: {
          _id: '60aba4e24efcc70f8892e1c6',
          name: 'Áo thun',
          __v: 0
        },
        image: 'https://api-ecom.duthanhduoc.com/images/b1c008a6-bb10-46a6-8caf-2b0e9ca4e175.jpg',
        createdAt: '2021-05-25T13:52:16.271Z',
        updatedAt: '2022-12-14T17:57:13.575Z'
      },
      {
        _id: '60ad004c2fb52902585972a6',
        images: [
          'https://api-ecom.duthanhduoc.com/images/b1c008a6-bb10-46a6-8caf-2b0e9ca4e175.jpg',
          'https://api-ecom.duthanhduoc.com/images/2df345c5-4381-4863-9510-17f44572ad45.jpg',
          'https://api-ecom.duthanhduoc.com/images/00b21c79-e1a0-45f0-9152-6881e644b15b.jpg',
          'https://api-ecom.duthanhduoc.com/images/dc35e2ed-0407-4f27-9b97-bad9bc785deb.jpg',
          'https://api-ecom.duthanhduoc.com/images/f1de05b8-60c5-4940-be9f-b6cc98d34061.jpg',
          'https://api-ecom.duthanhduoc.com/images/4788c7fd-d728-4f47-8000-e858d6466384.jpg',
          'https://api-ecom.duthanhduoc.com/images/b4614934-0164-4845-bf14-d19de6c36835.jpg',
          'https://api-ecom.duthanhduoc.com/images/1ec5e192-c2fc-4411-b170-4aa2b1635ddb.jpg',
          'https://api-ecom.duthanhduoc.com/images/a751a941-7d74-4f2a-a238-c806b055ed11.jpg'
        ],
        price: 49000,
        rating: 5,
        price_before_discount: 70000,
        quantity: 6797,
        sold: 21,
        view: 65,
        name: 'Mẫu Mới Khuyến Mãi Sốc 3 Ngày ⚡ Áo Thun Tay Lỡ In Bướm Dirty Coins Ao Thun Unisex From Rộng -BONSEN STORE',
        category: {
          _id: '60aba4e24efcc70f8892e1c6',
          name: 'Áo thun',
          __v: 0
        },
        image: 'https://api-ecom.duthanhduoc.com/images/b1c008a6-bb10-46a6-8caf-2b0e9ca4e175.jpg',
        createdAt: '2021-05-25T13:49:00.060Z',
        updatedAt: '2022-12-14T12:50:34.370Z'
      },
      {
        _id: '60acfac72fb529025859729f',
        images: [
          'https://api-ecom.duthanhduoc.com/images/ad82b185-5c18-41ca-90ec-1c54c846fd49.jpg',
          'https://api-ecom.duthanhduoc.com/images/04001c71-9d8f-4e68-b9ac-cbb47406f30f.jpg',
          'https://api-ecom.duthanhduoc.com/images/3142a89e-8301-42c3-9621-1c901e4a097b.jpg',
          'https://api-ecom.duthanhduoc.com/images/849be86c-8880-44dd-a037-ffc447d336f3.jpg',
          'https://api-ecom.duthanhduoc.com/images/0856c2f1-fcc7-470d-9e99-d7b9f6f32dcc.jpg'
        ],
        price: 39000,
        rating: 5,
        price_before_discount: 60000,
        quantity: 17453,
        sold: 97,
        view: 107,
        name: '[Có video] Áo phông form rộng tay lỡ unisex - Áo thun orange soda - Sỉ áo thun số lượng lớn',
        category: {
          _id: '60aba4e24efcc70f8892e1c6',
          name: 'Áo thun',
          __v: 0
        },
        image: 'https://api-ecom.duthanhduoc.com/images/ad82b185-5c18-41ca-90ec-1c54c846fd49.jpg',
        createdAt: '2021-05-25T13:25:27.965Z',
        updatedAt: '2022-11-26T12:14:13.481Z'
      },
      {
        _id: '60acf0ec2fb5290258597299',
        images: [
          'https://api-ecom.duthanhduoc.com/images/25c409a5-9e6c-4e2d-a49e-23a8c291f749.jpg',
          'https://api-ecom.duthanhduoc.com/images/85e6928d-9e7f-4ff9-8105-8389448f6a48.jpg',
          'https://api-ecom.duthanhduoc.com/images/1705c99d-e61b-4eb3-a786-7d90e927b242.jpg',
          'https://api-ecom.duthanhduoc.com/images/6f9f953c-6048-419f-87c3-24c24620dfab.jpg',
          'https://api-ecom.duthanhduoc.com/images/49eaf710-c340-43fc-b321-43bf896d7a39.jpg',
          'https://api-ecom.duthanhduoc.com/images/400b5529-01d4-42d7-be63-e179f8c0a4df.jpg',
          'https://api-ecom.duthanhduoc.com/images/f0b9ff49-824f-4a7a-8bce-a4d6599e26a6.jpg',
          'https://api-ecom.duthanhduoc.com/images/03c61a2a-f703-486b-961d-73b38be7240f.jpg',
          'https://api-ecom.duthanhduoc.com/images/59bcd4bd-fd27-416c-b7df-dd831a3da621.jpg'
        ],
        price: 119000,
        rating: 5,
        price_before_discount: 198000,
        quantity: 234,
        sold: 24,
        view: 259,
        name: 'Áo thun nam ngắn tay thể thao chất liệu thun lạnh 4 chiều AT39',
        category: {
          _id: '60aba4e24efcc70f8892e1c6',
          name: 'Áo thun',
          __v: 0
        },
        image: 'https://api-ecom.duthanhduoc.com/images/25c409a5-9e6c-4e2d-a49e-23a8c291f749.jpg',
        createdAt: '2021-05-25T12:43:24.537Z',
        updatedAt: '2022-11-07T20:09:29.996Z'
      },
      {
        _id: '60abaa9adbfa6e153cb9962c',
        images: [
          'https://api-ecom.duthanhduoc.com/images/c9e6cdf9-5d7c-4767-ab19-5d64b133fd3b.jpg',
          'https://api-ecom.duthanhduoc.com/images/d938561d-9ad1-4f61-822a-9d29b3cb663e.jpg',
          'https://api-ecom.duthanhduoc.com/images/7c2e9bd9-0773-4f17-9568-62ab8ce3a689.jpg',
          'https://api-ecom.duthanhduoc.com/images/a70f8655-53c8-4359-96c2-ac733f11279e.jpg',
          'https://api-ecom.duthanhduoc.com/images/dd139f2c-14cf-43f8-ab58-2b4cadcd2140.jpg'
        ],
        price: 69000,
        rating: 5,
        price_before_discount: 80000,
        quantity: 8129,
        sold: 4100,
        view: 107,
        name: '[Mã FAMAYMA2 giảm 10K đơn 50K] Áo thun nam nữ form rộng Yinxx, áo phông tay lỡ ATL43',
        category: {
          _id: '60aba4e24efcc70f8892e1c6',
          name: 'Áo thun',
          __v: 0
        },
        image: 'https://api-ecom.duthanhduoc.com/images/c9e6cdf9-5d7c-4767-ab19-5d64b133fd3b.jpg',
        createdAt: '2021-05-24T13:31:06.769Z',
        updatedAt: '2022-11-27T16:00:39.393Z'
      },
      {
        _id: '60aba9a2dbfa6e153cb9962b',
        images: [
          'https://api-ecom.duthanhduoc.com/images/2b5e1bfa-b202-46b3-b68b-f6c8a19cb1cf.jpg',
          'https://api-ecom.duthanhduoc.com/images/71ff840e-80bd-4d01-b997-f409392c3901.jpg',
          'https://api-ecom.duthanhduoc.com/images/9fb54b27-bb8d-415c-bb41-72a113f33fb5.jpg',
          'https://api-ecom.duthanhduoc.com/images/2741ba1d-844f-4b6d-baa8-8ebe41a4d5af.jpg',
          'https://api-ecom.duthanhduoc.com/images/63a231e8-aa62-4b0a-a7c7-9d327181ae0a.jpg',
          'https://api-ecom.duthanhduoc.com/images/050e6792-e9bb-4093-b198-3864eddffa3f.jpg',
          'https://api-ecom.duthanhduoc.com/images/92a099c0-b9b7-4d37-b234-ee319868bfe4.jpg',
          'https://api-ecom.duthanhduoc.com/images/23d8c0fb-8d30-4438-a973-3908a89db968.jpg',
          'https://api-ecom.duthanhduoc.com/images/4553c8a6-dfb8-4323-97b9-bf17cac384a4.jpg'
        ],
        price: 69000,
        rating: 5,
        price_before_discount: 100000,
        quantity: 23565,
        sold: 3800,
        view: 289,
        name: '[Mã FAMAYMA2 giảm 10K đơn 50K] Áo thun tay lỡ unisex Yinxx, áo phông form rộng ATL187',
        category: {
          _id: '60aba4e24efcc70f8892e1c6',
          name: 'Áo thun',
          __v: 0
        },
        image: 'https://api-ecom.duthanhduoc.com/images/2b5e1bfa-b202-46b3-b68b-f6c8a19cb1cf.jpg',
        createdAt: '2021-05-24T13:26:58.072Z',
        updatedAt: '2022-12-03T22:44:09.909Z'
      }
    ],
    pagination: {
      page: 1,
      limit: 30,
      page_size: 1
    }
  }
}
const productDetailRes = {
  message: 'Lấy sản phẩm thành công',
  data: {
    _id: '60afb2426ef5b902180aacb9',
    images: [
      'https://api-ecom.duthanhduoc.com/images/aa374023-7a5b-46ea-aca3-dad1b29fb015.jpg',
      'https://api-ecom.duthanhduoc.com/images/b997dac2-2674-4e20-b5ee-459566b077e7.jpg',
      'https://api-ecom.duthanhduoc.com/images/ac328d77-6014-4a2d-8626-924ac35876df.jpg',
      'https://api-ecom.duthanhduoc.com/images/5061fefa-bded-4fb0-80e5-3623656a4816.jpg',
      'https://api-ecom.duthanhduoc.com/images/02c08a86-4d9b-437b-ae02-f1d49cf2933b.jpg',
      'https://api-ecom.duthanhduoc.com/images/12c405e3-b24f-46ef-8969-54050e1022e9.jpg',
      'https://api-ecom.duthanhduoc.com/images/d448057c-3d3d-45d2-a9bc-e984bc80555f.jpg'
    ],
    price: 2590000,
    rating: 4.2,
    price_before_discount: 3490000,
    quantity: 73,
    sold: 6800,
    view: 3359,
    name: 'Điện thoại OPPO A12 (3GB/32GB) - Hàng chính hãng',
    description:
      '<p>Thể hiện c&aacute; t&iacute;nh của bạn bằng thiết kế kim cương 3D độc đ&aacute;o của OPPO A12, c&ugrave;ng với m&agrave;n h&igrave;nh lớn &ldquo;giọt nước&rdquo; tuyệt mỹ v&agrave; camera k&eacute;p s&agrave;nh điệu, bạn sẽ lu&ocirc;n vui vẻ năng động suốt cả ng&agrave;y.</p><p>Thiết kế si&ecirc;u mỏng nhẹ, họa tiết kim cương 3D ấn tượng<br />OPPO A12 sở hữu thiết kế kh&ocirc;ng chỉ đẹp ph&aacute; c&aacute;ch m&agrave; c&ograve;n rất dễ d&agrave;ng để sử dụng tr&ecirc;n tay. Trọng lượng cực nhẹ 165gram, độ mỏng chỉ 8,3mm kết hợp với những đường bo cong kh&eacute;o l&eacute;o gi&uacute;p điện thoại v&ocirc; c&ugrave;ng gọn nhẹ, mượt m&agrave; trong l&ograve;ng b&agrave;n tay. Kiểu d&aacute;ng của OPPO A12 đặc biệt ấn tượng nhờ điểm nhấn l&agrave; mặt lưng họa tiết 3D kim cương độc đ&aacute;o. Ở mỗi g&oacute;c nh&igrave;n kh&aacute;c nhau, bạn sẽ thấy một vẻ đẹp kh&aacute;c nhau, gi&uacute;p chiếc điện thoại trở n&ecirc;n thực sự nổi bật.</p><p>M&agrave;n h&igrave;nh lớn 6,22 inch &ldquo;giọt nước&rdquo; bảo vệ mắt<br />Bạn sẽ được tận hưởng những h&igrave;nh ảnh hấp dẫn tr&ecirc;n OPPO A12 nhờ m&agrave;n h&igrave;nh lớn k&iacute;ch thước 6,22 inch, hiệu ứng tr&agrave;n viền dạng &ldquo;giọt nước&rdquo; đẹp mắt, tăng cường trải nghiệm xem phim, chơi game.</p><p>Mở kh&oacute;a theo c&aacute;ch của bạn<br />Kh&ocirc;ng cần phải nhập mật khẩu mỗi lần mở m&aacute;y nữa, OPPO A12 cho ph&eacute;p bạn đăng nhập bằng cảm biến v&acirc;n tay hoặc nhận diện khu&ocirc;n mặt AI rất tiện lợi. Chỉ cần chạm nhẹ v&agrave;o khu vực cảm biến mặt lưng hoặc đơn giản l&agrave; đưa m&aacute;y l&ecirc;n, điện thoại sẽ ngay lập tức nhận ra chủ nh&acirc;n của m&aacute;y v&agrave; mở kh&oacute;a một c&aacute;ch sẵn s&agrave;ng.</p><p>Bộ vi xử l&yacute; 8 nh&acirc;n mạnh mẽ<br />OPPO A12 sở hữu cấu h&igrave;nh đ&aacute;ng nể trong tầm gi&aacute; rẻ nhờ bộ vi xử l&yacute; Helio P35. Con chip mới từ nh&agrave; MediaTek sản xuất tr&ecirc;n tiến tr&igrave;nh 12nm với 8 nh&acirc;n cực mạnh, trong đ&oacute; 4 nh&acirc;n 2,35GHz v&agrave; 4 nh&acirc;n 1,8GHz đi c&ugrave;ng GPU đồ họa PowerVR GE8320.</p><p>Thời lượng pin suốt cả ng&agrave;y<br />Mang tr&ecirc;n m&igrave;nh vi&ecirc;n pin dung lượng cực &ldquo;khủng&rdquo; 4230 mAh, bạn c&oacute; thể thoải m&aacute;i sử dụng trong 2 ng&agrave;y ở nhu cầu sử dụng b&igrave;nh thường.</p><p>&Acirc;m thanh sống động<br />OPPO A12 được trang bị t&iacute;nh năng Dirac, một t&iacute;nh năng cho ph&eacute;p chuyển đổi c&aacute;c chế độ &acirc;m thanh th&ocirc;ng minh để tối ưu h&oacute;a &acirc;m thanh dựa tr&ecirc;n t&aacute;c vụ bạn đang hoạt động.</p><p>Camera k&eacute;p AI, tỏa s&aacute;ng trong từng bức ảnh<br />OPPO A12 sở hữu camera k&eacute;p ở mặt sau, bao gồm camera ch&iacute;nh 13MP v&agrave; camera 2MP hỗ trợ đo độ s&acirc;u trường ảnh. Camera ch&iacute;nh chất lượng mang đến những h&igrave;nh ảnh sắc n&eacute;t, r&otilde; r&agrave;ng v&agrave; độ s&aacute;ng, m&agrave;u sắc trung thực.</p><p>Th&ocirc;ng số kỹ thuật chi tiết<br />M&agrave;n h&igrave;nh<br />C&ocirc;ng nghệ m&agrave;n h&igrave;nh : IPS LCD<br />M&agrave;u m&agrave;n h&igrave;nh : 16 triệu m&agrave;u<br />Chuẩn m&agrave;n h&igrave;nh : HD +<br />Độ ph&acirc;n giải m&agrave;n h&igrave;nh : 720 x 1560 Pixels<br />M&agrave;n h&igrave;nh : 6.22 inches<br />Mặt k&iacute;nh m&agrave;n h&igrave;nh : Corning Gorilla Glass 3<br />Camera Trước<br />Độ ph&acirc;n giải : 5 MP<br />Th&ocirc;ng tin kh&aacute;c : Xo&aacute; ph&ocirc;ng, L&agrave;m đẹp (Selfie A.I Beauty), Nh&atilde;n d&aacute;n (AR Stickers), Flash m&agrave;n h&igrave;nh, To&agrave;n cảnh (Panorama), Quay video HD, Chụp bằng cử chỉ, Nhận diện khu&ocirc;n mặt, L&agrave;m đẹp (Beautify), Quay video Full HD, Tự động lấy n&eacute;t (AF), HDR<br />Camera Sau<br />Độ ph&acirc;n giải : Ch&iacute;nh 13 MP &amp; Phụ 2 MP<br />Quay phim : Quay phim HD 720p@30fps<br />Đ&egrave;n Flash : C&oacute;<br />Chụp ảnh n&acirc;ng cao : Google Lens, G&oacute;c si&ecirc;u rộng (Ultrawide), Nh&atilde;n d&aacute;n (AR Stickers), Chụp bằng cử chỉ, Xo&aacute; ph&ocirc;ng, Quay chậm (Slow Motion), Tr&ocirc;i nhanh thời gian (Time Lapse), A.I Camera, L&agrave;m đẹp, Tự động lấy n&eacute;t (AF)<br />Cấu h&igrave;nh phần cứng<br />Tốc độ CPU : 2.3GHz<br />Số nh&acirc;n : 4 nh&acirc;n 2.35 GHz &amp; 4 nh&acirc;n 1.9 GHz<br />Chipset : MediaTek Helio P35 8 nh&acirc;n<br />RAM : 3 GB<br />Chip đồ họa (GPU) : PowerVR GE8320<br />Cảm biến : Cảm biến tiệm cận, Cảm biến &aacute;nh s&aacute;ng, Cảm biến gia tốc kế<br />Bộ nhớ &amp; Lưu trữ<br />Danh bạ lưu trữ : Kh&ocirc;ng giới hạn<br />ROM : 32 GB<br />Bộ nhớ c&ograve;n lại : Đang cập nhật<br />Thẻ nhớ ngo&agrave;i : MicroSD<br />Hỗ trợ thẻ nhớ tối đa : 256 GB<br />Thiết kế &amp; Trọng lượng<br />Kiểu d&aacute;ng : Nguy&ecirc;n Khối<br />Chất liệu : Khung &amp; Mặt lưng nhựa<br />K&iacute;ch thước : D&agrave;i 155.9 mm - Ngang 75.5 mm - D&agrave;y 8.3 mm<br />Trọng lượng : 165 g<br />Khả năng chống nước : Đang cập nhật<br />Th&ocirc;ng tin pin<br />Loại pin : Pin chuẩn Li-Po<br />Dung lượng pin : 4230 mAh<br />Pin c&oacute; thể th&aacute;o rời : Kh&ocirc;ng<br />Chế độ sạc nhanh : Đang cập nhật<br />Kết nối &amp; Cổng giao tiếp<br />Loại SIM : 2 Nano SIM<br />Khe cắm sim : Dual nano-SIM + 1 khe thẻ nhớ<br />Wifi : Wi-Fi 802.11 a/b/g/n/ac, DLNA, Wi-Fi Direct, Wi-Fi hotspot<br />GPS : BDS, A-GPS, GLONASS<br />Bluetooth : Bluetooth 5.0<br />GPRS/EDGE : C&oacute;<br />NFC : Đang cập nhật<br />Cổng sạc : Micro USB<br />Jack (Input &amp; Output) : 3.5mm<br />Giải tr&iacute; &amp; Ứng dụng<br />Xem phim : C&oacute;<br />Nghe nhạc : C&oacute;<br />Ghi &acirc;m : C&oacute;<br />FM radio : C&oacute;<br />Đ&egrave;n pin : C&oacute;<br />Chức năng kh&aacute;c : Đang cập nhật<br />Bảo h&agrave;nh<br />Thời gian bảo h&agrave;nh : 12 Th&aacute;ng<br />Th&ocirc;ng tin h&agrave;ng h&oacute;a<br />Xuất xứ : Trung Quốc<br />Năm sản xuất : 2020<br />Th&ocirc;ng tin d&ograve;ng sản phẩm<br />Model Series : OPPO A12<br />Hệ điều h&agrave;nh<br />Hệ điều h&agrave;nh : ColorOS 6.1.2, nền tảng Android 9<br />Bộ sản phẩm bao gồm: Hộp, Sạc, S&aacute;ch hướng dẫn, C&aacute;p, C&acirc;y lấy sim, Ốp lưng</p><p>Th&ocirc;ng tin bảo h&agrave;nh: <br />Sản phẩm được bảo h&agrave;nh 12 th&aacute;ng tại c&aacute;c trung t&acirc;m bảo h&agrave;nh OPPO<br />- 1 đổi 1 trong 30 ng&agrave;y đầu sử dụng (Lỗi phần cứng sản xuất)<br />- Giao h&agrave;ng tr&ecirc;n to&agrave;n quốc<br />- Hotline: 1800 577 776 (miễn ph&iacute;).</p><p>#OPPO #OPPOA12</p>',
    category: {
      _id: '60afafe76ef5b902180aacb5',
      name: 'Điện thoại',
      __v: 0
    },
    image: 'https://api-ecom.duthanhduoc.com/images/aa374023-7a5b-46ea-aca3-dad1b29fb015.jpg',
    createdAt: '2021-05-27T14:52:50.392Z',
    updatedAt: '2022-12-19T15:19:53.312Z'
  }
}
const productsRequest = http.get(`${config.baseUrl}products`, (req, res, ctx) => {
  return res(ctx.status(HttpStatusCode.Ok), ctx.json(productsRes))
})
const productDetailRequest = http.get(`${config.baseUrl}products/:id`, (req, res, ctx) => {
  return res(ctx.status(HttpStatusCode.Ok), ctx.json(productDetailRes))
})

const getAllProductRes: {
  data: {
    products: ProductResponse[]
  }
} = {
  data: {
    products: [
      {
        id: '2',
        name: 'Small Copper Wallet',
        description: 'Eum libero ipsam possimus accusamus accusamus est doloribus.',
        price: 6671875,
        sold: 9429,
        rating: 2.3
      },
      {
        id: '468',
        name: 'Awesome Marble Bottle',
        description: 'Alias odio est ab qui dolor possimus.',
        price: 40969206,
        sold: 5317,
        rating: 1.3
      },
      {
        id: '469',
        name: 'Awesome Silk Watch',
        description: 'Nam ut laboriosam autem dolor qui quia rerum.',
        price: 7664690,
        sold: 8158,
        rating: 2
      },
      {
        id: '470',
        name: 'Ergonomic Cotton Lamp',
        description: 'Porro aut praesentium.',
        price: 85134441,
        sold: 5642,
        rating: 2.6
      },
      {
        id: '471',
        name: 'Practical Silk Gloves',
        description: 'Unde ut dolorum temporibus est unde aperiam nesciunt.',
        price: 50824780,
        sold: 6632,
        rating: 2.1
      },
      {
        id: '472',
        name: 'Lightweight Rubber Clock',
        description: 'Est et aut qui veniam eum dolorum.',
        price: 14446987,
        sold: 2096,
        rating: 3.7
      },
      {
        id: '473',
        name: 'Fantastic Wool Table',
        description: 'Dolores voluptates voluptas error est asperiores quibusdam odit.',
        price: 52066817,
        sold: 374,
        rating: 3.1
      },
      {
        id: '474',
        name: 'Lightweight Linen Pants',
        description: 'Sunt modi nihil.',
        price: 50513591,
        sold: 274,
        rating: 3
      },
      {
        id: '475',
        name: 'Gorgeous Copper Bottle',
        description: 'Inventore non ipsum consequatur.',
        price: 45402461,
        sold: 1223,
        rating: 4.3
      },
      {
        id: '10',
        name: 'Awesome Paper Bench',
        description: 'Quia et delectus adipisci ad similique.',
        price: 70595743,
        sold: 6198,
        rating: 0
      },
      {
        id: '5',
        name: 'Aerodynamic Leather Plate',
        description: 'Eius praesentium qui distinctio doloribus alias.',
        price: 6601441,
        sold: 911,
        rating: 1.8
      },
      {
        id: '3',
        name: 'Sleek Rubber Table',
        description: 'Laudantium mollitia deleniti neque.',
        price: 69457715,
        sold: 1669,
        rating: 4
      },
      {
        id: '4',
        name: 'Incredible Plastic Gloves',
        description: 'Amet facere molestiae consequatur quo debitis et quia.',
        price: 40857219,
        sold: 6748,
        rating: 2.2
      },
      {
        id: '6',
        name: 'Aerodynamic Silk Car',
        description: 'Tempora incidunt est voluptas.',
        price: 85750664,
        sold: 2220,
        rating: 1.8
      },
      {
        id: '7',
        name: 'Sleek Wool Lamp',
        description: 'Veniam et facilis debitis laboriosam laudantium qui et.',
        price: 407032,
        sold: 8594,
        rating: 0.8
      },
      {
        id: '8',
        name: 'Aerodynamic Wooden Watch',
        description: 'Velit mollitia nam delectus rerum dolores repellat ad.',
        price: 2084186,
        sold: 2776,
        rating: 2.6
      },
      {
        id: '9',
        name: 'Enormous Wooden Watch',
        description: 'Voluptatem minima qui laborum.',
        price: 15720047,
        sold: 7431,
        rating: 0.4
      },
      {
        id: '11',
        name: 'Sleek Leather Computer',
        description: 'Odio inventore enim soluta est delectus doloribus ipsam.',
        price: 58540356,
        sold: 6072,
        rating: 2.9
      },
      {
        id: '12',
        name: 'Heavy Duty Steel Hat',
        description: 'Laborum ullam enim voluptatibus sint.',
        price: 36972972,
        sold: 7710,
        rating: 2.9
      },
      {
        id: '13',
        name: 'Synergistic Silk Pants',
        description: 'Nostrum amet omnis soluta in.',
        price: 55069440,
        sold: 3165,
        rating: 3.9
      },
      {
        id: '14',
        name: 'Rustic Marble Bottle',
        description: 'Dolorem neque eum eos.',
        price: 83116057,
        sold: 7581,
        rating: 2.3
      },
      {
        id: '15',
        name: 'Aerodynamic Plastic Bag',
        description: 'Est omnis deleniti vitae aspernatur in architecto sunt.',
        price: 21806659,
        sold: 2712,
        rating: 2.1
      },
      {
        id: '16',
        name: 'Ergonomic Wool Lamp',
        description: 'Quas praesentium debitis.',
        price: 3186540,
        sold: 8999,
        rating: 1.2
      },
      {
        id: '17',
        name: 'Synergistic Iron Shirt',
        description: 'Possimus non assumenda temporibus est nesciunt exercitationem repellat.',
        price: 31565082,
        sold: 341,
        rating: 3.2
      },
      {
        id: '18',
        name: 'Small Cotton Chair',
        description: 'Laudantium consequatur voluptatem.',
        price: 82576161,
        sold: 622,
        rating: 3.8
      },
      {
        id: '19',
        name: 'Awesome Marble Bag',
        description: 'Quaerat eveniet voluptatem repudiandae ratione dolor asperiores mollitia.',
        price: 83492472,
        sold: 6139,
        rating: 3.2
      },
      {
        id: '20',
        name: 'Ergonomic Wooden Car',
        description: 'Ullam aperiam nemo possimus saepe eum ratione molestias.',
        price: 19817376,
        sold: 4722,
        rating: 0.1
      },
      {
        id: '21',
        name: 'Heavy Duty Paper Lamp',
        description: 'Ipsam eaque laboriosam repellendus distinctio harum.',
        price: 30904310,
        sold: 8000,
        rating: 0.8
      },
      {
        id: '22',
        name: 'Sleek Concrete Watch',
        description: 'Unde minima vel deleniti quae id.',
        price: 83951260,
        sold: 3076,
        rating: 0.3
      },
      {
        id: '23',
        name: 'Practical Concrete Lamp',
        description: 'Nihil et distinctio.',
        price: 29821990,
        sold: 3123,
        rating: 1.7
      },
      {
        id: '24',
        name: 'Practical Rubber Shirt',
        description: 'Est rerum a beatae et.',
        price: 57246492,
        sold: 8170,
        rating: 3
      },
      {
        id: '25',
        name: 'Practical Linen Plate',
        description: 'Praesentium excepturi iure quidem soluta incidunt.',
        price: 31822299,
        sold: 576,
        rating: 2.4
      },
      {
        id: '26',
        name: 'Ergonomic Bronze Wallet',
        description: 'Ratione excepturi ad.',
        price: 42754340,
        sold: 9357,
        rating: 1.2
      },
      {
        id: '27',
        name: 'Mediocre Marble Shirt',
        description: 'At officiis aut eos similique.',
        price: 52183001,
        sold: 1557,
        rating: 0.2
      },
      {
        id: '28',
        name: 'Practical Bronze Chair',
        description: 'Laudantium et quis aut harum consectetur magni.',
        price: 69932638,
        sold: 1917,
        rating: 1.2
      },
      {
        id: '29',
        name: 'Gorgeous Copper Clock',
        description: 'Qui magni ullam.',
        price: 24045873,
        sold: 6745,
        rating: 3.3
      },
      {
        id: '30',
        name: 'Ergonomic Linen Car',
        description: 'Minus non commodi nihil sed enim occaecati.',
        price: 18622726,
        sold: 5953,
        rating: 1.5
      },
      {
        id: '31',
        name: 'Incredible Cotton Gloves',
        description: 'Ab harum a molestias ut officia fugiat.',
        price: 5493500,
        sold: 307,
        rating: 4.3
      },
      {
        id: '32',
        name: 'Synergistic Linen Bench',
        description: 'Amet culpa sequi alias eveniet eaque ut.',
        price: 37449469,
        sold: 2693,
        rating: 3.5
      },
      {
        id: '33',
        name: 'Lightweight Plastic Hat',
        description: 'Omnis vel dolorem facere sunt.',
        price: 49767215,
        sold: 1516,
        rating: 2.4
      },
      {
        id: '34',
        name: 'Durable Paper Coat',
        description: 'Quasi vel consequatur mollitia debitis doloribus.',
        price: 49633204,
        sold: 7563,
        rating: 3.6
      },
      {
        id: '35',
        name: 'Practical Iron Coat',
        description: 'Pariatur modi voluptatibus.',
        price: 60292324,
        sold: 6259,
        rating: 2.4
      },
      {
        id: '36',
        name: 'Practical Bronze Shoes',
        description: 'Sequi eveniet itaque velit earum sunt perspiciatis.',
        price: 35794431,
        sold: 5745,
        rating: 0.8
      },
      {
        id: '37',
        name: 'Enormous Steel Chair',
        description: 'Est ex corrupti nobis labore voluptas eaque cumque.',
        price: 41526435,
        sold: 6111,
        rating: 4
      },
      {
        id: '38',
        name: 'Sleek Plastic Clock',
        description: 'Facilis quibusdam molestias nihil quia officia consequuntur.',
        price: 9938219,
        sold: 1624,
        rating: 2.8
      },
      {
        id: '39',
        name: 'Mediocre Wool Pants',
        description: 'Ad atque quam.',
        price: 14507873,
        sold: 3289,
        rating: 1.9
      },
      {
        id: '40',
        name: 'Practical Aluminum Knife',
        description: 'Aut nulla natus velit modi maxime.',
        price: 18564129,
        sold: 7952,
        rating: 4.1
      },
      {
        id: '41',
        name: 'Incredible Marble Bag',
        description: 'Est quis quam voluptas voluptatem autem.',
        price: 51128588,
        sold: 7777,
        rating: 3
      },
      {
        id: '42',
        name: 'Rustic Silk Table',
        description: 'Tenetur omnis rerum.',
        price: 28244896,
        sold: 4398,
        rating: 3.4
      },
      {
        id: '43',
        name: 'Practical Steel Keyboard',
        description: 'Ab et et et omnis.',
        price: 24874196,
        sold: 6035,
        rating: 1.5
      },
      {
        id: '44',
        name: 'Aerodynamic Cotton Watch',
        description: 'Nulla asperiores ut aut exercitationem.',
        price: 64819093,
        sold: 3947,
        rating: 4.2
      },
      {
        id: '45',
        name: 'Aerodynamic Concrete Bottle',
        description: 'Eius qui facilis provident delectus ex.',
        price: 72523402,
        sold: 9542,
        rating: 0.1
      },
      {
        id: '46',
        name: 'Small Marble Shoes',
        description: 'Atque in culpa facere.',
        price: 60808033,
        sold: 2763,
        rating: 2.9
      },
      {
        id: '47',
        name: 'Awesome Steel Shirt',
        description: 'Ad tenetur quis.',
        price: 89076355,
        sold: 6205,
        rating: 0.5
      },
      {
        id: '48',
        name: 'Intelligent Iron Hat',
        description: 'Qui labore ut.',
        price: 54595289,
        sold: 3463,
        rating: 0.1
      },
      {
        id: '49',
        name: 'Rustic Plastic Lamp',
        description: 'Recusandae inventore velit reiciendis eum illum quia.',
        price: 48968194,
        sold: 6023,
        rating: 1.3
      },
      {
        id: '50',
        name: 'Ergonomic Concrete Keyboard',
        description: 'Quod et recusandae.',
        price: 74995445,
        sold: 1566,
        rating: 2.4
      },
      {
        id: '51',
        name: 'Awesome Linen Lamp',
        description: 'Quis nihil sequi praesentium soluta suscipit.',
        price: 29539951,
        sold: 7922,
        rating: 0.9
      },
      {
        id: '52',
        name: 'Fantastic Wooden Bench',
        description: 'Et non ab voluptatibus quia et.',
        price: 52862855,
        sold: 3996,
        rating: 2.9
      },
      {
        id: '53',
        name: 'Mediocre Iron Keyboard',
        description: 'Vel sapiente cumque aperiam eligendi molestias.',
        price: 20315629,
        sold: 3901,
        rating: 1.4
      },
      {
        id: '54',
        name: 'Durable Marble Bench',
        description: 'Quasi totam eligendi eius quisquam autem dolores voluptates.',
        price: 81521827,
        sold: 6889,
        rating: 1.4
      },
      {
        id: '55',
        name: 'Heavy Duty Marble Shoes',
        description: 'Distinctio fuga ullam.',
        price: 65671868,
        sold: 6560,
        rating: 0.1
      },
      {
        id: '56',
        name: 'Fantastic Aluminum Wallet',
        description: 'Animi hic delectus.',
        price: 66222387,
        sold: 1536,
        rating: 1.6
      },
      {
        id: '57',
        name: 'Enormous Wooden Bag',
        description: 'Ea deleniti ea cupiditate.',
        price: 76732102,
        sold: 6699,
        rating: 1.3
      },
      {
        id: '58',
        name: 'Practical Leather Bottle',
        description: 'Est itaque voluptatum dolore.',
        price: 83164225,
        sold: 8107,
        rating: 0.1
      },
      {
        id: '59',
        name: 'Durable Aluminum Shirt',
        description: 'Possimus voluptatum ducimus.',
        price: 80209327,
        sold: 8189,
        rating: 2.5
      },
      {
        id: '60',
        name: 'Intelligent Linen Shirt',
        description: 'Soluta impedit nobis tenetur rerum quas beatae corporis.',
        price: 88799899,
        sold: 5506,
        rating: 0.1
      },
      {
        id: '61',
        name: 'Durable Granite Gloves',
        description: 'Qui et ea dolorem ea eius sit.',
        price: 86103301,
        sold: 1794,
        rating: 0.5
      },
      {
        id: '62',
        name: 'Small Plastic Knife',
        description: 'Adipisci dolore voluptate inventore.',
        price: 22231272,
        sold: 401,
        rating: 0.3
      },
      {
        id: '63',
        name: 'Heavy Duty Copper Gloves',
        description: 'Molestiae cupiditate aliquam et.',
        price: 60660872,
        sold: 9681,
        rating: 0.7
      },
      {
        id: '64',
        name: 'Lightweight Cotton Coat',
        description: 'Voluptatem quas in repellat possimus aliquam voluptatem quia.',
        price: 58968198,
        sold: 2039,
        rating: 3.1
      },
      {
        id: '65',
        name: 'Sleek Silk Bottle',
        description: 'Dolor voluptates porro corrupti.',
        price: 32357397,
        sold: 5869,
        rating: 2.8
      },
      {
        id: '66',
        name: 'Durable Wooden Bench',
        description: 'Soluta deserunt consequatur sint.',
        price: 56611199,
        sold: 5718,
        rating: 0.7
      },
      {
        id: '67',
        name: 'Practical Wool Lamp',
        description: 'Quaerat eligendi et et temporibus assumenda et optio.',
        price: 8016453,
        sold: 6959,
        rating: 2.6
      },
      {
        id: '68',
        name: 'Fantastic Granite Table',
        description: 'Et odit maxime voluptas.',
        price: 13127453,
        sold: 987,
        rating: 2.5
      },
      {
        id: '69',
        name: 'Practical Copper Coat',
        description: 'Deleniti eligendi dolore nihil sunt harum ipsam consequatur.',
        price: 32628082,
        sold: 1942,
        rating: 4.4
      },
      {
        id: '70',
        name: 'Lightweight Paper Shoes',
        description: 'Reprehenderit id illo et repudiandae aut neque.',
        price: 40059799,
        sold: 3433,
        rating: 2.1
      },
      {
        id: '71',
        name: 'Durable Granite Lamp',
        description: 'Vel dolor ut ipsa asperiores sunt voluptatem.',
        price: 30536952,
        sold: 6124,
        rating: 1
      },
      {
        id: '72',
        name: 'Incredible Copper Table',
        description: 'Quae excepturi optio rem.',
        price: 16394560,
        sold: 659,
        rating: 1.3
      },
      {
        id: '73',
        name: 'Practical Granite Hat',
        description: 'Voluptatem iure et consequuntur.',
        price: 30642706,
        sold: 9766,
        rating: 0.4
      },
      {
        id: '74',
        name: 'Sleek Wooden Computer',
        description: 'Aliquam similique molestias et saepe ab deserunt ex.',
        price: 4177080,
        sold: 699,
        rating: 0.4
      },
      {
        id: '75',
        name: 'Small Wooden Bench',
        description: 'Rem repellendus asperiores illo eum quisquam quaerat.',
        price: 630933,
        sold: 1370,
        rating: 3.9
      },
      {
        id: '76',
        name: 'Fantastic Concrete Watch',
        description: 'Cum aut quia velit molestias et possimus perferendis.',
        price: 87087239,
        sold: 1799,
        rating: 0.4
      },
      {
        id: '77',
        name: 'Enormous Silk Plate',
        description: 'Cum non pariatur molestiae ab sequi.',
        price: 87972626,
        sold: 9698,
        rating: 0
      },
      {
        id: '78',
        name: 'Gorgeous Paper Bag',
        description: 'Non alias unde pariatur a repellendus ducimus qui.',
        price: 6663758,
        sold: 8786,
        rating: 1.4
      },
      {
        id: '79',
        name: 'Durable Concrete Coat',
        description: 'Aliquam iusto temporibus odio dolorem ut reprehenderit omnis.',
        price: 28023696,
        sold: 6855,
        rating: 1.1
      },
      {
        id: '80',
        name: 'Sleek Aluminum Hat',
        description: 'Asperiores non tempore quos aut rerum eos.',
        price: 66401639,
        sold: 6206,
        rating: 3.1
      },
      {
        id: '81',
        name: 'Rustic Silk Watch',
        description: 'Pariatur possimus iste rerum occaecati dolore pariatur culpa.',
        price: 19543908,
        sold: 9629,
        rating: 1.4
      },
      {
        id: '82',
        name: 'Gorgeous Bronze Table',
        description: 'Eos beatae ea reprehenderit repellendus voluptate consequuntur.',
        price: 38233369,
        sold: 2963,
        rating: 1.7
      },
      {
        id: '83',
        name: 'Heavy Duty Wool Keyboard',
        description: 'Minus nihil sit omnis.',
        price: 40930082,
        sold: 6601,
        rating: 1.7
      },
      {
        id: '84',
        name: 'Durable Paper Bench',
        description: 'Saepe distinctio hic tenetur quae voluptates minima.',
        price: 9162576,
        sold: 6964,
        rating: 3.5
      },
      {
        id: '85',
        name: 'Practical Wool Hat',
        description: 'Architecto repellat tempore labore.',
        price: 25009537,
        sold: 8382,
        rating: 2.4
      },
      {
        id: '86',
        name: 'Ergonomic Iron Bag',
        description: 'Quia et et.',
        price: 50292966,
        sold: 3703,
        rating: 1.5
      },
      {
        id: '87',
        name: 'Heavy Duty Leather Keyboard',
        description: 'Dolore excepturi totam ea.',
        price: 62807671,
        sold: 9888,
        rating: 2.2
      },
      {
        id: '88',
        name: 'Synergistic Cotton Wallet',
        description: 'Aliquid ut maxime tenetur praesentium cumque.',
        price: 66414864,
        sold: 9863,
        rating: 3.7
      },
      {
        id: '89',
        name: 'Rustic Rubber Computer',
        description: 'Sint eum eveniet natus quas sapiente.',
        price: 1328302,
        sold: 9534,
        rating: 3.4
      },
      {
        id: '90',
        name: 'Small Plastic Computer',
        description: 'Et doloremque voluptates maxime facere.',
        price: 35238123,
        sold: 6294,
        rating: 0.1
      },
      {
        id: '91',
        name: 'Heavy Duty Cotton Bench',
        description: 'Sit laborum sunt eos deleniti perferendis.',
        price: 70937974,
        sold: 4948,
        rating: 0.5
      },
      {
        id: '92',
        name: 'Lightweight Bronze Table',
        description: 'Deserunt ad mollitia ratione quia laudantium aliquam ducimus.',
        price: 47549844,
        sold: 4073,
        rating: 0.6
      },
      {
        id: '93',
        name: 'Awesome Granite Bench',
        description: 'Enim et accusantium laboriosam assumenda eaque.',
        price: 32622289,
        sold: 4243,
        rating: 1.7
      },
      {
        id: '94',
        name: 'Mediocre Marble Wallet',
        description: 'Tempora voluptatem voluptatem explicabo.',
        price: 41277223,
        sold: 6926,
        rating: 3.3
      },
      {
        id: '95',
        name: 'Gorgeous Paper Hat',
        description: 'Veritatis et dolorem hic deleniti optio.',
        price: 77413116,
        sold: 5821,
        rating: 3.1
      },
      {
        id: '96',
        name: 'Gorgeous Rubber Hat',
        description: 'Dolorem necessitatibus exercitationem sunt consequatur pariatur nulla.',
        price: 43235614,
        sold: 9608,
        rating: 0.2
      },
      {
        id: '97',
        name: 'Mediocre Leather Chair',
        description: 'Non harum quo voluptas ut est tempora sapiente.',
        price: 5997776,
        sold: 2992,
        rating: 4.1
      },
      {
        id: '98',
        name: 'Awesome Concrete Car',
        description: 'Minus et odit facilis ab et.',
        price: 80009590,
        sold: 9554,
        rating: 0.3
      },
      {
        id: '99',
        name: 'Ergonomic Rubber Pants',
        description: 'Voluptas aut et.',
        price: 56665322,
        sold: 1443,
        rating: 0
      },
      {
        id: '100',
        name: 'Gorgeous Wooden Plate',
        description: 'Id illum rerum at hic.',
        price: 38063218,
        sold: 5863,
        rating: 4.3
      },
      {
        id: '101',
        name: 'Durable Rubber Knife',
        description: 'Quo et soluta voluptatem hic.',
        price: 43086048,
        sold: 8854,
        rating: 2.6
      },
      {
        id: '102',
        name: 'Fantastic Wooden Gloves',
        description: 'Aut eligendi voluptatum consectetur.',
        price: 30684331,
        sold: 2626,
        rating: 4
      },
      {
        id: '103',
        name: 'Ergonomic Rubber Plate',
        description: 'Qui necessitatibus voluptatem esse quaerat culpa nam.',
        price: 65509563,
        sold: 7748,
        rating: 4.5
      },
      {
        id: '104',
        name: 'Aerodynamic Wooden Coat',
        description: 'Dolor eligendi recusandae id laudantium eius quo.',
        price: 10639422,
        sold: 6892,
        rating: 4.1
      },
      {
        id: '105',
        name: 'Fantastic Concrete Keyboard',
        description: 'Aut rerum nemo ut.',
        price: 47128418,
        sold: 575,
        rating: 1.8
      },
      {
        id: '106',
        name: 'Ergonomic Steel Computer',
        description: 'Quo itaque sit veniam facere facilis facere ut.',
        price: 59323613,
        sold: 5173,
        rating: 3.8
      },
      {
        id: '107',
        name: 'Aerodynamic Linen Keyboard',
        description: 'Accusantium quia enim beatae.',
        price: 3509991,
        sold: 3984,
        rating: 1.5
      },
      {
        id: '108',
        name: 'Durable Silk Watch',
        description: 'Cupiditate natus dolore laudantium aspernatur voluptate.',
        price: 18902417,
        sold: 4268,
        rating: 4.2
      },
      {
        id: '109',
        name: 'Durable Silk Watch',
        description: 'Aut officiis aut ducimus est.',
        price: 11399599,
        sold: 1683,
        rating: 1
      },
      {
        id: '110',
        name: 'Ergonomic Copper Watch',
        description: 'Quia autem et tempore et.',
        price: 33128232,
        sold: 7060,
        rating: 1.3
      },
      {
        id: '111',
        name: 'Sleek Paper Computer',
        description: 'Modi recusandae officiis eos labore.',
        price: 75262025,
        sold: 8225,
        rating: 1.5
      },
      {
        id: '112',
        name: 'Fantastic Aluminum Computer',
        description: 'Molestiae a nostrum et facilis.',
        price: 78686394,
        sold: 6826,
        rating: 0.3
      },
      {
        id: '113',
        name: 'Lightweight Wooden Watch',
        description: 'Recusandae labore quia modi tenetur perspiciatis distinctio quas.',
        price: 82481022,
        sold: 6160,
        rating: 3
      },
      {
        id: '114',
        name: 'Ergonomic Steel Table',
        description: 'Iusto nihil non.',
        price: 31718128,
        sold: 2882,
        rating: 1
      },
      {
        id: '115',
        name: 'Synergistic Steel Car',
        description: 'Doloribus neque in quis numquam numquam exercitationem.',
        price: 59119728,
        sold: 4872,
        rating: 0.1
      },
      {
        id: '116',
        name: 'Heavy Duty Leather Knife',
        description: 'Cum quae commodi nihil dignissimos esse deleniti.',
        price: 5880875,
        sold: 5709,
        rating: 0.8
      },
      {
        id: '117',
        name: 'Practical Steel Plate',
        description: 'Ut accusantium quisquam excepturi ut laboriosam similique.',
        price: 3442207,
        sold: 7258,
        rating: 0.4
      },
      {
        id: '118',
        name: 'Mediocre Paper Bag',
        description: 'Dolorem sunt occaecati tenetur iure ab magnam aperiam.',
        price: 24848643,
        sold: 4511,
        rating: 0.4
      },
      {
        id: '119',
        name: 'Enormous Granite Shoes',
        description: 'Unde voluptatem sint quas facere aspernatur ipsam et.',
        price: 46087425,
        sold: 3768,
        rating: 1.6
      },
      {
        id: '120',
        name: 'Mediocre Silk Computer',
        description: 'Et libero harum dolorem dolorum deleniti voluptas.',
        price: 26011050,
        sold: 3295,
        rating: 1.4
      },
      {
        id: '121',
        name: 'Intelligent Silk Hat',
        description: 'Et quia esse dolor aut sit labore doloremque.',
        price: 20471431,
        sold: 6743,
        rating: 3.6
      },
      {
        id: '122',
        name: 'Intelligent Steel Watch',
        description: 'Perspiciatis id laboriosam aspernatur.',
        price: 21615196,
        sold: 9869,
        rating: 3.6
      },
      {
        id: '123',
        name: 'Awesome Rubber Shoes',
        description: 'Inventore provident non quos nemo qui sit adipisci.',
        price: 34902963,
        sold: 5063,
        rating: 2.7
      },
      {
        id: '124',
        name: 'Synergistic Steel Shoes',
        description: 'Soluta voluptatibus quo enim.',
        price: 15483241,
        sold: 122,
        rating: 3.7
      },
      {
        id: '125',
        name: 'Fantastic Silk Car',
        description: 'Non quia eaque doloribus ut.',
        price: 71718940,
        sold: 3262,
        rating: 0.2
      },
      {
        id: '126',
        name: 'Fantastic Wooden Shoes',
        description: 'Earum laboriosam voluptas quo illum accusantium vitae.',
        price: 46808155,
        sold: 4441,
        rating: 4.1
      },
      {
        id: '127',
        name: 'Incredible Plastic Shirt',
        description: 'Ratione temporibus expedita.',
        price: 28597331,
        sold: 6663,
        rating: 3.9
      },
      {
        id: '128',
        name: 'Aerodynamic Cotton Knife',
        description: 'Quia deserunt blanditiis.',
        price: 58640326,
        sold: 9960,
        rating: 2.9
      },
      {
        id: '129',
        name: 'Durable Marble Car',
        description: 'Quam dolores dolores animi cupiditate.',
        price: 4384986,
        sold: 4979,
        rating: 2.8
      },
      {
        id: '130',
        name: 'Incredible Marble Hat',
        description: 'Omnis veniam et et.',
        price: 62164276,
        sold: 6874,
        rating: 2.4
      },
      {
        id: '131',
        name: 'Awesome Paper Gloves',
        description: 'Qui molestiae eum possimus repudiandae repellat.',
        price: 53718249,
        sold: 6794,
        rating: 1.9
      },
      {
        id: '132',
        name: 'Small Silk Coat',
        description: 'Velit cum aut natus.',
        price: 68319173,
        sold: 9912,
        rating: 0.9
      },
      {
        id: '133',
        name: 'Awesome Iron Lamp',
        description: 'Necessitatibus quos amet eveniet.',
        price: 79365121,
        sold: 875,
        rating: 0.8
      },
      {
        id: '134',
        name: 'Practical Steel Keyboard',
        description: 'Et dolor expedita quod nobis.',
        price: 68167127,
        sold: 8747,
        rating: 3.3
      },
      {
        id: '135',
        name: 'Gorgeous Bronze Hat',
        description: 'Non aut fugit reiciendis et.',
        price: 24286131,
        sold: 6463,
        rating: 2.2
      },
      {
        id: '136',
        name: 'Mediocre Silk Computer',
        description: 'Aliquam cupiditate maiores.',
        price: 81419224,
        sold: 5683,
        rating: 1.3
      },
      {
        id: '137',
        name: 'Ergonomic Bronze Shirt',
        description: 'Nulla eius repudiandae.',
        price: 76332478,
        sold: 6576,
        rating: 1.7
      },
      {
        id: '138',
        name: 'Heavy Duty Linen Bench',
        description: 'Quae ut dolor esse delectus harum.',
        price: 8621509,
        sold: 3015,
        rating: 2.6
      },
      {
        id: '139',
        name: 'Gorgeous Linen Watch',
        description: 'Corrupti pariatur minus.',
        price: 62275141,
        sold: 1855,
        rating: 2.1
      },
      {
        id: '140',
        name: 'Synergistic Copper Keyboard',
        description: 'Rerum architecto reiciendis fugit.',
        price: 27295201,
        sold: 1417,
        rating: 1
      },
      {
        id: '141',
        name: 'Lightweight Granite Bottle',
        description: 'Dignissimos aut necessitatibus.',
        price: 7830228,
        sold: 3192,
        rating: 0.6
      },
      {
        id: '142',
        name: 'Mediocre Wooden Lamp',
        description: 'Hic sed ut dolores.',
        price: 39486715,
        sold: 9684,
        rating: 3.8
      },
      {
        id: '143',
        name: 'Synergistic Rubber Keyboard',
        description: 'Hic eius itaque nisi corrupti perferendis voluptatem quisquam.',
        price: 11655738,
        sold: 2791,
        rating: 0.9
      },
      {
        id: '144',
        name: 'Fantastic Iron Bag',
        description: 'Repudiandae quidem consectetur.',
        price: 49777923,
        sold: 5100,
        rating: 3.5
      },
      {
        id: '145',
        name: 'Mediocre Wooden Knife',
        description: 'Sed nihil incidunt.',
        price: 19973268,
        sold: 4873,
        rating: 3.4
      },
      {
        id: '146',
        name: 'Mediocre Paper Hat',
        description: 'Non officiis architecto unde quos quo officia et.',
        price: 45377513,
        sold: 3403,
        rating: 2.6
      },
      {
        id: '147',
        name: 'Heavy Duty Steel Pants',
        description: 'Distinctio animi a consequatur est rerum corporis.',
        price: 57607042,
        sold: 7453,
        rating: 0.5
      },
      {
        id: '148',
        name: 'Sleek Steel Bench',
        description: 'Dolorem aut dolor sunt omnis.',
        price: 13416606,
        sold: 5205,
        rating: 1.7
      },
      {
        id: '149',
        name: 'Rustic Steel Keyboard',
        description: 'Ut assumenda ut esse ducimus repellat.',
        price: 88034086,
        sold: 4534,
        rating: 0.7
      },
      {
        id: '150',
        name: 'Sleek Wooden Shirt',
        description: 'Ex qui maiores sed non.',
        price: 44569538,
        sold: 8780,
        rating: 1.7
      },
      {
        id: '151',
        name: 'Sleek Granite Clock',
        description: 'Aut et est.',
        price: 27429152,
        sold: 8517,
        rating: 0.2
      },
      {
        id: '152',
        name: 'Rustic Granite Computer',
        description: 'Commodi omnis ea sed saepe beatae laborum nam.',
        price: 1471023,
        sold: 884,
        rating: 2.9
      },
      {
        id: '153',
        name: 'Mediocre Wooden Knife',
        description: 'Quas aut modi optio ex est.',
        price: 86894664,
        sold: 6959,
        rating: 2.5
      },
      {
        id: '154',
        name: 'Gorgeous Plastic Hat',
        description: 'Molestiae vel unde.',
        price: 79461958,
        sold: 3928,
        rating: 0.9
      },
      {
        id: '155',
        name: 'Aerodynamic Plastic Shoes',
        description: 'Facilis magnam dolore aspernatur molestiae.',
        price: 81526720,
        sold: 1799,
        rating: 0.4
      },
      {
        id: '156',
        name: 'Mediocre Paper Lamp',
        description: 'Autem repellat quibusdam cumque rem est.',
        price: 1565419,
        sold: 986,
        rating: 2.8
      },
      {
        id: '157',
        name: 'Intelligent Cotton Coat',
        description: 'Quis aliquid quia impedit cumque quia praesentium.',
        price: 11464013,
        sold: 1450,
        rating: 4.3
      },
      {
        id: '158',
        name: 'Awesome Paper Chair',
        description: 'Quia odit dolorum fugiat deleniti eos doloribus.',
        price: 19274403,
        sold: 2409,
        rating: 1.9
      },
      {
        id: '159',
        name: 'Rustic Plastic Plate',
        description: 'Porro voluptas vel sit fuga error.',
        price: 15375890,
        sold: 3685,
        rating: 1.2
      },
      {
        id: '160',
        name: 'Rustic Marble Computer',
        description: 'Rem odio aut et in qui aut reprehenderit.',
        price: 45711262,
        sold: 5458,
        rating: 0.3
      },
      {
        id: '161',
        name: 'Gorgeous Wooden Wallet',
        description: 'Est iste occaecati.',
        price: 9531591,
        sold: 5398,
        rating: 1.2
      },
      {
        id: '162',
        name: 'Small Silk Hat',
        description: 'Eius ut doloremque doloremque velit quia.',
        price: 73564988,
        sold: 8201,
        rating: 2.6
      },
      {
        id: '163',
        name: 'Gorgeous Concrete Wallet',
        description: 'Esse consequatur est rerum eum architecto.',
        price: 83642195,
        sold: 8401,
        rating: 1.7
      },
      {
        id: '164',
        name: 'Synergistic Wool Wallet',
        description: 'Occaecati exercitationem incidunt.',
        price: 76785778,
        sold: 869,
        rating: 2.7
      },
      {
        id: '165',
        name: 'Intelligent Cotton Coat',
        description: 'Voluptatem voluptates omnis id incidunt cumque.',
        price: 72116250,
        sold: 5801,
        rating: 0.4
      },
      {
        id: '166',
        name: 'Heavy Duty Plastic Shoes',
        description: 'Tempora id quasi est vitae omnis officiis.',
        price: 45668099,
        sold: 9935,
        rating: 1.8
      },
      {
        id: '167',
        name: 'Heavy Duty Silk Watch',
        description: 'A est facere et est.',
        price: 27158657,
        sold: 563,
        rating: 4.4
      },
      {
        id: '168',
        name: 'Rustic Silk Shoes',
        description: 'Consectetur culpa officiis nemo et et.',
        price: 18588819,
        sold: 8178,
        rating: 2.7
      },
      {
        id: '169',
        name: 'Synergistic Concrete Chair',
        description: 'Nobis atque quia nostrum in.',
        price: 47863186,
        sold: 3922,
        rating: 0.2
      },
      {
        id: '170',
        name: 'Heavy Duty Marble Table',
        description: 'Et molestiae odio placeat rerum.',
        price: 55329988,
        sold: 800,
        rating: 2.9
      },
      {
        id: '171',
        name: 'Heavy Duty Granite Car',
        description: 'Enim dolor dolore sit sint.',
        price: 40368670,
        sold: 4620,
        rating: 3.2
      },
      {
        id: '172',
        name: 'Mediocre Paper Table',
        description: 'Fugiat enim dolor molestiae sunt molestiae qui.',
        price: 62341174,
        sold: 6426,
        rating: 3.2
      },
      {
        id: '173',
        name: 'Incredible Steel Watch',
        description: 'Aut modi maiores qui odio consequatur nisi unde.',
        price: 78375328,
        sold: 8570,
        rating: 3.6
      },
      {
        id: '174',
        name: 'Fantastic Leather Clock',
        description: 'Reiciendis iusto enim provident eaque dolores voluptatem.',
        price: 8214896,
        sold: 5185,
        rating: 0
      },
      {
        id: '175',
        name: 'Synergistic Aluminum Watch',
        description: 'Delectus labore omnis dicta et quis.',
        price: 81788487,
        sold: 9316,
        rating: 2.2
      },
      {
        id: '176',
        name: 'Awesome Bronze Chair',
        description: 'Consequatur esse nisi enim.',
        price: 44484393,
        sold: 5080,
        rating: 4.1
      },
      {
        id: '177',
        name: 'Durable Concrete Coat',
        description: 'Nulla nostrum consequuntur sed.',
        price: 75004947,
        sold: 7272,
        rating: 1.9
      },
      {
        id: '178',
        name: 'Fantastic Steel Keyboard',
        description: 'Itaque omnis occaecati earum.',
        price: 32129514,
        sold: 2749,
        rating: 2.5
      },
      {
        id: '179',
        name: 'Intelligent Linen Hat',
        description: 'Quia odio totam officia nisi et laudantium ex.',
        price: 10970923,
        sold: 1776,
        rating: 0.8
      },
      {
        id: '180',
        name: 'Intelligent Cotton Table',
        description: 'Est voluptatem voluptas sed doloremque a qui.',
        price: 23291684,
        sold: 9079,
        rating: 0.2
      },
      {
        id: '181',
        name: 'Aerodynamic Bronze Knife',
        description: 'Sed dolore eos eligendi culpa.',
        price: 48060724,
        sold: 8005,
        rating: 4.1
      },
      {
        id: '182',
        name: 'Mediocre Aluminum Bag',
        description: 'Temporibus possimus aut voluptatem neque ipsam.',
        price: 35991976,
        sold: 7569,
        rating: 0.8
      },
      {
        id: '183',
        name: 'Enormous Marble Watch',
        description: 'Dolores atque et corrupti non quo.',
        price: 68541192,
        sold: 1605,
        rating: 0.8
      },
      {
        id: '184',
        name: 'Lightweight Steel Chair',
        description: 'Earum omnis dolor velit quidem nulla amet.',
        price: 85281877,
        sold: 4951,
        rating: 3.3
      },
      {
        id: '185',
        name: 'Fantastic Linen Wallet',
        description: 'Modi ut ducimus facilis autem illum voluptas nulla.',
        price: 35320644,
        sold: 6295,
        rating: 1.7
      },
      {
        id: '186',
        name: 'Aerodynamic Plastic Pants',
        description: 'Aperiam similique aspernatur nihil et.',
        price: 39700196,
        sold: 7561,
        rating: 4.2
      },
      {
        id: '187',
        name: 'Practical Wooden Plate',
        description: 'Quis aut quibusdam nulla facere in ut.',
        price: 79019077,
        sold: 7721,
        rating: 1.7
      },
      {
        id: '188',
        name: 'Fantastic Concrete Wallet',
        description: 'Fugit harum exercitationem soluta facilis omnis qui et.',
        price: 7375709,
        sold: 9572,
        rating: 3.7
      },
      {
        id: '189',
        name: 'Rustic Aluminum Car',
        description: 'Non quia voluptatem quod.',
        price: 70678340,
        sold: 1063,
        rating: 3.3
      },
      {
        id: '190',
        name: 'Fantastic Leather Keyboard',
        description: 'Quod esse unde numquam provident et iste at.',
        price: 38444028,
        sold: 1231,
        rating: 0.2
      },
      {
        id: '191',
        name: 'Rustic Steel Plate',
        description: 'Ducimus consequatur pariatur velit voluptatem quo laborum distinctio.',
        price: 19347641,
        sold: 1412,
        rating: 0.7
      },
      {
        id: '192',
        name: 'Gorgeous Plastic Keyboard',
        description: 'Possimus aut assumenda.',
        price: 9010137,
        sold: 8768,
        rating: 0.3
      },
      {
        id: '193',
        name: 'Ergonomic Cotton Knife',
        description: 'Facere labore molestiae et.',
        price: 46570244,
        sold: 2344,
        rating: 2.7
      },
      {
        id: '194',
        name: 'Aerodynamic Bronze Knife',
        description: 'Voluptatum quaerat ex nesciunt.',
        price: 25822019,
        sold: 4260,
        rating: 4.1
      },
      {
        id: '195',
        name: 'Practical Wool Shoes',
        description: 'Assumenda voluptas perspiciatis rem molestiae.',
        price: 83278209,
        sold: 6895,
        rating: 2.8
      },
      {
        id: '196',
        name: 'Aerodynamic Cotton Gloves',
        description: 'Fugit est quia.',
        price: 27957752,
        sold: 1136,
        rating: 1.9
      },
      {
        id: '197',
        name: 'Small Linen Knife',
        description: 'Autem recusandae cumque.',
        price: 74342949,
        sold: 7575,
        rating: 0.8
      },
      {
        id: '198',
        name: 'Incredible Iron Shirt',
        description: 'Labore totam ducimus officia sint quidem totam nihil.',
        price: 30298227,
        sold: 1523,
        rating: 0.1
      },
      {
        id: '199',
        name: 'Enormous Cotton Knife',
        description: 'Quaerat officia corrupti est.',
        price: 23273831,
        sold: 2870,
        rating: 2.6
      },
      {
        id: '200',
        name: 'Durable Leather Knife',
        description: 'Cupiditate aut optio nihil magnam qui dolorem accusamus.',
        price: 3133757,
        sold: 2821,
        rating: 2.1
      },
      {
        id: '201',
        name: 'Lightweight Iron Coat',
        description: 'Ullam sunt quod enim enim voluptates.',
        price: 76484705,
        sold: 6450,
        rating: 2.9
      },
      {
        id: '202',
        name: 'Practical Aluminum Bench',
        description: 'Et cum et labore.',
        price: 37453399,
        sold: 5012,
        rating: 2.5
      },
      {
        id: '203',
        name: 'Aerodynamic Copper Computer',
        description: 'Officia et porro molestiae eius.',
        price: 63953504,
        sold: 2416,
        rating: 3.3
      },
      {
        id: '204',
        name: 'Durable Marble Watch',
        description: 'Iusto velit maxime placeat iusto dolorem.',
        price: 14555066,
        sold: 6840,
        rating: 2.2
      },
      {
        id: '205',
        name: 'Awesome Iron Watch',
        description: 'Praesentium voluptate voluptas excepturi nulla id praesentium.',
        price: 88694667,
        sold: 270,
        rating: 0.9
      },
      {
        id: '206',
        name: 'Intelligent Rubber Chair',
        description: 'Quae natus in corrupti eos id.',
        price: 39669957,
        sold: 1580,
        rating: 3.8
      },
      {
        id: '207',
        name: 'Awesome Cotton Bag',
        description: 'Dolores deserunt quibusdam consequatur consequatur et ut illo.',
        price: 29293863,
        sold: 427,
        rating: 3.6
      },
      {
        id: '208',
        name: 'Sleek Rubber Wallet',
        description: 'Delectus itaque nemo fugiat dolorum.',
        price: 70438090,
        sold: 6012,
        rating: 2.4
      },
      {
        id: '209',
        name: 'Ergonomic Marble Gloves',
        description: 'Labore qui id repellendus.',
        price: 61328712,
        sold: 2968,
        rating: 3.3
      },
      {
        id: '210',
        name: 'Awesome Iron Computer',
        description: 'Nisi placeat tempore odio nobis quis et sequi.',
        price: 65693420,
        sold: 5642,
        rating: 3.6
      },
      {
        id: '211',
        name: 'Awesome Plastic Hat',
        description: 'Aut in recusandae consequatur aut et deleniti.',
        price: 70383821,
        sold: 3056,
        rating: 2.8
      },
      {
        id: '212',
        name: 'Ergonomic Bronze Coat',
        description: 'Excepturi veniam est et labore quis voluptatem.',
        price: 27433497,
        sold: 5129,
        rating: 0.4
      },
      {
        id: '213',
        name: 'Sleek Linen Bag',
        description: 'Quo asperiores repellendus voluptatem repellat quo.',
        price: 83121759,
        sold: 3712,
        rating: 1.6
      },
      {
        id: '214',
        name: 'Heavy Duty Paper Plate',
        description: 'Inventore ut fuga sit illo.',
        price: 6840685,
        sold: 8031,
        rating: 3.5
      },
      {
        id: '215',
        name: 'Ergonomic Wooden Knife',
        description: 'Voluptatem quam non et vero harum odit temporibus.',
        price: 52444565,
        sold: 2049,
        rating: 2.8
      },
      {
        id: '216',
        name: 'Ergonomic Cotton Car',
        description: 'Quis dolorem libero fugit fugiat.',
        price: 2101070,
        sold: 1170,
        rating: 4.3
      },
      {
        id: '217',
        name: 'Incredible Cotton Car',
        description: 'Fugit consequatur autem quod.',
        price: 20885883,
        sold: 3505,
        rating: 4.2
      },
      {
        id: '218',
        name: 'Enormous Concrete Watch',
        description: 'Perspiciatis nesciunt voluptatem et ullam explicabo cum tempore.',
        price: 80046311,
        sold: 6091,
        rating: 1.1
      },
      {
        id: '219',
        name: 'Heavy Duty Marble Pants',
        description: 'Occaecati asperiores aliquid odit sunt aspernatur.',
        price: 60253109,
        sold: 5149,
        rating: 0.9
      },
      {
        id: '220',
        name: 'Awesome Rubber Car',
        description: 'Aperiam nesciunt et nihil fugiat rerum.',
        price: 43024982,
        sold: 1255,
        rating: 3.3
      },
      {
        id: '221',
        name: 'Rustic Copper Coat',
        description: 'Velit distinctio officiis molestias.',
        price: 14268654,
        sold: 8653,
        rating: 2.4
      },
      {
        id: '222',
        name: 'Mediocre Silk Coat',
        description: 'Et iusto accusamus error ea numquam.',
        price: 41102506,
        sold: 7750,
        rating: 2.9
      },
      {
        id: '223',
        name: 'Enormous Paper Watch',
        description: 'Laboriosam fugiat in distinctio sequi debitis tenetur dolor.',
        price: 35775800,
        sold: 5759,
        rating: 0.9
      },
      {
        id: '224',
        name: 'Ergonomic Granite Keyboard',
        description: 'Ipsum nostrum repellendus quae nesciunt.',
        price: 23488419,
        sold: 5841,
        rating: 3.9
      },
      {
        id: '225',
        name: 'Gorgeous Plastic Car',
        description: 'Quidem et in eveniet aliquam in.',
        price: 17633473,
        sold: 345,
        rating: 2.5
      },
      {
        id: '226',
        name: 'Synergistic Iron Coat',
        description: 'Consequatur eum magnam et ut.',
        price: 55799187,
        sold: 809,
        rating: 4.1
      },
      {
        id: '227',
        name: 'Aerodynamic Wool Gloves',
        description: 'Nam sequi enim quaerat natus dignissimos.',
        price: 78186622,
        sold: 6717,
        rating: 1.7
      },
      {
        id: '228',
        name: 'Fantastic Plastic Chair',
        description: 'Unde mollitia nisi sint et consequuntur.',
        price: 70358611,
        sold: 8176,
        rating: 2.4
      },
      {
        id: '229',
        name: 'Ergonomic Linen Computer',
        description: 'Et sed enim culpa occaecati et.',
        price: 33935190,
        sold: 4059,
        rating: 2.9
      },
      {
        id: '230',
        name: 'Enormous Iron Hat',
        description: 'Rem doloribus in ullam.',
        price: 28618718,
        sold: 3899,
        rating: 0.3
      },
      {
        id: '231',
        name: 'Intelligent Steel Chair',
        description: 'Neque assumenda expedita dolor laboriosam.',
        price: 26176563,
        sold: 8893,
        rating: 4.4
      },
      {
        id: '232',
        name: 'Intelligent Leather Gloves',
        description: 'Culpa rem molestias porro facere.',
        price: 75573584,
        sold: 3897,
        rating: 1.5
      },
      {
        id: '233',
        name: 'Gorgeous Wooden Plate',
        description: 'Error quia dolorem nihil ratione at enim.',
        price: 19634625,
        sold: 1752,
        rating: 3.5
      },
      {
        id: '234',
        name: 'Durable Wooden Shirt',
        description: 'At omnis modi aliquam reprehenderit est ab expedita.',
        price: 23576260,
        sold: 1225,
        rating: 2.1
      },
      {
        id: '235',
        name: 'Small Wool Lamp',
        description: 'Ea quis illum recusandae quo nesciunt.',
        price: 27537173,
        sold: 2869,
        rating: 0.5
      },
      {
        id: '236',
        name: 'Sleek Plastic Wallet',
        description: 'Voluptatem libero voluptates magni aut doloribus exercitationem cumque.',
        price: 42866740,
        sold: 1905,
        rating: 3.3
      },
      {
        id: '237',
        name: 'Durable Aluminum Bench',
        description: 'Ipsum libero sint itaque deleniti sunt.',
        price: 48335567,
        sold: 5895,
        rating: 3.4
      },
      {
        id: '238',
        name: 'Practical Leather Gloves',
        description: 'Vel voluptate sit minima totam eum aut.',
        price: 60512472,
        sold: 9605,
        rating: 3.9
      },
      {
        id: '239',
        name: 'Aerodynamic Leather Shoes',
        description: 'Et omnis ab incidunt odit.',
        price: 15790394,
        sold: 9356,
        rating: 3.5
      },
      {
        id: '240',
        name: 'Enormous Steel Keyboard',
        description: 'Voluptate quia incidunt molestias.',
        price: 1908479,
        sold: 2396,
        rating: 2.2
      },
      {
        id: '241',
        name: 'Small Cotton Wallet',
        description: 'Quod reprehenderit nisi qui autem labore quo.',
        price: 34347938,
        sold: 4715,
        rating: 3.4
      },
      {
        id: '242',
        name: 'Durable Copper Table',
        description: 'Ut expedita temporibus eos adipisci rerum eius.',
        price: 16354789,
        sold: 9232,
        rating: 0.7
      },
      {
        id: '243',
        name: 'Intelligent Steel Computer',
        description: 'Reiciendis sit voluptas quae et laudantium voluptate consequatur.',
        price: 28004347,
        sold: 7741,
        rating: 2.8
      },
      {
        id: '244',
        name: 'Practical Wool Car',
        description: 'Iusto sapiente est architecto esse occaecati vitae sunt.',
        price: 74132278,
        sold: 8462,
        rating: 4.1
      },
      {
        id: '245',
        name: 'Heavy Duty Plastic Wallet',
        description: 'Aperiam distinctio odit molestiae ipsa sapiente dolorum.',
        price: 10369935,
        sold: 5151,
        rating: 0.2
      },
      {
        id: '246',
        name: 'Rustic Linen Car',
        description: 'Aut libero mollitia non.',
        price: 27345408,
        sold: 1068,
        rating: 0.8
      },
      {
        id: '247',
        name: 'Enormous Wooden Bottle',
        description: 'Doloremque minus officia.',
        price: 46901062,
        sold: 4126,
        rating: 4.2
      },
      {
        id: '248',
        name: 'Intelligent Linen Wallet',
        description: 'Expedita neque beatae tempore et voluptas velit.',
        price: 38744580,
        sold: 6913,
        rating: 2.9
      },
      {
        id: '249',
        name: 'Ergonomic Wool Table',
        description: 'Eos omnis dolorem occaecati recusandae et at labore.',
        price: 2234723,
        sold: 8303,
        rating: 2.5
      },
      {
        id: '250',
        name: 'Aerodynamic Iron Lamp',
        description: 'Quasi tempore iste consequatur unde necessitatibus doloremque.',
        price: 30696989,
        sold: 3447,
        rating: 0.9
      },
      {
        id: '251',
        name: 'Gorgeous Wool Bench',
        description: 'Iste sapiente animi possimus est dolorum qui quod.',
        price: 9376291,
        sold: 8692,
        rating: 0.6
      },
      {
        id: '252',
        name: 'Fantastic Concrete Chair',
        description: 'Quasi sed impedit.',
        price: 10043818,
        sold: 7073,
        rating: 0
      },
      {
        id: '253',
        name: 'Fantastic Cotton Keyboard',
        description: 'Dolorem aperiam velit ut dolorem laboriosam impedit.',
        price: 68596229,
        sold: 84,
        rating: 3.7
      },
      {
        id: '254',
        name: 'Small Wooden Computer',
        description: 'Ut esse deleniti.',
        price: 67642061,
        sold: 4035,
        rating: 3.6
      },
      {
        id: '255',
        name: 'Gorgeous Plastic Bench',
        description: 'Ad consectetur voluptas ut unde libero quis.',
        price: 34389081,
        sold: 8360,
        rating: 3.5
      },
      {
        id: '256',
        name: 'Durable Iron Pants',
        description: 'Cupiditate iure consequatur est eligendi et ullam tenetur.',
        price: 22900954,
        sold: 9128,
        rating: 4
      },
      {
        id: '257',
        name: 'Heavy Duty Paper Plate',
        description: 'Suscipit natus aut similique sit adipisci nostrum.',
        price: 37687755,
        sold: 9919,
        rating: 0.8
      },
      {
        id: '258',
        name: 'Intelligent Steel Keyboard',
        description: 'Culpa fugiat natus non corporis quasi veniam.',
        price: 86351546,
        sold: 656,
        rating: 2.9
      },
      {
        id: '259',
        name: 'Practical Aluminum Bag',
        description: 'Esse voluptas asperiores perspiciatis provident vel et cumque.',
        price: 17467274,
        sold: 6951,
        rating: 2.1
      },
      {
        id: '260',
        name: 'Enormous Bronze Plate',
        description: 'Iste asperiores rerum rerum perferendis.',
        price: 80273411,
        sold: 7532,
        rating: 2.3
      },
      {
        id: '261',
        name: 'Durable Paper Chair',
        description: 'Ratione sapiente voluptas aspernatur fugiat velit dolor.',
        price: 55638519,
        sold: 6654,
        rating: 3.7
      },
      {
        id: '262',
        name: 'Enormous Leather Bottle',
        description: 'Aliquam eos dignissimos.',
        price: 59145179,
        sold: 1569,
        rating: 2.6
      },
      {
        id: '263',
        name: 'Sleek Plastic Computer',
        description: 'Quos sunt repudiandae minus eveniet qui.',
        price: 4367568,
        sold: 4582,
        rating: 2.9
      },
      {
        id: '264',
        name: 'Fantastic Copper Pants',
        description: 'Eos ea et asperiores.',
        price: 53040834,
        sold: 1762,
        rating: 3.6
      },
      {
        id: '265',
        name: 'Aerodynamic Iron Car',
        description: 'Reprehenderit aut cum ut voluptatem aut expedita.',
        price: 15271858,
        sold: 4499,
        rating: 4.2
      },
      {
        id: '266',
        name: 'Intelligent Copper Hat',
        description: 'Placeat amet dolorem vero rem illo.',
        price: 55363367,
        sold: 8057,
        rating: 0.3
      },
      {
        id: '267',
        name: 'Incredible Cotton Computer',
        description: 'Rerum ad consequatur sed vero.',
        price: 53784588,
        sold: 7716,
        rating: 4
      },
      {
        id: '268',
        name: 'Enormous Paper Lamp',
        description: 'Consectetur enim reprehenderit et sit amet et exercitationem.',
        price: 83933743,
        sold: 9574,
        rating: 3.5
      },
      {
        id: '269',
        name: 'Gorgeous Linen Bag',
        description: 'Quod suscipit eum.',
        price: 26391454,
        sold: 7215,
        rating: 1.3
      },
      {
        id: '270',
        name: 'Fantastic Wooden Clock',
        description: 'Consequatur corporis dolorum sapiente.',
        price: 67554234,
        sold: 6650,
        rating: 3.6
      },
      {
        id: '271',
        name: 'Awesome Marble Wallet',
        description: 'Placeat voluptatibus et ea facere quia repudiandae.',
        price: 46736150,
        sold: 1148,
        rating: 1.6
      },
      {
        id: '272',
        name: 'Aerodynamic Cotton Pants',
        description: 'Doloremque rem veritatis vitae.',
        price: 9511071,
        sold: 2285,
        rating: 0.1
      },
      {
        id: '273',
        name: 'Aerodynamic Wool Watch',
        description: 'Doloremque vel ullam qui ea ea quae.',
        price: 61650909,
        sold: 9655,
        rating: 0.9
      },
      {
        id: '274',
        name: 'Practical Steel Chair',
        description: 'Quia suscipit sunt laborum quia qui quis quos.',
        price: 43145913,
        sold: 9699,
        rating: 3.3
      },
      {
        id: '275',
        name: 'Practical Silk Chair',
        description: 'Consequuntur consectetur doloremque.',
        price: 27131631,
        sold: 2197,
        rating: 3.5
      },
      {
        id: '276',
        name: 'Incredible Marble Clock',
        description: 'Ut quo repellat vitae adipisci.',
        price: 4782310,
        sold: 8534,
        rating: 3.8
      },
      {
        id: '277',
        name: 'Aerodynamic Leather Watch',
        description: 'Veritatis vel soluta deserunt quaerat velit minus excepturi.',
        price: 38795431,
        sold: 7050,
        rating: 1.9
      },
      {
        id: '278',
        name: 'Heavy Duty Linen Watch',
        description: 'Nihil doloribus repudiandae aut.',
        price: 66482720,
        sold: 469,
        rating: 1.3
      },
      {
        id: '279',
        name: 'Durable Paper Coat',
        description: 'Dolore ut aliquam suscipit quo natus molestiae omnis.',
        price: 33708048,
        sold: 1234,
        rating: 0.1
      },
      {
        id: '280',
        name: 'Gorgeous Paper Hat',
        description: 'Labore reiciendis praesentium et.',
        price: 78012932,
        sold: 2555,
        rating: 1
      },
      {
        id: '281',
        name: 'Sleek Iron Bag',
        description: 'Adipisci non harum non.',
        price: 39526294,
        sold: 2263,
        rating: 3.9
      },
      {
        id: '282',
        name: 'Ergonomic Silk Chair',
        description: 'Est eligendi aut.',
        price: 31092056,
        sold: 8341,
        rating: 0.1
      },
      {
        id: '283',
        name: 'Heavy Duty Leather Bench',
        description: 'Ut dignissimos dolorem eos.',
        price: 55066948,
        sold: 862,
        rating: 2
      },
      {
        id: '284',
        name: 'Intelligent Bronze Gloves',
        description: 'Doloribus maiores suscipit laudantium autem et.',
        price: 13991908,
        sold: 8177,
        rating: 4
      },
      {
        id: '285',
        name: 'Intelligent Steel Chair',
        description: 'Nobis quia placeat nesciunt ab ipsam est.',
        price: 87554517,
        sold: 7105,
        rating: 2.5
      },
      {
        id: '286',
        name: 'Fantastic Copper Clock',
        description: 'Et aut reprehenderit recusandae sed aut rem quo.',
        price: 64292778,
        sold: 5411,
        rating: 1.4
      },
      {
        id: '287',
        name: 'Awesome Concrete Bag',
        description: 'Est est facere consectetur aut est.',
        price: 4683337,
        sold: 8738,
        rating: 3.8
      },
      {
        id: '288',
        name: 'Rustic Wool Car',
        description: 'Qui debitis exercitationem.',
        price: 32445111,
        sold: 2510,
        rating: 3.7
      },
      {
        id: '289',
        name: 'Ergonomic Rubber Watch',
        description: 'Nihil quia et libero recusandae magnam.',
        price: 28417023,
        sold: 3445,
        rating: 2
      },
      {
        id: '290',
        name: 'Ergonomic Cotton Car',
        description: 'Neque doloremque et illo sint.',
        price: 27253737,
        sold: 2462,
        rating: 3.2
      },
      {
        id: '291',
        name: 'Gorgeous Wool Lamp',
        description: 'Ut expedita libero nisi corrupti debitis quasi culpa.',
        price: 19298536,
        sold: 2798,
        rating: 3.7
      },
      {
        id: '292',
        name: 'Heavy Duty Concrete Plate',
        description: 'Libero pariatur velit tenetur.',
        price: 1619905,
        sold: 8917,
        rating: 0.4
      },
      {
        id: '293',
        name: 'Synergistic Cotton Bag',
        description: 'Hic eum eligendi molestias.',
        price: 5185023,
        sold: 7655,
        rating: 1.9
      },
      {
        id: '294',
        name: 'Aerodynamic Iron Keyboard',
        description: 'Dolore et officia voluptatem est illum.',
        price: 23147975,
        sold: 5936,
        rating: 1.9
      },
      {
        id: '295',
        name: 'Fantastic Wooden Bottle',
        description: 'Accusantium aut labore minima aspernatur ut quas.',
        price: 42949362,
        sold: 5505,
        rating: 2.1
      },
      {
        id: '296',
        name: 'Gorgeous Copper Table',
        description: 'Molestiae culpa accusamus sunt aut nostrum deserunt et.',
        price: 61481514,
        sold: 7962,
        rating: 4
      },
      {
        id: '297',
        name: 'Durable Concrete Shirt',
        description: 'Doloribus porro itaque dolores nobis odio.',
        price: 40322776,
        sold: 3238,
        rating: 1.1
      },
      {
        id: '298',
        name: 'Aerodynamic Iron Bottle',
        description: 'Consequatur minima est.',
        price: 48010100,
        sold: 2273,
        rating: 1.7
      },
      {
        id: '299',
        name: 'Mediocre Linen Shirt',
        description: 'Et laboriosam rerum qui autem possimus quibusdam.',
        price: 75225397,
        sold: 6815,
        rating: 4.2
      },
      {
        id: '300',
        name: 'Lightweight Plastic Bag',
        description: 'Consequatur ut aut quia ab reiciendis.',
        price: 36165070,
        sold: 290,
        rating: 4.1
      },
      {
        id: '301',
        name: 'Practical Plastic Coat',
        description: 'Voluptatem unde et voluptas impedit dicta repellendus.',
        price: 2107560,
        sold: 9701,
        rating: 3.7
      },
      {
        id: '302',
        name: 'Heavy Duty Leather Table',
        description: 'Aliquam dignissimos eligendi quia deleniti quibusdam recusandae.',
        price: 54633018,
        sold: 6130,
        rating: 1.6
      },
      {
        id: '303',
        name: 'Fantastic Copper Chair',
        description: 'Consequatur quibusdam magnam dolores.',
        price: 38939207,
        sold: 2331,
        rating: 3.1
      },
      {
        id: '304',
        name: 'Aerodynamic Bronze Knife',
        description: 'Atque sed doloremque.',
        price: 74056908,
        sold: 179,
        rating: 3.2
      },
      {
        id: '305',
        name: 'Fantastic Paper Chair',
        description: 'Fuga non ea occaecati suscipit sequi.',
        price: 79870158,
        sold: 7469,
        rating: 2.3
      },
      {
        id: '306',
        name: 'Lightweight Rubber Bench',
        description: 'Iste quis est molestiae eveniet eligendi.',
        price: 35609917,
        sold: 9586,
        rating: 2.3
      },
      {
        id: '307',
        name: 'Intelligent Leather Bottle',
        description: 'Ipsum sequi aut corrupti iure placeat vero alias.',
        price: 89436900,
        sold: 8037,
        rating: 1.8
      },
      {
        id: '308',
        name: 'Durable Aluminum Bag',
        description: 'Quam rerum necessitatibus incidunt.',
        price: 64127195,
        sold: 1595,
        rating: 3.2
      },
      {
        id: '309',
        name: 'Sleek Leather Pants',
        description: 'At cum non aut.',
        price: 77768874,
        sold: 522,
        rating: 2.2
      },
      {
        id: '310',
        name: 'Mediocre Plastic Watch',
        description: 'Unde nisi commodi.',
        price: 52730155,
        sold: 4897,
        rating: 1.3
      },
      {
        id: '311',
        name: 'Synergistic Concrete Pants',
        description: 'Ullam rerum sit ipsa voluptatibus.',
        price: 63940075,
        sold: 1276,
        rating: 1.5
      },
      {
        id: '312',
        name: 'Practical Leather Watch',
        description: 'Fugiat velit exercitationem accusantium sit est necessitatibus voluptate.',
        price: 68557565,
        sold: 7615,
        rating: 4.4
      },
      {
        id: '313',
        name: 'Incredible Wooden Gloves',
        description: 'Sunt cum et eum aut dolores est.',
        price: 54774788,
        sold: 8849,
        rating: 1.4
      },
      {
        id: '314',
        name: 'Aerodynamic Aluminum Hat',
        description: 'Eius debitis sit pariatur rerum sint.',
        price: 66357723,
        sold: 3972,
        rating: 0.9
      },
      {
        id: '315',
        name: 'Durable Wooden Wallet',
        description: 'Accusantium qui itaque.',
        price: 21782236,
        sold: 8614,
        rating: 0.6
      },
      {
        id: '316',
        name: 'Aerodynamic Wool Wallet',
        description: 'Numquam autem eum vero distinctio illo magnam.',
        price: 75846888,
        sold: 6565,
        rating: 2.9
      },
      {
        id: '317',
        name: 'Intelligent Cotton Bag',
        description: 'Sunt illum quis.',
        price: 4339726,
        sold: 9047,
        rating: 4.3
      },
      {
        id: '318',
        name: 'Practical Aluminum Bottle',
        description: 'Neque quisquam et ipsum omnis.',
        price: 67178543,
        sold: 3515,
        rating: 0.1
      },
      {
        id: '319',
        name: 'Synergistic Copper Car',
        description: 'Unde ut quis et.',
        price: 79247269,
        sold: 37,
        rating: 0.7
      },
      {
        id: '320',
        name: 'Intelligent Plastic Computer',
        description: 'Quos sint sint ut quis vitae.',
        price: 15197644,
        sold: 1133,
        rating: 2.1
      },
      {
        id: '321',
        name: 'Durable Silk Wallet',
        description: 'Eius amet eum officia molestias reiciendis pariatur.',
        price: 68010812,
        sold: 8721,
        rating: 2.5
      },
      {
        id: '322',
        name: 'Intelligent Granite Shirt',
        description: 'Deleniti qui in nulla fugiat corporis rem.',
        price: 28460311,
        sold: 1571,
        rating: 0.7
      },
      {
        id: '323',
        name: 'Intelligent Wooden Wallet',
        description: 'Architecto magni et qui.',
        price: 8939053,
        sold: 2321,
        rating: 2.6
      },
      {
        id: '324',
        name: 'Sleek Leather Shirt',
        description: 'Iusto aut delectus atque dolor.',
        price: 49495287,
        sold: 5352,
        rating: 2.5
      },
      {
        id: '325',
        name: 'Durable Cotton Chair',
        description: 'Consequuntur aut sit.',
        price: 32142870,
        sold: 9758,
        rating: 1.9
      },
      {
        id: '326',
        name: 'Small Wool Gloves',
        description: 'Exercitationem et nihil et minus pariatur quia nesciunt.',
        price: 50748110,
        sold: 5541,
        rating: 3.3
      },
      {
        id: '327',
        name: 'Small Granite Shirt',
        description: 'Soluta dolor illo sunt aut fugit.',
        price: 61071611,
        sold: 6975,
        rating: 1.7
      },
      {
        id: '328',
        name: 'Intelligent Wooden Lamp',
        description: 'Et ut vel iure est.',
        price: 18843260,
        sold: 2545,
        rating: 2.9
      },
      {
        id: '329',
        name: 'Lightweight Wool Shirt',
        description: 'Qui culpa ut quia magnam omnis omnis modi.',
        price: 76463548,
        sold: 8114,
        rating: 1.6
      },
      {
        id: '330',
        name: 'Synergistic Marble Hat',
        description: 'Rerum consectetur quia.',
        price: 41908548,
        sold: 1582,
        rating: 0.3
      },
      {
        id: '331',
        name: 'Synergistic Linen Gloves',
        description: 'Autem veritatis itaque.',
        price: 35524351,
        sold: 146,
        rating: 0.8
      },
      {
        id: '332',
        name: 'Durable Paper Knife',
        description: 'Consequatur magnam omnis fugiat.',
        price: 64809501,
        sold: 222,
        rating: 4
      },
      {
        id: '333',
        name: 'Lightweight Bronze Car',
        description: 'Aut maxime doloremque necessitatibus quia nostrum hic.',
        price: 40666330,
        sold: 3886,
        rating: 0.4
      },
      {
        id: '334',
        name: 'Durable Plastic Clock',
        description: 'Dolores vel et eius voluptate facere porro aut.',
        price: 2253434,
        sold: 5718,
        rating: 2.7
      },
      {
        id: '335',
        name: 'Enormous Leather Pants',
        description: 'Ratione quisquam atque et consequuntur ut ea.',
        price: 34253711,
        sold: 5525,
        rating: 1.7
      },
      {
        id: '336',
        name: 'Heavy Duty Wooden Table',
        description: 'Quis tempora amet iste dolorem est facere quibusdam.',
        price: 4611164,
        sold: 7657,
        rating: 1.1
      },
      {
        id: '337',
        name: 'Practical Wool Watch',
        description: 'Nobis illo et ea repellendus consectetur.',
        price: 40983275,
        sold: 479,
        rating: 2.8
      },
      {
        id: '338',
        name: 'Ergonomic Copper Wallet',
        description: 'Error corporis consequatur ea.',
        price: 35976754,
        sold: 1950,
        rating: 3
      },
      {
        id: '339',
        name: 'Practical Granite Shirt',
        description: 'Eum voluptatibus explicabo explicabo enim sint voluptatem.',
        price: 82829693,
        sold: 7608,
        rating: 1.2
      },
      {
        id: '340',
        name: 'Awesome Steel Bench',
        description: 'Corrupti saepe atque.',
        price: 30398174,
        sold: 7770,
        rating: 0.1
      },
      {
        id: '341',
        name: 'Incredible Cotton Keyboard',
        description: 'Eligendi corrupti deleniti et libero amet.',
        price: 25729833,
        sold: 604,
        rating: 3.1
      },
      {
        id: '342',
        name: 'Awesome Steel Car',
        description: 'Et et et quis aut.',
        price: 85077153,
        sold: 2527,
        rating: 3.3
      },
      {
        id: '343',
        name: 'Mediocre Aluminum Keyboard',
        description: 'Atque voluptatem voluptas laboriosam esse harum assumenda impedit.',
        price: 58992478,
        sold: 1295,
        rating: 1.6
      },
      {
        id: '344',
        name: 'Durable Paper Watch',
        description: 'Quia pariatur repellat aut saepe repellat quas.',
        price: 49427580,
        sold: 9654,
        rating: 4
      },
      {
        id: '345',
        name: 'Durable Copper Hat',
        description: 'Minima adipisci qui.',
        price: 64964696,
        sold: 7791,
        rating: 3.6
      },
      {
        id: '346',
        name: 'Rustic Cotton Knife',
        description: 'Nam sed mollitia dolorum qui corporis nihil minus.',
        price: 12342642,
        sold: 2089,
        rating: 4.4
      },
      {
        id: '347',
        name: 'Enormous Aluminum Computer',
        description: 'Cupiditate facere nostrum aut.',
        price: 63702687,
        sold: 6910,
        rating: 2.3
      },
      {
        id: '348',
        name: 'Fantastic Bronze Knife',
        description: 'Labore veniam explicabo ullam voluptate quaerat tempora.',
        price: 24337472,
        sold: 307,
        rating: 1.1
      },
      {
        id: '349',
        name: 'Fantastic Wool Clock',
        description: 'Illo vero error.',
        price: 72428359,
        sold: 7849,
        rating: 3.8
      },
      {
        id: '350',
        name: 'Synergistic Aluminum Computer',
        description: 'Hic quos voluptas explicabo.',
        price: 56526730,
        sold: 6184,
        rating: 2.7
      },
      {
        id: '351',
        name: 'Incredible Copper Gloves',
        description: 'Cupiditate provident distinctio rerum molestias.',
        price: 71070155,
        sold: 934,
        rating: 4.1
      },
      {
        id: '352',
        name: 'Heavy Duty Plastic Bench',
        description: 'Eos quia perferendis pariatur laudantium.',
        price: 37588685,
        sold: 6857,
        rating: 2.2
      },
      {
        id: '353',
        name: 'Rustic Aluminum Keyboard',
        description: 'Dolor id non et unde sunt omnis.',
        price: 81471212,
        sold: 2740,
        rating: 3.8
      },
      {
        id: '354',
        name: 'Lightweight Wooden Wallet',
        description: 'Ut illo excepturi nobis quae dolore natus quia.',
        price: 42271885,
        sold: 3885,
        rating: 4.2
      },
      {
        id: '355',
        name: 'Synergistic Aluminum Bench',
        description: 'Animi ad reiciendis tempore necessitatibus quod aut.',
        price: 45703424,
        sold: 9129,
        rating: 1.1
      },
      {
        id: '356',
        name: 'Enormous Copper Keyboard',
        description: 'Expedita doloribus dolorem vel voluptatem sunt quos non.',
        price: 11764256,
        sold: 9049,
        rating: 4.4
      },
      {
        id: '357',
        name: 'Mediocre Paper Lamp',
        description: 'Quis et recusandae velit optio asperiores ut sint.',
        price: 71876026,
        sold: 8660,
        rating: 0.7
      },
      {
        id: '358',
        name: 'Practical Wool Bag',
        description: 'Libero aut laborum iure qui nihil.',
        price: 31844441,
        sold: 9778,
        rating: 4.3
      },
      {
        id: '359',
        name: 'Fantastic Steel Bag',
        description: 'Consequatur molestiae cupiditate blanditiis.',
        price: 44193468,
        sold: 3840,
        rating: 1.1
      },
      {
        id: '360',
        name: 'Fantastic Granite Computer',
        description: 'Omnis eligendi porro quidem deleniti quia.',
        price: 39449190,
        sold: 7034,
        rating: 2.1
      },
      {
        id: '361',
        name: 'Practical Wooden Bottle',
        description: 'Voluptas autem et quia magni amet.',
        price: 37216783,
        sold: 5014,
        rating: 2.7
      },
      {
        id: '362',
        name: 'Sleek Silk Hat',
        description: 'Aut aperiam libero at facilis adipisci.',
        price: 21773693,
        sold: 749,
        rating: 3.2
      },
      {
        id: '363',
        name: 'Mediocre Wooden Keyboard',
        description: 'Eveniet dignissimos molestias officiis aperiam voluptates cupiditate voluptas.',
        price: 46862506,
        sold: 7244,
        rating: 3.4
      },
      {
        id: '364',
        name: 'Durable Granite Lamp',
        description: 'Nam illo voluptas debitis.',
        price: 69159597,
        sold: 9186,
        rating: 3.9
      },
      {
        id: '365',
        name: 'Incredible Silk Knife',
        description: 'Possimus totam sed esse.',
        price: 24201934,
        sold: 3261,
        rating: 1.4
      },
      {
        id: '366',
        name: 'Awesome Steel Computer',
        description: 'Facere voluptas delectus distinctio voluptate dolore eaque beatae.',
        price: 72432904,
        sold: 6859,
        rating: 1.6
      },
      {
        id: '367',
        name: 'Awesome Leather Coat',
        description: 'Doloribus ex est et aut rerum enim.',
        price: 63693701,
        sold: 2934,
        rating: 0.1
      },
      {
        id: '368',
        name: 'Rustic Steel Coat',
        description: 'Nihil quaerat dolorem qui similique.',
        price: 86335763,
        sold: 5079,
        rating: 1.9
      },
      {
        id: '369',
        name: 'Fantastic Linen Car',
        description: 'Qui vel velit asperiores et.',
        price: 1211467,
        sold: 5477,
        rating: 3.1
      },
      {
        id: '370',
        name: 'Mediocre Aluminum Coat',
        description: 'Voluptate rem suscipit veniam.',
        price: 16385212,
        sold: 9734,
        rating: 1.4
      },
      {
        id: '371',
        name: 'Durable Copper Watch',
        description: 'Repudiandae ut vitae sed et ex repellat autem.',
        price: 3178286,
        sold: 2601,
        rating: 0.3
      },
      {
        id: '372',
        name: 'Synergistic Linen Bench',
        description: 'Consequatur ut eveniet assumenda delectus mollitia.',
        price: 20600156,
        sold: 1023,
        rating: 2.7
      },
      {
        id: '373',
        name: 'Synergistic Plastic Pants',
        description: 'Ut reiciendis laborum sit voluptas aut.',
        price: 47429763,
        sold: 2881,
        rating: 3
      },
      {
        id: '374',
        name: 'Heavy Duty Bronze Plate',
        description: 'Perspiciatis corporis facilis cum eum ipsam sunt aut.',
        price: 57197979,
        sold: 4016,
        rating: 3
      },
      {
        id: '375',
        name: 'Lightweight Silk Knife',
        description: 'In praesentium nobis dolor.',
        price: 9038979,
        sold: 3751,
        rating: 0.8
      },
      {
        id: '376',
        name: 'Heavy Duty Leather Shirt',
        description: 'Id unde id dolore minus veritatis quae.',
        price: 52621128,
        sold: 6670,
        rating: 2
      },
      {
        id: '377',
        name: 'Ergonomic Linen Computer',
        description: 'Exercitationem blanditiis dolorum et magnam.',
        price: 81552591,
        sold: 2696,
        rating: 1.9
      },
      {
        id: '378',
        name: 'Synergistic Copper Computer',
        description: 'Aliquam dolorem et.',
        price: 27392978,
        sold: 2112,
        rating: 3.4
      },
      {
        id: '379',
        name: 'Durable Concrete Knife',
        description: 'Nostrum laudantium quo numquam earum.',
        price: 19367921,
        sold: 8216,
        rating: 3.6
      },
      {
        id: '380',
        name: 'Awesome Concrete Chair',
        description: 'Blanditiis debitis dignissimos veniam velit.',
        price: 60455018,
        sold: 8093,
        rating: 0.2
      },
      {
        id: '381',
        name: 'Heavy Duty Iron Knife',
        description: 'Et nihil eligendi corporis dolorum odio sit quis.',
        price: 25511208,
        sold: 282,
        rating: 0.2
      },
      {
        id: '382',
        name: 'Enormous Cotton Plate',
        description: 'Veritatis repudiandae consequatur magni quod.',
        price: 2267410,
        sold: 1563,
        rating: 4.2
      },
      {
        id: '383',
        name: 'Synergistic Concrete Clock',
        description: 'Nobis atque occaecati.',
        price: 19678901,
        sold: 2218,
        rating: 3.3
      },
      {
        id: '384',
        name: 'Intelligent Paper Watch',
        description: 'Neque sit qui dolor fugit nostrum.',
        price: 56273906,
        sold: 587,
        rating: 3.2
      },
      {
        id: '385',
        name: 'Small Copper Plate',
        description: 'Omnis et aut.',
        price: 81103401,
        sold: 1202,
        rating: 3.9
      },
      {
        id: '386',
        name: 'Aerodynamic Aluminum Watch',
        description: 'Amet explicabo itaque eos hic quam.',
        price: 43778195,
        sold: 6622,
        rating: 2.5
      },
      {
        id: '387',
        name: 'Heavy Duty Aluminum Car',
        description: 'Asperiores quis in aliquam quos.',
        price: 63054218,
        sold: 8171,
        rating: 2.9
      },
      {
        id: '388',
        name: 'Lightweight Linen Pants',
        description: 'Qui deleniti quia voluptatem voluptas impedit.',
        price: 12005290,
        sold: 5975,
        rating: 3.8
      },
      {
        id: '389',
        name: 'Synergistic Cotton Chair',
        description: 'Sed vel debitis corporis veritatis.',
        price: 89390802,
        sold: 4800,
        rating: 0.7
      },
      {
        id: '390',
        name: 'Enormous Bronze Lamp',
        description: 'Modi quasi minima.',
        price: 25063437,
        sold: 1402,
        rating: 0.1
      },
      {
        id: '391',
        name: 'Gorgeous Bronze Wallet',
        description: 'Eos rerum iusto deserunt rem sit quis officia.',
        price: 11796459,
        sold: 8860,
        rating: 3
      },
      {
        id: '392',
        name: 'Aerodynamic Wooden Coat',
        description: 'Suscipit velit quidem aut non voluptatibus.',
        price: 38291603,
        sold: 7111,
        rating: 3.3
      },
      {
        id: '393',
        name: 'Rustic Wool Chair',
        description: 'Molestiae pariatur maxime autem tempora corrupti in maxime.',
        price: 63135389,
        sold: 1241,
        rating: 3.1
      },
      {
        id: '394',
        name: 'Awesome Concrete Clock',
        description: 'Voluptatum facere minima est sed eos fugit.',
        price: 53452021,
        sold: 2920,
        rating: 2
      },
      {
        id: '395',
        name: 'Incredible Silk Plate',
        description: 'Aliquid velit impedit aut.',
        price: 4137327,
        sold: 7370,
        rating: 1.3
      },
      {
        id: '396',
        name: 'Enormous Wooden Lamp',
        description: 'Ipsa error magni illum facere dolor.',
        price: 45928128,
        sold: 4426,
        rating: 1
      },
      {
        id: '397',
        name: 'Ergonomic Silk Plate',
        description: 'Sint omnis et porro.',
        price: 58547465,
        sold: 59,
        rating: 2.6
      },
      {
        id: '398',
        name: 'Fantastic Bronze Pants',
        description: 'Enim quas quas quae doloribus.',
        price: 68737792,
        sold: 7788,
        rating: 3.7
      },
      {
        id: '399',
        name: 'Gorgeous Steel Pants',
        description: 'Blanditiis inventore laborum velit nulla cumque ut.',
        price: 77104361,
        sold: 25,
        rating: 0.3
      },
      {
        id: '400',
        name: 'Ergonomic Wool Computer',
        description: 'Molestiae autem similique hic praesentium consequuntur voluptates.',
        price: 31427898,
        sold: 6156,
        rating: 4.3
      },
      {
        id: '401',
        name: 'Incredible Silk Gloves',
        description: 'Labore eligendi alias veritatis distinctio neque unde accusamus.',
        price: 44554465,
        sold: 4226,
        rating: 3.3
      },
      {
        id: '402',
        name: 'Ergonomic Granite Car',
        description: 'Explicabo dolores molestias.',
        price: 77873325,
        sold: 3437,
        rating: 1.2
      },
      {
        id: '403',
        name: 'Aerodynamic Wool Chair',
        description: 'Nihil qui quaerat maxime.',
        price: 78765845,
        sold: 273,
        rating: 1.1
      },
      {
        id: '404',
        name: 'Durable Granite Wallet',
        description: 'Dolor perspiciatis corporis nemo aliquid omnis.',
        price: 80673104,
        sold: 8530,
        rating: 1.1
      },
      {
        id: '405',
        name: 'Awesome Marble Clock',
        description: 'Architecto est est beatae non pariatur.',
        price: 51723215,
        sold: 2331,
        rating: 0.9
      },
      {
        id: '406',
        name: 'Gorgeous Iron Bench',
        description: 'Qui dolorem dolores facere labore.',
        price: 81602279,
        sold: 9659,
        rating: 3.3
      },
      {
        id: '407',
        name: 'Practical Granite Car',
        description: 'Explicabo voluptates quisquam enim voluptatem aut saepe libero.',
        price: 25825814,
        sold: 5289,
        rating: 2.4
      },
      {
        id: '408',
        name: 'Enormous Iron Shirt',
        description: 'Dolore sed et.',
        price: 7045320,
        sold: 3907,
        rating: 3.4
      },
      {
        id: '409',
        name: 'Awesome Rubber Lamp',
        description: 'Sed quia totam sunt.',
        price: 77909802,
        sold: 8304,
        rating: 4.2
      },
      {
        id: '410',
        name: 'Sleek Wool Wallet',
        description: 'Vel eum dolorem nihil dolores est molestias.',
        price: 14902012,
        sold: 1078,
        rating: 2.9
      },
      {
        id: '411',
        name: 'Sleek Granite Hat',
        description: 'Alias aut qui earum qui blanditiis et.',
        price: 68724855,
        sold: 8600,
        rating: 1.1
      },
      {
        id: '412',
        name: 'Small Leather Computer',
        description: 'Asperiores est eum repudiandae iusto rem dolor eveniet.',
        price: 79461706,
        sold: 8319,
        rating: 2.8
      },
      {
        id: '413',
        name: 'Enormous Rubber Watch',
        description: 'Illo animi quod nihil.',
        price: 26563560,
        sold: 8464,
        rating: 3.6
      },
      {
        id: '414',
        name: 'Lightweight Leather Lamp',
        description: 'Ratione ipsam necessitatibus.',
        price: 83137692,
        sold: 7669,
        rating: 1.6
      },
      {
        id: '415',
        name: 'Mediocre Leather Chair',
        description: 'Voluptatem aut repellat.',
        price: 6985770,
        sold: 1237,
        rating: 0.8
      },
      {
        id: '416',
        name: 'Gorgeous Steel Shirt',
        description: 'Voluptatum facilis earum occaecati quia fugit.',
        price: 35110702,
        sold: 6018,
        rating: 0.1
      },
      {
        id: '417',
        name: 'Ergonomic Granite Table',
        description: 'Non harum voluptatem quasi ut.',
        price: 74001088,
        sold: 4204,
        rating: 1.4
      },
      {
        id: '418',
        name: 'Sleek Marble Bag',
        description: 'Tenetur vel voluptates.',
        price: 247360,
        sold: 9614,
        rating: 0.6
      },
      {
        id: '419',
        name: 'Small Concrete Knife',
        description: 'Rerum id debitis dolorem est.',
        price: 71139048,
        sold: 7579,
        rating: 2.1
      },
      {
        id: '420',
        name: 'Enormous Aluminum Keyboard',
        description: 'Quod sit qui quia error quisquam dolorem autem.',
        price: 39776553,
        sold: 8953,
        rating: 2.4
      },
      {
        id: '421',
        name: 'Heavy Duty Bronze Clock',
        description: 'Optio optio esse.',
        price: 48289290,
        sold: 5240,
        rating: 3.6
      },
      {
        id: '422',
        name: 'Ergonomic Iron Bottle',
        description: 'Ipsam consequatur aut veniam.',
        price: 14862226,
        sold: 4403,
        rating: 0.6
      },
      {
        id: '423',
        name: 'Gorgeous Plastic Shoes',
        description: 'Voluptatem occaecati non totam sed adipisci voluptatibus est.',
        price: 55326185,
        sold: 8043,
        rating: 0.2
      },
      {
        id: '424',
        name: 'Gorgeous Leather Shirt',
        description: 'Dolorem aut dicta delectus maxime quia.',
        price: 32135295,
        sold: 9441,
        rating: 1.3
      },
      {
        id: '425',
        name: 'Mediocre Wooden Clock',
        description: 'Cumque non numquam.',
        price: 2775535,
        sold: 1602,
        rating: 3.8
      },
      {
        id: '426',
        name: 'Lightweight Granite Keyboard',
        description: 'Cupiditate optio est est corporis.',
        price: 23707896,
        sold: 3994,
        rating: 1.3
      },
      {
        id: '427',
        name: 'Lightweight Bronze Shoes',
        description: 'Est rem doloribus ratione at ab dignissimos fugit.',
        price: 64040422,
        sold: 8156,
        rating: 0.4
      },
      {
        id: '428',
        name: 'Mediocre Plastic Wallet',
        description: 'Incidunt harum eius.',
        price: 54528139,
        sold: 6053,
        rating: 3.6
      },
      {
        id: '429',
        name: 'Sleek Iron Keyboard',
        description: 'Recusandae sed rerum iure.',
        price: 48717347,
        sold: 3954,
        rating: 0.6
      },
      {
        id: '430',
        name: 'Incredible Marble Bag',
        description: 'Molestias porro cum quam.',
        price: 28271797,
        sold: 8278,
        rating: 2.8
      },
      {
        id: '431',
        name: 'Heavy Duty Aluminum Car',
        description: 'Officia non quisquam veritatis.',
        price: 74896485,
        sold: 3771,
        rating: 0.7
      },
      {
        id: '432',
        name: 'Mediocre Steel Bench',
        description: 'Commodi debitis in delectus officia aut.',
        price: 5835841,
        sold: 5340,
        rating: 4.3
      },
      {
        id: '433',
        name: 'Awesome Bronze Hat',
        description: 'Eum aut architecto et tempore.',
        price: 64925308,
        sold: 3753,
        rating: 3.2
      },
      {
        id: '434',
        name: 'Ergonomic Paper Chair',
        description: 'Nisi eaque consequatur perferendis vitae est qui.',
        price: 71649417,
        sold: 1329,
        rating: 3.5
      },
      {
        id: '435',
        name: 'Mediocre Concrete Chair',
        description: 'Error recusandae aspernatur aut.',
        price: 4105133,
        sold: 8435,
        rating: 3.3
      },
      {
        id: '436',
        name: 'Lightweight Wooden Hat',
        description: 'Maxime dolorem error dolorum voluptate.',
        price: 32709353,
        sold: 1134,
        rating: 0.8
      },
      {
        id: '437',
        name: 'Heavy Duty Copper Chair',
        description: 'Illum incidunt aut est.',
        price: 30542712,
        sold: 5241,
        rating: 1.4
      },
      {
        id: '438',
        name: 'Aerodynamic Bronze Shirt',
        description: 'Aut consequuntur est optio aut est.',
        price: 21595627,
        sold: 376,
        rating: 1.4
      },
      {
        id: '439',
        name: 'Aerodynamic Paper Wallet',
        description: 'Est sed perferendis voluptatem culpa repellendus aut.',
        price: 80012757,
        sold: 4307,
        rating: 0.1
      },
      {
        id: '440',
        name: 'Heavy Duty Copper Gloves',
        description: 'Voluptatum autem distinctio nihil laudantium omnis iste.',
        price: 6060998,
        sold: 9191,
        rating: 2.4
      },
      {
        id: '441',
        name: 'Rustic Copper Coat',
        description: 'Dolore aut amet at labore.',
        price: 49738050,
        sold: 5101,
        rating: 2.3
      },
      {
        id: '442',
        name: 'Sleek Silk Computer',
        description: 'Distinctio necessitatibus culpa omnis numquam voluptatum.',
        price: 1853044,
        sold: 484,
        rating: 0.4
      },
      {
        id: '443',
        name: 'Durable Marble Table',
        description: 'Maxime et reprehenderit quas et.',
        price: 51018031,
        sold: 2755,
        rating: 4.4
      },
      {
        id: '444',
        name: 'Lightweight Wool Computer',
        description: 'Esse consequatur laudantium et.',
        price: 40021731,
        sold: 4219,
        rating: 1.1
      },
      {
        id: '445',
        name: 'Intelligent Wooden Clock',
        description: 'Aut id molestias.',
        price: 84241939,
        sold: 4641,
        rating: 2.5
      },
      {
        id: '446',
        name: 'Fantastic Copper Bottle',
        description: 'Nobis veniam corporis aut tempore dolor accusantium laborum.',
        price: 44898485,
        sold: 7329,
        rating: 0.8
      },
      {
        id: '447',
        name: 'Incredible Bronze Plate',
        description: 'Labore quasi debitis.',
        price: 36313726,
        sold: 8048,
        rating: 4.1
      },
      {
        id: '448',
        name: 'Lightweight Bronze Chair',
        description: 'Et fuga odio dicta assumenda.',
        price: 22089979,
        sold: 3066,
        rating: 1.8
      },
      {
        id: '449',
        name: 'Heavy Duty Wooden Table',
        description: 'Quia vel et.',
        price: 44917006,
        sold: 6059,
        rating: 1.2
      },
      {
        id: '450',
        name: 'Intelligent Wool Keyboard',
        description: 'Error ut sit ab beatae beatae veniam.',
        price: 36074430,
        sold: 6458,
        rating: 2.7
      },
      {
        id: '451',
        name: 'Mediocre Wooden Clock',
        description: 'Quisquam ut odio minus odit.',
        price: 72272548,
        sold: 2186,
        rating: 3.5
      },
      {
        id: '452',
        name: 'Lightweight Aluminum Watch',
        description: 'Ea dolores eos id dignissimos et natus.',
        price: 49427658,
        sold: 4845,
        rating: 1.6
      },
      {
        id: '453',
        name: 'Synergistic Granite Clock',
        description: 'Ut error placeat.',
        price: 77985730,
        sold: 9315,
        rating: 1.7
      },
      {
        id: '454',
        name: 'Practical Granite Clock',
        description: 'Delectus itaque architecto autem molestiae.',
        price: 1586559,
        sold: 9540,
        rating: 1.3
      },
      {
        id: '455',
        name: 'Ergonomic Plastic Clock',
        description: 'Eos libero corrupti.',
        price: 30854075,
        sold: 9774,
        rating: 3
      },
      {
        id: '456',
        name: 'Practical Marble Car',
        description: 'Libero velit voluptatem odio esse voluptatem.',
        price: 21246586,
        sold: 6932,
        rating: 2.4
      },
      {
        id: '457',
        name: 'Intelligent Bronze Clock',
        description: 'Est voluptatem et esse nulla.',
        price: 2593559,
        sold: 1154,
        rating: 1.6
      },
      {
        id: '458',
        name: 'Durable Cotton Table',
        description: 'Soluta occaecati consequatur quod non architecto consequuntur sed.',
        price: 30385377,
        sold: 385,
        rating: 1.2
      },
      {
        id: '459',
        name: 'Small Silk Car',
        description: 'Qui quia molestias accusamus.',
        price: 89984023,
        sold: 7656,
        rating: 3.9
      },
      {
        id: '460',
        name: 'Lightweight Paper Table',
        description: 'Eum est non enim expedita.',
        price: 7450170,
        sold: 2098,
        rating: 0.8
      },
      {
        id: '461',
        name: 'Synergistic Wooden Car',
        description: 'Labore dolores delectus consequatur nihil est quo.',
        price: 24066123,
        sold: 3138,
        rating: 2.3
      },
      {
        id: '462',
        name: 'Enormous Silk Pants',
        description: 'Nesciunt debitis est.',
        price: 7842965,
        sold: 9710,
        rating: 0.7
      },
      {
        id: '463',
        name: 'Enormous Bronze Shoes',
        description: 'Et libero dignissimos tempore.',
        price: 35474610,
        sold: 4904,
        rating: 0.2
      },
      {
        id: '464',
        name: 'Incredible Aluminum Bag',
        description: 'Voluptas ea quaerat quo et.',
        price: 63937485,
        sold: 5474,
        rating: 2.7
      },
      {
        id: '465',
        name: 'Awesome Rubber Knife',
        description: 'Hic delectus consequuntur dolorum assumenda tenetur eveniet voluptatem.',
        price: 69335884,
        sold: 9514,
        rating: 2.8
      },
      {
        id: '466',
        name: 'Rustic Cotton Bottle',
        description: 'Laudantium ex qui non saepe incidunt.',
        price: 10489238,
        sold: 9874,
        rating: 2.9
      },
      {
        id: '467',
        name: 'Sleek Leather Clock',
        description: 'Quia et velit repellat dignissimos quam omnis corrupti.',
        price: 29403859,
        sold: 5262,
        rating: 3.5
      },
      {
        id: '476',
        name: 'Lightweight Wooden Pants',
        description: 'Similique delectus consequatur alias sed.',
        price: 2358369,
        sold: 987,
        rating: 2.9
      },
      {
        id: '477',
        name: 'Aerodynamic Concrete Table',
        description: 'Dignissimos quis possimus natus similique minima iure veritatis.',
        price: 5314032,
        sold: 7516,
        rating: 3.8
      },
      {
        id: '478',
        name: 'Small Wooden Lamp',
        description: 'Et aut suscipit nobis.',
        price: 16908313,
        sold: 6462,
        rating: 1.1
      },
      {
        id: '479',
        name: 'Heavy Duty Steel Clock',
        description: 'Eius nobis enim ipsa eius.',
        price: 58202840,
        sold: 9798,
        rating: 0.9
      },
      {
        id: '480',
        name: 'Ergonomic Rubber Car',
        description: 'Et esse quos rerum voluptatem.',
        price: 77007357,
        sold: 3984,
        rating: 4.1
      },
      {
        id: '481',
        name: 'Intelligent Leather Shirt',
        description: 'Laborum perferendis voluptatem voluptatem consequatur.',
        price: 83641941,
        sold: 3573,
        rating: 2
      },
      {
        id: '482',
        name: 'Lightweight Leather Bag',
        description: 'Id et officiis.',
        price: 2982006,
        sold: 4501,
        rating: 2.6
      },
      {
        id: '483',
        name: 'Rustic Wool Coat',
        description: 'Veritatis eos a quia quia aliquam quisquam aspernatur.',
        price: 9796137,
        sold: 1576,
        rating: 3.9
      },
      {
        id: '484',
        name: 'Rustic Plastic Clock',
        description: 'Consequatur delectus alias autem corporis ab.',
        price: 70677244,
        sold: 9887,
        rating: 0
      },
      {
        id: '485',
        name: 'Incredible Concrete Computer',
        description: 'Veniam veniam tempora in officiis aspernatur dicta.',
        price: 66368724,
        sold: 4241,
        rating: 3.7
      },
      {
        id: '486',
        name: 'Enormous Linen Table',
        description: 'Ea et deserunt.',
        price: 9630723,
        sold: 6983,
        rating: 2.2
      },
      {
        id: '487',
        name: 'Rustic Rubber Computer',
        description: 'Molestiae fugit non voluptatem laboriosam distinctio.',
        price: 68719285,
        sold: 3865,
        rating: 3.4
      },
      {
        id: '488',
        name: 'Small Wool Car',
        description: 'Sapiente quo sint delectus.',
        price: 25162554,
        sold: 1600,
        rating: 4.2
      },
      {
        id: '489',
        name: 'Intelligent Granite Car',
        description: 'Eum ut non.',
        price: 76666562,
        sold: 470,
        rating: 1.4
      },
      {
        id: '490',
        name: 'Incredible Bronze Shoes',
        description: 'Sequi ea quidem id ratione cum earum molestias.',
        price: 54698854,
        sold: 8474,
        rating: 4
      },
      {
        id: '491',
        name: 'Awesome Rubber Bench',
        description: 'Dolores et alias debitis suscipit.',
        price: 58138853,
        sold: 1921,
        rating: 1.7
      },
      {
        id: '492',
        name: 'Small Cotton Bottle',
        description: 'A quia voluptatem eos enim aperiam quae.',
        price: 78621432,
        sold: 6536,
        rating: 2.1
      },
      {
        id: '493',
        name: 'Sleek Aluminum Watch',
        description: 'Voluptatem ea modi adipisci.',
        price: 70724445,
        sold: 8224,
        rating: 0.5
      },
      {
        id: '494',
        name: 'Sleek Aluminum Clock',
        description: 'Numquam cupiditate qui libero velit consectetur.',
        price: 42120018,
        sold: 436,
        rating: 3.3
      },
      {
        id: '495',
        name: 'Aerodynamic Granite Shirt',
        description: 'Tempora atque fugiat.',
        price: 58655906,
        sold: 4218,
        rating: 0.7
      },
      {
        id: '496',
        name: 'Small Wooden Shoes',
        description: 'Fugit incidunt neque sit magni veniam voluptatem commodi.',
        price: 51168292,
        sold: 2234,
        rating: 2
      },
      {
        id: '497',
        name: 'Fantastic Linen Bench',
        description: 'Optio quisquam iure doloribus.',
        price: 14489132,
        sold: 839,
        rating: 2.3
      },
      {
        id: '498',
        name: 'Incredible Leather Plate',
        description: 'Aut consequatur ipsam eaque excepturi nisi.',
        price: 26991816,
        sold: 192,
        rating: 2.9
      },
      {
        id: '499',
        name: 'Sleek Marble Wallet',
        description: 'Sed aperiam odio perferendis.',
        price: 14033360,
        sold: 8908,
        rating: 1.9
      },
      {
        id: '500',
        name: 'Heavy Duty Marble Gloves',
        description: 'Est error minus velit cum occaecati.',
        price: 61727097,
        sold: 9590,
        rating: 2.1
      },
      {
        id: '1',
        name: 'Synergistic Granite Gloves',
        description: 'Voluptatibus vero aliquam.',
        price: 1805971,
        sold: 2008,
        rating: 3.3
      }
    ]
  }
}

export const getAllProductRequest = http.get(`/products?page=1&limit=60`, () => {
  return HttpResponse.json(getAllProductRes)
})

const productRequests = [productsRequest, productDetailRequest, getAllProductRequest]

export default productRequests
