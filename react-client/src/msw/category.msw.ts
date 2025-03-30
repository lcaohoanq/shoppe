import { http, HttpResponse } from 'msw'
import { ApiResponse } from 'src/types/api.type'
import { CategoryResponse } from 'src/types/category.type'

const categoriesRes: ApiResponse<CategoryResponse[]> = {
  message: 'Get all categories successfully',
  data: [
    {
      id: 24,
      name: 'Thời Trang Nam',
      subcategories: [
        {
          id: 153,
          name: 'Áo Khoác',
          created_at: '2024-12-09 22:55:33.306385',
          updated_at: '2024-12-09 22:55:33.306390'
        },
        {
          id: 154,
          name: 'Trang Sức Nam',
          created_at: '2024-12-09 22:55:33.310916',
          updated_at: '2024-12-09 22:55:33.310918'
        },
        {
          id: 155,
          name: 'Quần Jeans',
          created_at: '2024-12-09 22:55:33.313675',
          updated_at: '2024-12-09 22:55:33.313677'
        },
        {
          id: 156,
          name: 'Đồ Lót',
          created_at: '2024-12-09 22:55:33.315976',
          updated_at: '2024-12-09 22:55:33.315978'
        },
        {
          id: 157,
          name: 'Đồ Ngủ',
          created_at: '2024-12-09 22:55:33.318327',
          updated_at: '2024-12-09 22:55:33.318329'
        },
        {
          id: 158,
          name: 'Trang Phục Truyền Thống',
          created_at: '2024-12-09 22:55:33.320512',
          updated_at: '2024-12-09 22:55:33.320513'
        },
        {
          id: 159,
          name: 'Đồ Bộ',
          created_at: '2024-12-09 22:55:33.323017',
          updated_at: '2024-12-09 22:55:33.323019'
        },
        {
          id: 160,
          name: 'Trang Phục Ngành Nghề',
          created_at: '2024-12-09 22:55:33.327428',
          updated_at: '2024-12-09 22:55:33.327430'
        },
        {
          id: 161,
          name: 'Thắt Lưng Nam',
          created_at: '2024-12-09 22:55:33.330782',
          updated_at: '2024-12-09 22:55:33.330784'
        },
        {
          id: 162,
          name: 'Áo Vest và Blazer',
          created_at: '2024-12-09 22:55:33.333073',
          updated_at: '2024-12-09 22:55:33.333075'
        },
        {
          id: 163,
          name: 'Áo',
          created_at: '2024-12-09 22:55:33.335459',
          updated_at: '2024-12-09 22:55:33.335461'
        },
        {
          id: 164,
          name: 'Cà vạt & Nơ cổ',
          created_at: '2024-12-09 22:55:33.337874',
          updated_at: '2024-12-09 22:55:33.337876'
        },
        {
          id: 165,
          name: 'Quần Short',
          created_at: '2024-12-09 22:55:33.340339',
          updated_at: '2024-12-09 22:55:33.340341'
        },
        {
          id: 166,
          name: 'Vớ/Tất',
          created_at: '2024-12-09 22:55:33.344320',
          updated_at: '2024-12-09 22:55:33.344323'
        },
        {
          id: 167,
          name: 'Kính Mắt Nam',
          created_at: '2024-12-09 22:55:33.347364',
          updated_at: '2024-12-09 22:55:33.347366'
        },
        {
          id: 168,
          name: 'Áo Hoodie, Áo Len & Áo Nỉ',
          created_at: '2024-12-09 22:55:33.349926',
          updated_at: '2024-12-09 22:55:33.349928'
        },
        {
          id: 169,
          name: 'Áo Ba Lỗ',
          created_at: '2024-12-09 22:55:33.352571',
          updated_at: '2024-12-09 22:55:33.352573'
        },
        {
          id: 170,
          name: 'Phụ Kiện Nam',
          created_at: '2024-12-09 22:55:33.355064',
          updated_at: '2024-12-09 22:55:33.355066'
        },
        {
          id: 171,
          name: 'Đồ Hóa Trang',
          created_at: '2024-12-09 22:55:33.357697',
          updated_at: '2024-12-09 22:55:33.357699'
        },
        {
          id: 172,
          name: 'Khác',
          created_at: '2024-12-09 22:55:33.372141',
          updated_at: '2024-12-09 22:55:33.372146'
        },
        {
          id: 173,
          name: 'Quần Dài/Quần Âu',
          created_at: '2024-12-09 22:55:33.375359',
          updated_at: '2024-12-09 22:55:33.375361'
        }
      ]
    },
    {
      id: 25,
      name: 'Thời Trang Nữ',
      subcategories: [
        {
          id: 132,
          name: 'Đồ truyền thống',
          created_at: '2024-12-09 22:55:03.394011',
          updated_at: '2024-12-09 22:55:03.394014'
        },
        {
          id: 133,
          name: 'Quần',
          created_at: '2024-12-09 22:55:03.396500',
          updated_at: '2024-12-09 22:55:03.396502'
        },
        {
          id: 134,
          name: 'Quần jeans',
          created_at: '2024-12-09 22:55:03.398726',
          updated_at: '2024-12-09 22:55:03.398728'
        },
        {
          id: 135,
          name: 'Đồ tập',
          created_at: '2024-12-09 22:55:03.401097',
          updated_at: '2024-12-09 22:55:03.401100'
        },
        {
          id: 136,
          name: 'Chân váy',
          created_at: '2024-12-09 22:55:03.403252',
          updated_at: '2024-12-09 22:55:03.403253'
        },
        {
          id: 137,
          name: 'Đầm/Váy',
          created_at: '2024-12-09 22:55:03.406937',
          updated_at: '2024-12-09 22:55:03.406942'
        },
        {
          id: 138,
          name: 'Đồ lót',
          created_at: '2024-12-09 22:55:03.409798',
          updated_at: '2024-12-09 22:55:03.409800'
        },
        {
          id: 139,
          name: 'Đồ ngủ',
          created_at: '2024-12-09 22:55:03.412138',
          updated_at: '2024-12-09 22:55:03.412139'
        },
        {
          id: 140,
          name: 'Hoodie và Áo nỉ',
          created_at: '2024-12-09 22:55:03.414558',
          updated_at: '2024-12-09 22:55:03.414560'
        },
        {
          id: 141,
          name: 'Đồ bầu',
          created_at: '2024-12-09 22:55:03.416865',
          updated_at: '2024-12-09 22:55:03.416868'
        },
        {
          id: 142,
          name: 'Đồ hóa trang',
          created_at: '2024-12-09 22:55:03.419082',
          updated_at: '2024-12-09 22:55:03.419084'
        },
        {
          id: 143,
          name: 'Áo',
          created_at: '2024-12-09 22:55:03.422501',
          updated_at: '2024-12-09 22:55:03.422504'
        },
        {
          id: 144,
          name: 'Áo khoác, Áo choàng & Vest',
          created_at: '2024-12-09 22:55:03.425436',
          updated_at: '2024-12-09 22:55:03.425438'
        },
        {
          id: 145,
          name: 'Vớ/ Tất',
          created_at: '2024-12-09 22:55:03.427922',
          updated_at: '2024-12-09 22:55:03.427924'
        },
        {
          id: 146,
          name: 'Đồ liền thân',
          created_at: '2024-12-09 22:55:03.430468',
          updated_at: '2024-12-09 22:55:03.430470'
        },
        {
          id: 147,
          name: 'Áo len & Cardigan',
          created_at: '2024-12-09 22:55:03.432956',
          updated_at: '2024-12-09 22:55:03.432958'
        },
        {
          id: 148,
          name: 'Bộ',
          created_at: '2024-12-09 22:55:03.435232',
          updated_at: '2024-12-09 22:55:03.435234'
        },
        {
          id: 149,
          name: 'Quần đùi',
          created_at: '2024-12-09 22:55:03.439351',
          updated_at: '2024-12-09 22:55:03.439353'
        },
        {
          id: 150,
          name: 'Vải',
          created_at: '2024-12-09 22:55:03.442748',
          updated_at: '2024-12-09 22:55:03.442750'
        },
        {
          id: 151,
          name: 'Khác',
          created_at: '2024-12-09 22:55:03.445432',
          updated_at: '2024-12-09 22:55:03.445434'
        },
        {
          id: 152,
          name: 'Váy cưới',
          created_at: '2024-12-09 22:55:03.448114',
          updated_at: '2024-12-09 22:55:03.448116'
        }
      ]
    },
    {
      id: 26,
      name: 'Điện Thoại & Phụ Kiện',
      subcategories: [
        {
          id: 121,
          name: 'Đế giữ điện thoại',
          created_at: '2024-12-09 22:54:25.223589',
          updated_at: '2024-12-09 22:54:25.223591'
        },
        {
          id: 122,
          name: 'Điện thoại',
          created_at: '2024-12-09 22:54:25.226286',
          updated_at: '2024-12-09 22:54:25.226288'
        },
        {
          id: 123,
          name: 'Ốp lưng, bao da, Miếng dán điện thoại',
          created_at: '2024-12-09 22:54:25.228406',
          updated_at: '2024-12-09 22:54:25.228408'
        },
        {
          id: 124,
          name: 'Sim',
          created_at: '2024-12-09 22:54:25.230998',
          updated_at: '2024-12-09 22:54:25.231000'
        },
        {
          id: 125,
          name: 'Thẻ nhớ',
          created_at: '2024-12-09 22:54:25.233685',
          updated_at: '2024-12-09 22:54:25.233687'
        },
        {
          id: 126,
          name: 'Pin Dự Phòng',
          created_at: '2024-12-09 22:54:25.236048',
          updated_at: '2024-12-09 22:54:25.236050'
        },
        {
          id: 127,
          name: 'Pin Gắn Trong, Cáp và Bộ Sạc',
          created_at: '2024-12-09 22:54:25.240069',
          updated_at: '2024-12-09 22:54:25.240083'
        },
        {
          id: 128,
          name: 'Phụ kiện khác',
          created_at: '2024-12-09 22:54:25.242961',
          updated_at: '2024-12-09 22:54:25.242964'
        },
        {
          id: 129,
          name: 'Bảo vệ màn hình',
          created_at: '2024-12-09 22:54:25.245315',
          updated_at: '2024-12-09 22:54:25.245317'
        },
        {
          id: 130,
          name: 'Thiết bị khác',
          created_at: '2024-12-09 22:54:25.248020',
          updated_at: '2024-12-09 22:54:25.248023'
        },
        {
          id: 131,
          name: 'Máy tính bảng',
          created_at: '2024-12-09 22:54:25.250943',
          updated_at: '2024-12-09 22:54:25.250946'
        }
      ]
    },
    {
      id: 27,
      name: 'Mẹ & Bé',
      subcategories: [
        {
          id: 95,
          name: 'Đồ dùng du lịch cho bé',
          created_at: '2024-12-09 22:52:54.853306',
          updated_at: '2024-12-09 22:52:54.853308'
        },
        {
          id: 96,
          name: 'Sữa công thức trên 0-24 tháng tuổi',
          created_at: '2024-12-09 22:52:54.863280',
          updated_at: '2024-12-09 22:52:54.863282'
        },
        {
          id: 97,
          name: 'Thực phẩm cho bé',
          created_at: '2024-12-09 22:52:54.866371',
          updated_at: '2024-12-09 22:52:54.866373'
        },
        {
          id: 98,
          name: 'Đồ chơi',
          created_at: '2024-12-09 22:52:54.869324',
          updated_at: '2024-12-09 22:52:54.869326'
        },
        {
          id: 99,
          name: 'Phụ kiện cho mẹ',
          created_at: '2024-12-09 22:52:54.872885',
          updated_at: '2024-12-09 22:52:54.872888'
        },
        {
          id: 100,
          name: 'Đồ dùng ăn dặm cho bé',
          created_at: '2024-12-09 22:52:54.877488',
          updated_at: '2024-12-09 22:52:54.877491'
        },
        {
          id: 101,
          name: 'An toàn cho bé',
          created_at: '2024-12-09 22:52:54.880024',
          updated_at: '2024-12-09 22:52:54.880027'
        },
        {
          id: 102,
          name: 'Tã & bô em bé',
          created_at: '2024-12-09 22:52:54.882704',
          updated_at: '2024-12-09 22:52:54.882706'
        },
        {
          id: 103,
          name: 'Chăm sóc sức khỏe mẹ',
          created_at: '2024-12-09 22:52:54.885476',
          updated_at: '2024-12-09 22:52:54.885478'
        },
        {
          id: 104,
          name: 'Đồ dùng phòng tắm & Chăm sóc cơ thể bé',
          created_at: '2024-12-09 22:52:54.889619',
          updated_at: '2024-12-09 22:52:54.889627'
        },
        {
          id: 105,
          name: 'Đồ dùng phòng ngủ cho bé',
          created_at: '2024-12-09 22:52:54.893351',
          updated_at: '2024-12-09 22:52:54.893353'
        },
        {
          id: 106,
          name: 'Chăm sóc sức khỏe bé',
          created_at: '2024-12-09 22:52:54.896388',
          updated_at: '2024-12-09 22:52:54.896391'
        },
        {
          id: 107,
          name: 'Sữa công thức trên 24 tháng',
          created_at: '2024-12-09 22:52:54.899167',
          updated_at: '2024-12-09 22:52:54.899169'
        },
        {
          id: 108,
          name: 'Khác',
          created_at: '2024-12-09 22:52:54.901868',
          updated_at: '2024-12-09 22:52:54.901870'
        },
        {
          id: 109,
          name: 'Bộ & Gói quà tặng',
          created_at: '2024-12-09 22:52:54.905451',
          updated_at: '2024-12-09 22:52:54.905454'
        }
      ]
    },
    {
      id: 28,
      name: 'Thiết Bị Điện Tử',
      subcategories: [
        {
          id: 110,
          name: 'Headphones',
          created_at: '2024-12-09 22:53:39.962322',
          updated_at: '2024-12-09 22:53:39.962324'
        },
        {
          id: 111,
          name: 'Thiết bị đeo thông minh',
          created_at: '2024-12-09 22:53:39.966892',
          updated_at: '2024-12-09 22:53:39.966895'
        },
        {
          id: 112,
          name: 'Tai nghe nhét tai',
          created_at: '2024-12-09 22:53:39.969342',
          updated_at: '2024-12-09 22:53:39.969344'
        },
        {
          id: 113,
          name: 'Máy Game Console',
          created_at: '2024-12-09 22:53:39.971843',
          updated_at: '2024-12-09 22:53:39.971845'
        },
        {
          id: 114,
          name: 'Tivi Box',
          created_at: '2024-12-09 22:53:39.974251',
          updated_at: '2024-12-09 22:53:39.974254'
        },
        {
          id: 115,
          name: 'Đĩa game',
          created_at: '2024-12-09 22:53:39.976269',
          updated_at: '2024-12-09 22:53:39.976271'
        },
        {
          id: 116,
          name: 'Linh phụ kiện',
          created_at: '2024-12-09 22:53:39.979196',
          updated_at: '2024-12-09 22:53:39.979199'
        },
        {
          id: 117,
          name: 'Phụ kiện Console',
          created_at: '2024-12-09 22:53:39.982877',
          updated_at: '2024-12-09 22:53:39.982880'
        },
        {
          id: 118,
          name: 'Phụ kiện tivi',
          created_at: '2024-12-09 22:53:39.985337',
          updated_at: '2024-12-09 22:53:39.985339'
        },
        {
          id: 119,
          name: 'Loa',
          created_at: '2024-12-09 22:53:39.987699',
          updated_at: '2024-12-09 22:53:39.987702'
        },
        {
          id: 120,
          name: 'Tivi',
          created_at: '2024-12-09 22:53:39.990519',
          updated_at: '2024-12-09 22:53:39.990523'
        }
      ]
    },
    {
      id: 29,
      name: 'Nhà Cửa & Đời Sống',
      subcategories: [
        {
          id: 174,
          name: 'Đồ trang trí tiệc',
          created_at: '2024-12-11 09:46:50.467083',
          updated_at: '2024-12-11 09:46:50.467095'
        },
        {
          id: 175,
          name: 'Dụng cụ pha chế',
          created_at: '2024-12-11 09:46:50.498426',
          updated_at: '2024-12-11 09:46:50.498429'
        },
        {
          id: 176,
          name: 'Đồ dùng nhà bếp và hộp đựng thực phẩm',
          created_at: '2024-12-11 09:46:50.503277',
          updated_at: '2024-12-11 09:46:50.503281'
        },
        {
          id: 177,
          name: 'Vật phẩm thờ cúng',
          created_at: '2024-12-11 09:46:50.508357',
          updated_at: '2024-12-11 09:46:50.508360'
        },
        {
          id: 178,
          name: 'Chăm sóc nhà cửa và giặt ủi',
          created_at: '2024-12-11 09:46:50.512303',
          updated_at: '2024-12-11 09:46:50.512305'
        },
        {
          id: 179,
          name: 'Ngoài trời & Sân vườn',
          created_at: '2024-12-11 09:46:50.515745',
          updated_at: '2024-12-11 09:46:50.515748'
        },
        {
          id: 180,
          name: 'Đồ dùng phòng tắm',
          created_at: '2024-12-11 09:46:50.521658',
          updated_at: '2024-12-11 09:46:50.521662'
        },
        {
          id: 181,
          name: 'Đèn',
          created_at: '2024-12-11 09:46:50.525090',
          updated_at: '2024-12-11 09:46:50.525093'
        },
        {
          id: 182,
          name: 'Sắp xếp nhà cửa',
          created_at: '2024-12-11 09:46:50.528352',
          updated_at: '2024-12-11 09:46:50.528355'
        },
        {
          id: 183,
          name: 'Dụng cụ & Thiết bị tiện ích',
          created_at: '2024-12-11 09:46:50.531762',
          updated_at: '2024-12-11 09:46:50.531765'
        },
        {
          id: 184,
          name: 'Chăn, Ga, Gối & Nệm',
          created_at: '2024-12-11 09:46:50.538745',
          updated_at: '2024-12-11 09:46:50.538748'
        },
        {
          id: 185,
          name: 'Trang trí nhà cửa',
          created_at: '2024-12-11 09:46:50.542335',
          updated_at: '2024-12-11 09:46:50.542337'
        },
        {
          id: 186,
          name: 'Đồ dùng phòng ăn',
          created_at: '2024-12-11 09:46:50.545743',
          updated_at: '2024-12-11 09:46:50.545746'
        },
        {
          id: 187,
          name: 'Tinh dầu thơm phòng',
          created_at: '2024-12-11 09:46:50.549062',
          updated_at: '2024-12-11 09:46:50.549065'
        }
      ]
    },
    {
      id: 30,
      name: 'Máy Tính & Laptop',
      subcategories: [
        {
          id: 188,
          name: 'Laptop',
          created_at: '2024-12-11 09:47:32.751963',
          updated_at: '2024-12-11 09:47:32.751967'
        },
        {
          id: 189,
          name: 'Máy Tính Bàn',
          created_at: '2024-12-11 09:47:32.755373',
          updated_at: '2024-12-11 09:47:32.755375'
        },
        {
          id: 190,
          name: 'Thiết Bị Mạng',
          created_at: '2024-12-11 09:47:32.757907',
          updated_at: '2024-12-11 09:47:32.757910'
        },
        {
          id: 191,
          name: 'Thiết Bị Lưu Trữ',
          created_at: '2024-12-11 09:47:32.760730',
          updated_at: '2024-12-11 09:47:32.760733'
        },
        {
          id: 192,
          name: 'Linh Kiện Máy Tính',
          created_at: '2024-12-11 09:47:32.763194',
          updated_at: '2024-12-11 09:47:32.763196'
        },
        {
          id: 193,
          name: 'Màn Hình',
          created_at: '2024-12-11 09:47:32.766464',
          updated_at: '2024-12-11 09:47:32.766468'
        },
        {
          id: 194,
          name: 'Máy In, Máy Scan & Máy Chiếu',
          created_at: '2024-12-11 09:47:32.771410',
          updated_at: '2024-12-11 09:47:32.771412'
        },
        {
          id: 195,
          name: 'Khác',
          created_at: '2024-12-11 09:47:32.774215',
          updated_at: '2024-12-11 09:47:32.774217'
        },
        {
          id: 196,
          name: 'Phụ Kiện Máy Tính',
          created_at: '2024-12-11 09:47:32.777136',
          updated_at: '2024-12-11 09:47:32.777138'
        },
        {
          id: 197,
          name: 'Gaming',
          created_at: '2024-12-11 09:47:32.779729',
          updated_at: '2024-12-11 09:47:32.779732'
        }
      ]
    },
    {
      id: 31,
      name: 'Sắc Đẹp',
      subcategories: [
        {
          id: 198,
          name: 'Chăm sóc tóc',
          created_at: '2024-12-11 09:47:44.560464',
          updated_at: '2024-12-11 09:47:44.560466'
        },
        {
          id: 199,
          name: 'Chăm sóc phụ nữ',
          created_at: '2024-12-11 09:47:44.562875',
          updated_at: '2024-12-11 09:47:44.562879'
        },
        {
          id: 200,
          name: 'Bộ sản phẩm làm đẹp',
          created_at: '2024-12-11 09:47:44.565426',
          updated_at: '2024-12-11 09:47:44.565429'
        },
        {
          id: 201,
          name: 'Vệ sinh răng miệng',
          created_at: '2024-12-11 09:47:44.570767',
          updated_at: '2024-12-11 09:47:44.570770'
        },
        {
          id: 202,
          name: 'Chăm sóc da mặt',
          created_at: '2024-12-11 09:47:44.573912',
          updated_at: '2024-12-11 09:47:44.573916'
        },
        {
          id: 203,
          name: 'Chăm sóc nam giới',
          created_at: '2024-12-11 09:47:44.576794',
          updated_at: '2024-12-11 09:47:44.576797'
        },
        {
          id: 204,
          name: 'Khác',
          created_at: '2024-12-11 09:47:44.579311',
          updated_at: '2024-12-11 09:47:44.579313'
        },
        {
          id: 205,
          name: 'Dụng cụ & Phụ kiện Làm đẹp',
          created_at: '2024-12-11 09:47:44.581748',
          updated_at: '2024-12-11 09:47:44.581751'
        },
        {
          id: 206,
          name: 'Tắm & chăm sóc cở thể',
          created_at: '2024-12-11 09:47:44.585898',
          updated_at: '2024-12-11 09:47:44.585901'
        },
        {
          id: 207,
          name: 'Nước hoa',
          created_at: '2024-12-11 09:47:44.589204',
          updated_at: '2024-12-11 09:47:44.589207'
        },
        {
          id: 208,
          name: 'Trang điểm',
          created_at: '2024-12-11 09:47:44.592399',
          updated_at: '2024-12-11 09:47:44.592402'
        }
      ]
    },
    {
      id: 32,
      name: 'Máy Ảnh & Máy Quay Phim',
      subcategories: [
        {
          id: 215,
          name: 'Camera giám sát & Camera hệ thống',
          created_at: '2024-12-11 09:49:06.138259',
          updated_at: '2024-12-11 09:49:06.138261'
        },
        {
          id: 216,
          name: 'Máy ảnh - Máy quay phim',
          created_at: '2024-12-11 09:49:06.140451',
          updated_at: '2024-12-11 09:49:06.140454'
        },
        {
          id: 217,
          name: 'Thẻ nhớ',
          created_at: '2024-12-11 09:49:06.143077',
          updated_at: '2024-12-11 09:49:06.143079'
        },
        {
          id: 218,
          name: 'Phụ kiện máy ảnh',
          created_at: '2024-12-11 09:49:06.145459',
          updated_at: '2024-12-11 09:49:06.145461'
        },
        {
          id: 219,
          name: 'Máy bay camera & Phụ kiện',
          created_at: '2024-12-11 09:49:06.148742',
          updated_at: '2024-12-11 09:49:06.148746'
        },
        {
          id: 220,
          name: 'Ống kính',
          created_at: '2024-12-11 09:49:06.152511',
          updated_at: '2024-12-11 09:49:06.152514'
        }
      ]
    },
    {
      id: 33,
      name: 'Sức Khỏe',
      subcategories: [
        {
          id: 221,
          name: 'Dụng cụ massage và trị liệu',
          created_at: '2024-12-11 09:49:16.690873',
          updated_at: '2024-12-11 09:49:16.690876'
        },
        {
          id: 222,
          name: 'Chống muỗi & xua đuổi con trùng',
          created_at: '2024-12-11 09:49:16.693217',
          updated_at: '2024-12-11 09:49:16.693219'
        },
        {
          id: 223,
          name: 'Tã người lớn',
          created_at: '2024-12-11 09:49:16.695693',
          updated_at: '2024-12-11 09:49:16.695696'
        },
        {
          id: 224,
          name: 'Hỗ trợ làm đẹp',
          created_at: '2024-12-11 09:49:16.699591',
          updated_at: '2024-12-11 09:49:16.699594'
        },
        {
          id: 225,
          name: 'Vật tư y tế',
          created_at: '2024-12-11 09:49:16.702323',
          updated_at: '2024-12-11 09:49:16.702325'
        },
        {
          id: 226,
          name: 'Thực phẩm chức năng',
          created_at: '2024-12-11 09:49:16.704666',
          updated_at: '2024-12-11 09:49:16.704670'
        },
        {
          id: 227,
          name: 'Hỗ trợ tình dục',
          created_at: '2024-12-11 09:49:16.707030',
          updated_at: '2024-12-11 09:49:16.707033'
        },
        {
          id: 228,
          name: 'Khác',
          created_at: '2024-12-11 09:49:16.709569',
          updated_at: '2024-12-11 09:49:16.709572'
        }
      ]
    },
    {
      id: 34,
      name: 'Đồng Hồ',
      subcategories: [
        {
          id: 209,
          name: 'Đồng Hồ Nam',
          created_at: '2024-12-11 09:48:45.445283',
          updated_at: '2024-12-11 09:48:45.445286'
        },
        {
          id: 210,
          name: 'Đồng Hồ Nữ',
          created_at: '2024-12-11 09:48:45.448108',
          updated_at: '2024-12-11 09:48:45.448110'
        },
        {
          id: 211,
          name: 'Đồng Hồ Trẻ Em',
          created_at: '2024-12-11 09:48:45.452556',
          updated_at: '2024-12-11 09:48:45.452559'
        },
        {
          id: 212,
          name: 'Bộ Đồng Hồ & Đồng Hồ Cặp',
          created_at: '2024-12-11 09:48:45.455387',
          updated_at: '2024-12-11 09:48:45.455389'
        },
        {
          id: 213,
          name: 'Phụ Kiện Đồng Hồ',
          created_at: '2024-12-11 09:48:45.457769',
          updated_at: '2024-12-11 09:48:45.457772'
        },
        {
          id: 214,
          name: 'Khác',
          created_at: '2024-12-11 09:48:45.460030',
          updated_at: '2024-12-11 09:48:45.460032'
        }
      ]
    },
    {
      id: 35,
      name: 'Giày Dép Nữ',
      subcategories: [
        {
          id: 237,
          name: 'Bốt',
          created_at: '2024-12-11 09:50:03.694594',
          updated_at: '2024-12-11 09:50:03.694599'
        },
        {
          id: 238,
          name: 'Giày Đế Bằng',
          created_at: '2024-12-11 09:50:03.698971',
          updated_at: '2024-12-11 09:50:03.698976'
        },
        {
          id: 239,
          name: 'Giày Cao Gót',
          created_at: '2024-12-11 09:50:03.701840',
          updated_at: '2024-12-11 09:50:03.701843'
        },
        {
          id: 240,
          name: 'Giày Đế Xuồng',
          created_at: '2024-12-11 09:50:03.704092',
          updated_at: '2024-12-11 09:50:03.704094'
        },
        {
          id: 241,
          name: 'Xăng-đan Và Dép',
          created_at: '2024-12-11 09:50:03.706591',
          updated_at: '2024-12-11 09:50:03.706593'
        },
        {
          id: 242,
          name: 'Phụ Kiện Giày',
          created_at: '2024-12-11 09:50:03.709391',
          updated_at: '2024-12-11 09:50:03.709395'
        },
        {
          id: 243,
          name: 'Giày Thể Thao/Sneaker',
          created_at: '2024-12-11 09:50:03.712303',
          updated_at: '2024-12-11 09:50:03.712306'
        },
        {
          id: 244,
          name: 'Giày Khác',
          created_at: '2024-12-11 09:50:03.716082',
          updated_at: '2024-12-11 09:50:03.716085'
        }
      ]
    },
    {
      id: 36,
      name: 'Giày Dép Nam',
      subcategories: [
        {
          id: 229,
          name: 'Bốt',
          created_at: '2024-12-11 09:49:46.897005',
          updated_at: '2024-12-11 09:49:46.897012'
        },
        {
          id: 230,
          name: 'Xăng-đan và Dép',
          created_at: '2024-12-11 09:49:46.901292',
          updated_at: '2024-12-11 09:49:46.901294'
        },
        {
          id: 231,
          name: 'Phụ kiện giày dép',
          created_at: '2024-12-11 09:49:46.903668',
          updated_at: '2024-12-11 09:49:46.903671'
        },
        {
          id: 232,
          name: 'Giày Sục',
          created_at: '2024-12-11 09:49:46.906317',
          updated_at: '2024-12-11 09:49:46.906320'
        },
        {
          id: 233,
          name: 'Giày Tây Lười',
          created_at: '2024-12-11 09:49:46.908783',
          updated_at: '2024-12-11 09:49:46.908788'
        },
        {
          id: 234,
          name: 'Khác',
          created_at: '2024-12-11 09:49:46.911133',
          updated_at: '2024-12-11 09:49:46.911136'
        },
        {
          id: 235,
          name: 'Giày Oxfords & Giày Buộc Dây',
          created_at: '2024-12-11 09:49:46.917360',
          updated_at: '2024-12-11 09:49:46.917375'
        },
        {
          id: 236,
          name: 'Giày Thể Thao/Sneaker',
          created_at: '2024-12-11 09:49:46.920762',
          updated_at: '2024-12-11 09:49:46.920764'
        }
      ]
    },
    {
      id: 37,
      name: 'Túi Ví Nữ',
      subcategories: [
        {
          id: 253,
          name: 'Túi Đeo Hông & Túi Đeo Ngực',
          created_at: '2024-12-11 09:51:04.155298',
          updated_at: '2024-12-11 09:51:04.155300'
        },
        {
          id: 254,
          name: 'Túi Đeo Chéo & Túi Đeo Vai',
          created_at: '2024-12-11 09:51:04.157716',
          updated_at: '2024-12-11 09:51:04.157719'
        },
        {
          id: 255,
          name: 'Cặp Laptop',
          created_at: '2024-12-11 09:51:04.160220',
          updated_at: '2024-12-11 09:51:04.160222'
        },
        {
          id: 256,
          name: 'Phụ Kiện Túi',
          created_at: '2024-12-11 09:51:04.163800',
          updated_at: '2024-12-11 09:51:04.163802'
        },
        {
          id: 257,
          name: 'Ví/Bóp Nữ',
          created_at: '2024-12-11 09:51:04.166621',
          updated_at: '2024-12-11 09:51:04.166623'
        },
        {
          id: 258,
          name: 'Túi Quai Xách',
          created_at: '2024-12-11 09:51:04.169138',
          updated_at: '2024-12-11 09:51:04.169140'
        },
        {
          id: 259,
          name: 'Ví Dự Tiệc & Ví Cầm Tay',
          created_at: '2024-12-11 09:51:04.172808',
          updated_at: '2024-12-11 09:51:04.172810'
        },
        {
          id: 260,
          name: 'Túi Tote',
          created_at: '2024-12-11 09:51:04.175428',
          updated_at: '2024-12-11 09:51:04.175430'
        },
        {
          id: 261,
          name: 'Ba Lô Nữ',
          created_at: '2024-12-11 09:51:04.177692',
          updated_at: '2024-12-11 09:51:04.177695'
        },
        {
          id: 262,
          name: 'Khác',
          created_at: '2024-12-11 09:51:04.181647',
          updated_at: '2024-12-11 09:51:04.181650'
        }
      ]
    },
    {
      id: 38,
      name: 'Thiết Bị Điện Gia Dụng',
      subcategories: [
        {
          id: 245,
          name: 'Đồ gia dụng lớn',
          created_at: '2024-12-11 09:50:51.656543',
          updated_at: '2024-12-11 09:50:51.656545'
        },
        {
          id: 246,
          name: 'Quạt & Máy nóng lạnh',
          created_at: '2024-12-11 09:50:51.659103',
          updated_at: '2024-12-11 09:50:51.659105'
        },
        {
          id: 247,
          name: 'Bếp điện',
          created_at: '2024-12-11 09:50:51.663004',
          updated_at: '2024-12-11 09:50:51.663012'
        },
        {
          id: 248,
          name: 'Máy xay, ép, máy đánh trứng trộn bột, máy ,xay thực phẩm',
          created_at: '2024-12-11 09:50:51.666368',
          updated_at: '2024-12-11 09:50:51.666370'
        },
        {
          id: 249,
          name: 'Máy hút bụi & Thiết bị làm sạch',
          created_at: '2024-12-11 09:50:51.668406',
          updated_at: '2024-12-11 09:50:51.668408'
        },
        {
          id: 250,
          name: 'Thiết bị chăm sóc quần áo',
          created_at: '2024-12-11 09:50:51.670746',
          updated_at: '2024-12-11 09:50:51.670748'
        },
        {
          id: 251,
          name: 'Khác',
          created_at: '2024-12-11 09:50:51.673268',
          updated_at: '2024-12-11 09:50:51.673270'
        },
        {
          id: 252,
          name: 'Đồ gia dụng nhà bếp',
          created_at: '2024-12-11 09:50:51.675935',
          updated_at: '2024-12-11 09:50:51.675939'
        }
      ]
    },
    {
      id: 39,
      name: 'Phụ Kiện & Trang Sức Nữ',
      subcategories: [
        {
          id: 263,
          name: 'Kim loại quý',
          created_at: '2024-12-11 09:52:01.657015',
          updated_at: '2024-12-11 09:52:01.657018'
        },
        {
          id: 264,
          name: 'Phụ kiện thêm',
          created_at: '2024-12-11 09:52:01.659405',
          updated_at: '2024-12-11 09:52:01.659407'
        },
        {
          id: 265,
          name: 'Bông tai',
          created_at: '2024-12-11 09:52:01.662808',
          updated_at: '2024-12-11 09:52:01.662811'
        },
        {
          id: 266,
          name: 'Thắt lưng',
          created_at: '2024-12-11 09:52:01.666028',
          updated_at: '2024-12-11 09:52:01.666031'
        },
        {
          id: 267,
          name: 'Cà vạt & Nơ cổ',
          created_at: '2024-12-11 09:52:01.668426',
          updated_at: '2024-12-11 09:52:01.668428'
        },
        {
          id: 268,
          name: 'Kính mắt',
          created_at: '2024-12-11 09:52:01.671247',
          updated_at: '2024-12-11 09:52:01.671250'
        },
        {
          id: 269,
          name: 'Ô/Dù',
          created_at: '2024-12-11 09:52:01.673911',
          updated_at: '2024-12-11 09:52:01.673914'
        },
        {
          id: 270,
          name: 'Bộ phụ kiện',
          created_at: '2024-12-11 09:52:01.676094',
          updated_at: '2024-12-11 09:52:01.676096'
        },
        {
          id: 271,
          name: 'Găng tay',
          created_at: '2024-12-11 09:52:01.680832',
          updated_at: '2024-12-11 09:52:01.680835'
        },
        {
          id: 272,
          name: 'Phụ kiện tóc',
          created_at: '2024-12-11 09:52:01.684451',
          updated_at: '2024-12-11 09:52:01.684454'
        },
        {
          id: 273,
          name: 'Vớ/Tất',
          created_at: '2024-12-11 09:52:01.688287',
          updated_at: '2024-12-11 09:52:01.688289'
        },
        {
          id: 274,
          name: 'Vòng tay & Lắc tay',
          created_at: '2024-12-11 09:52:01.691694',
          updated_at: '2024-12-11 09:52:01.691696'
        },
        {
          id: 275,
          name: 'Nhẫn',
          created_at: '2024-12-11 09:52:01.696434',
          updated_at: '2024-12-11 09:52:01.696437'
        },
        {
          id: 276,
          name: 'Mũ',
          created_at: '2024-12-11 09:52:01.700241',
          updated_at: '2024-12-11 09:52:01.700243'
        },
        {
          id: 277,
          name: 'Khác',
          created_at: '2024-12-11 09:52:01.704368',
          updated_at: '2024-12-11 09:52:01.704371'
        },
        {
          id: 278,
          name: 'Khăn choàng',
          created_at: '2024-12-11 09:52:01.707435',
          updated_at: '2024-12-11 09:52:01.707437'
        },
        {
          id: 279,
          name: 'Dây chuyền',
          created_at: '2024-12-11 09:52:01.710155',
          updated_at: '2024-12-11 09:52:01.710157'
        },
        {
          id: 280,
          name: 'Lắc chân',
          created_at: '2024-12-11 09:52:01.716378',
          updated_at: '2024-12-11 09:52:01.716381'
        }
      ]
    },
    {
      id: 40,
      name: 'Thể Thao & Du Lịch',
      subcategories: [
        {
          id: 281,
          name: 'Vali',
          created_at: '2024-12-11 09:52:36.489371',
          updated_at: '2024-12-11 09:52:36.489373'
        },
        {
          id: 282,
          name: 'Giày Thể Thao',
          created_at: '2024-12-11 09:52:36.491646',
          updated_at: '2024-12-11 09:52:36.491650'
        },
        {
          id: 283,
          name: 'Phụ Kiện Thể Thao & Dã Ngoại',
          created_at: '2024-12-11 09:52:36.495119',
          updated_at: '2024-12-11 09:52:36.495121'
        },
        {
          id: 284,
          name: 'Dụng Cụ Thể Thao & Dã Ngoại',
          created_at: '2024-12-11 09:52:36.498247',
          updated_at: '2024-12-11 09:52:36.498249'
        },
        {
          id: 285,
          name: 'Thời Trang Thể Thao & Dã Ngoại',
          created_at: '2024-12-11 09:52:36.501084',
          updated_at: '2024-12-11 09:52:36.501086'
        },
        {
          id: 286,
          name: 'Phụ kiện du lịch',
          created_at: '2024-12-11 09:52:36.505204',
          updated_at: '2024-12-11 09:52:36.505208'
        },
        {
          id: 287,
          name: 'Khác',
          created_at: '2024-12-11 09:52:36.513634',
          updated_at: '2024-12-11 09:52:36.513641'
        },
        {
          id: 288,
          name: 'Túi du lịch',
          created_at: '2024-12-11 09:52:36.519831',
          updated_at: '2024-12-11 09:52:36.519838'
        }
      ]
    },
    {
      id: 41,
      name: 'Bách Hóa Online',
      subcategories: [
        {
          id: 289,
          name: 'Thực phẩm tươi sống và thực phẩm đông lạnh',
          created_at: '2024-12-11 09:52:48.299858',
          updated_at: '2024-12-11 09:52:48.299860'
        },
        {
          id: 290,
          name: 'Nguyên liệu nấu ăn',
          created_at: '2024-12-11 09:52:48.301815',
          updated_at: '2024-12-11 09:52:48.301824'
        },
        {
          id: 291,
          name: 'Bộ quà tặng',
          created_at: '2024-12-11 09:52:48.303752',
          updated_at: '2024-12-11 09:52:48.303754'
        },
        {
          id: 292,
          name: 'Đồ làm bánh',
          created_at: '2024-12-11 09:52:48.305768',
          updated_at: '2024-12-11 09:52:48.305770'
        },
        {
          id: 293,
          name: 'Đồ uống',
          created_at: '2024-12-11 09:52:48.307997',
          updated_at: '2024-12-11 09:52:48.307999'
        },
        {
          id: 294,
          name: 'Đồ ăn vặt',
          created_at: '2024-12-11 09:52:48.310906',
          updated_at: '2024-12-11 09:52:48.310909'
        },
        {
          id: 295,
          name: 'Ngũ cốc & mứt',
          created_at: '2024-12-11 09:52:48.315020',
          updated_at: '2024-12-11 09:52:48.315023'
        },
        {
          id: 296,
          name: 'Các loại bánh',
          created_at: '2024-12-11 09:52:48.317408',
          updated_at: '2024-12-11 09:52:48.317410'
        },
        {
          id: 297,
          name: 'Đồ uống có cồn',
          created_at: '2024-12-11 09:52:48.319398',
          updated_at: '2024-12-11 09:52:48.319410'
        },
        {
          id: 298,
          name: 'Đồ chế biến sẵn',
          created_at: '2024-12-11 09:52:48.321556',
          updated_at: '2024-12-11 09:52:48.321564'
        },
        {
          id: 299,
          name: 'Khác',
          created_at: '2024-12-11 09:52:48.324074',
          updated_at: '2024-12-11 09:52:48.324076'
        },
        {
          id: 300,
          name: 'Nhu yếu phẩm',
          created_at: '2024-12-11 09:52:48.327080',
          updated_at: '2024-12-11 09:52:48.327086'
        },
        {
          id: 301,
          name: 'Sữa - trứng',
          created_at: '2024-12-11 09:52:48.331319',
          updated_at: '2024-12-11 09:52:48.331340'
        }
      ]
    },
    {
      id: 42,
      name: 'Ô Tô & Xe Máy & Xe Đạp',
      subcategories: [
        {
          id: 308,
          name: 'Phụ kiện xe đạp',
          created_at: '2024-12-11 09:53:31.067664',
          updated_at: '2024-12-11 09:53:31.067665'
        },
        {
          id: 309,
          name: 'Phụ tùng ô tô',
          created_at: '2024-12-11 09:53:31.069857',
          updated_at: '2024-12-11 09:53:31.069858'
        },
        {
          id: 310,
          name: 'Mũ bảo hiểm',
          created_at: '2024-12-11 09:53:31.071648',
          updated_at: '2024-12-11 09:53:31.071648'
        },
        {
          id: 311,
          name: 'Chăm sóc ô tô',
          created_at: '2024-12-11 09:53:31.073394',
          updated_at: '2024-12-11 09:53:31.073395'
        },
        {
          id: 312,
          name: 'Xe đạp, xe điện',
          created_at: '2024-12-11 09:53:31.075884',
          updated_at: '2024-12-11 09:53:31.075885'
        },
        {
          id: 313,
          name: 'Mô tô, xe máy',
          created_at: '2024-12-11 09:53:31.079126',
          updated_at: '2024-12-11 09:53:31.079127'
        },
        {
          id: 314,
          name: 'Phụ kiện bên ngoài ô tô',
          created_at: '2024-12-11 09:53:31.081494',
          updated_at: '2024-12-11 09:53:31.081495'
        },
        {
          id: 315,
          name: 'Phụ kiện xe máy',
          created_at: '2024-12-11 09:53:31.083937',
          updated_at: '2024-12-11 09:53:31.083938'
        },
        {
          id: 316,
          name: 'Phụ tùng xe máy',
          created_at: '2024-12-11 09:53:31.086313',
          updated_at: '2024-12-11 09:53:31.086314'
        },
        {
          id: 317,
          name: 'Dầu nhớt & dầu nhờn',
          created_at: '2024-12-11 09:53:31.088334',
          updated_at: '2024-12-11 09:53:31.088335'
        },
        {
          id: 318,
          name: 'Dịch vụ cho xe',
          created_at: '2024-12-11 09:53:31.090510',
          updated_at: '2024-12-11 09:53:31.090511'
        },
        {
          id: 319,
          name: 'Xe Ô tô',
          created_at: '2024-12-11 09:53:31.093457',
          updated_at: '2024-12-11 09:53:31.093459'
        },
        {
          id: 320,
          name: 'Phụ kiện bên trong ô tô',
          created_at: '2024-12-11 09:53:31.096330',
          updated_at: '2024-12-11 09:53:31.096330'
        }
      ]
    },
    {
      id: 43,
      name: 'Nhà Sách Online',
      subcategories: [
        {
          id: 330,
          name: 'Sách Tiếng Việt',
          created_at: '2024-12-11 09:54:16.346639',
          updated_at: '2024-12-11 09:54:16.346640'
        },
        {
          id: 331,
          name: 'Gói Quà',
          created_at: '2024-12-11 09:54:16.352929',
          updated_at: '2024-12-11 09:54:16.352931'
        },
        {
          id: 332,
          name: 'Sách ngoại văn',
          created_at: '2024-12-11 09:54:16.359221',
          updated_at: '2024-12-11 09:54:16.359223'
        },
        {
          id: 333,
          name: 'Sổ và Giấy Các Loại',
          created_at: '2024-12-11 09:54:16.364208',
          updated_at: '2024-12-11 09:54:16.364208'
        },
        {
          id: 334,
          name: 'Màu, Họa Cụ và Đồ Thủ Công',
          created_at: '2024-12-11 09:54:16.366361',
          updated_at: '2024-12-11 09:54:16.366362'
        },
        {
          id: 335,
          name: 'Nhạc cụ và phụ kiện âm nhạc',
          created_at: '2024-12-11 09:54:16.368257',
          updated_at: '2024-12-11 09:54:16.368257'
        },
        {
          id: 336,
          name: 'Bút viết',
          created_at: '2024-12-11 09:54:16.370229',
          updated_at: '2024-12-11 09:54:16.370230'
        },
        {
          id: 337,
          name: 'Dụng cụ học sinh & văn phòng',
          created_at: '2024-12-11 09:54:16.372474',
          updated_at: '2024-12-11 09:54:16.372475'
        },
        {
          id: 338,
          name: 'Quà Lưu Niệm',
          created_at: '2024-12-11 09:54:16.376334',
          updated_at: '2024-12-11 09:54:16.376334'
        }
      ]
    },
    {
      id: 44,
      name: 'Balo & Túi Ví Nam',
      subcategories: [
        {
          id: 359,
          name: 'Ba Lô Laptop Nam',
          created_at: '2024-12-11 09:55:40.443360',
          updated_at: '2024-12-11 09:55:40.443361'
        },
        {
          id: 360,
          name: 'Túi & Cặp Đựng Laptop',
          created_at: '2024-12-11 09:55:40.445779',
          updated_at: '2024-12-11 09:55:40.445781'
        },
        {
          id: 361,
          name: 'Túi Chống Sốc Laptop Nam',
          created_at: '2024-12-11 09:55:40.447797',
          updated_at: '2024-12-11 09:55:40.447798'
        },
        {
          id: 362,
          name: 'Túi Tote Nam',
          created_at: '2024-12-11 09:55:40.450578',
          updated_at: '2024-12-11 09:55:40.450580'
        },
        {
          id: 363,
          name: 'Bóp/Ví Nam',
          created_at: '2024-12-11 09:55:40.452971',
          updated_at: '2024-12-11 09:55:40.452972'
        },
        {
          id: 364,
          name: 'Cặp Xách Công Sở Nam',
          created_at: '2024-12-11 09:55:40.454817',
          updated_at: '2024-12-11 09:55:40.454818'
        },
        {
          id: 365,
          name: 'Khác',
          created_at: '2024-12-11 09:55:40.458408',
          updated_at: '2024-12-11 09:55:40.458409'
        },
        {
          id: 366,
          name: 'Ví Cầm Tay Nam',
          created_at: '2024-12-11 09:55:40.464752',
          updated_at: '2024-12-11 09:55:40.464754'
        },
        {
          id: 367,
          name: 'Túi Đeo Hông & Túi Đeo Ngực Nam',
          created_at: '2024-12-11 09:55:40.468468',
          updated_at: '2024-12-11 09:55:40.468469'
        },
        {
          id: 368,
          name: 'Ba Lô Nam',
          created_at: '2024-12-11 09:55:40.471540',
          updated_at: '2024-12-11 09:55:40.471541'
        },
        {
          id: 369,
          name: 'Túi Đeo Chéo Nam',
          created_at: '2024-12-11 09:55:40.475378',
          updated_at: '2024-12-11 09:55:40.475379'
        }
      ]
    },
    {
      id: 45,
      name: 'Thời Trang Trẻ Em',
      subcategories: [
        {
          id: 351,
          name: 'Giày tập đi & Tất sơ sinh',
          created_at: '2024-12-11 09:55:14.010830',
          updated_at: '2024-12-11 09:55:14.010832'
        },
        {
          id: 352,
          name: 'Giày dép bé gái',
          created_at: '2024-12-11 09:55:14.013448',
          updated_at: '2024-12-11 09:55:14.013448'
        },
        {
          id: 353,
          name: 'Trang phục bé gái',
          created_at: '2024-12-11 09:55:14.015354',
          updated_at: '2024-12-11 09:55:14.015355'
        },
        {
          id: 354,
          name: 'Trang phục bé trai',
          created_at: '2024-12-11 09:55:14.017431',
          updated_at: '2024-12-11 09:55:14.017432'
        },
        {
          id: 355,
          name: 'Giày dép bé trai',
          created_at: '2024-12-11 09:55:14.019125',
          updated_at: '2024-12-11 09:55:14.019126'
        },
        {
          id: 356,
          name: 'Quần Áo em bé',
          created_at: '2024-12-11 09:55:14.020763',
          updated_at: '2024-12-11 09:55:14.020764'
        },
        {
          id: 357,
          name: 'Khác',
          created_at: '2024-12-11 09:55:14.022785',
          updated_at: '2024-12-11 09:55:14.022786'
        },
        {
          id: 358,
          name: 'Phụ kiện trẻ em',
          created_at: '2024-12-11 09:55:14.026515',
          updated_at: '2024-12-11 09:55:14.026517'
        }
      ]
    },
    {
      id: 46,
      name: 'Đồ Chơi',
      subcategories: [
        {
          id: 302,
          name: 'Búp bê & Đồ chơi nhồi bông',
          created_at: '2024-12-11 09:53:08.206077',
          updated_at: '2024-12-11 09:53:08.206088'
        },
        {
          id: 303,
          name: 'Đồ chiow cho trẻ sơ sinh & trẻ nhỏ',
          created_at: '2024-12-11 09:53:08.208104',
          updated_at: '2024-12-11 09:53:08.208104'
        },
        {
          id: 304,
          name: 'Sở thích & Sưu tầm',
          created_at: '2024-12-11 09:53:08.211353',
          updated_at: '2024-12-11 09:53:08.211353'
        },
        {
          id: 305,
          name: 'Đồ chơi giải trí',
          created_at: '2024-12-11 09:53:08.213851',
          updated_at: '2024-12-11 09:53:08.213852'
        },
        {
          id: 306,
          name: 'Đồ chơi giáo dục',
          created_at: '2024-12-11 09:53:08.216108',
          updated_at: '2024-12-11 09:53:08.216109'
        },
        {
          id: 307,
          name: 'Đồ chơi vận động & ngoài trời',
          created_at: '2024-12-11 09:53:08.218331',
          updated_at: '2024-12-11 09:53:08.218333'
        }
      ]
    },
    {
      id: 47,
      name: 'Giặt Giũ & Chăm Sóc Nhà Cửa',
      subcategories: [
        {
          id: 321,
          name: 'Giấy vệ sinh, khăn giấy',
          created_at: '2024-12-11 09:53:50.263658',
          updated_at: '2024-12-11 09:53:50.263659'
        },
        {
          id: 322,
          name: 'Vệ sinh nhà cửa',
          created_at: '2024-12-11 09:53:50.265777',
          updated_at: '2024-12-11 09:53:50.265778'
        },
        {
          id: 323,
          name: 'Túi, màng bọc thực phẩm',
          created_at: '2024-12-11 09:53:50.267871',
          updated_at: '2024-12-11 09:53:50.267872'
        },
        {
          id: 324,
          name: 'Giặt giũ & Chăm sóc nhà cửa',
          created_at: '2024-12-11 09:53:50.269835',
          updated_at: '2024-12-11 09:53:50.269836'
        },
        {
          id: 325,
          name: 'Thuốc diệt côn trùng',
          created_at: '2024-12-11 09:53:50.271625',
          updated_at: '2024-12-11 09:53:50.271627'
        },
        {
          id: 326,
          name: 'Vệ sinh bát đĩa',
          created_at: '2024-12-11 09:53:50.273603',
          updated_at: '2024-12-11 09:53:50.273604'
        },
        {
          id: 327,
          name: 'Dụng cụ vệ sinh',
          created_at: '2024-12-11 09:53:50.276274',
          updated_at: '2024-12-11 09:53:50.276275'
        },
        {
          id: 328,
          name: 'Chát khử mùi, làm thơm',
          created_at: '2024-12-11 09:53:50.279257',
          updated_at: '2024-12-11 09:53:50.279258'
        },
        {
          id: 329,
          name: 'Bao bì, túi đựng rác',
          created_at: '2024-12-11 09:53:50.281629',
          updated_at: '2024-12-11 09:53:50.281630'
        }
      ]
    },
    {
      id: 48,
      name: 'Chăm Sóc Thú Cưng',
      subcategories: [
        {
          id: 339,
          name: 'Phụ kiện cho thú cưng',
          created_at: '2024-12-11 09:54:22.408772',
          updated_at: '2024-12-11 09:54:22.408774'
        },
        {
          id: 340,
          name: 'Quần áo thú cưng',
          created_at: '2024-12-11 09:54:22.412628',
          updated_at: '2024-12-11 09:54:22.412629'
        },
        {
          id: 341,
          name: 'Thức ăn cho thú cưng',
          created_at: '2024-12-11 09:54:22.414964',
          updated_at: '2024-12-11 09:54:22.414965'
        },
        {
          id: 342,
          name: 'Chăm sóc sức khỏe',
          created_at: '2024-12-11 09:54:22.417399',
          updated_at: '2024-12-11 09:54:22.417400'
        },
        {
          id: 343,
          name: 'Khác',
          created_at: '2024-12-11 09:54:22.419579',
          updated_at: '2024-12-11 09:54:22.419580'
        },
        {
          id: 344,
          name: 'Làm đẹp cho thú cưng',
          created_at: '2024-12-11 09:54:22.421643',
          updated_at: '2024-12-11 09:54:22.421644'
        },
        {
          id: 345,
          name: 'Vệ sinh cho thú cưng',
          created_at: '2024-12-11 09:54:22.424029',
          updated_at: '2024-12-11 09:54:22.424031'
        }
      ]
    },
    {
      id: 49,
      name: 'Voucher & Dịch Vụ',
      subcategories: [
        {
          id: 370,
          name: 'Gọi xe ',
          created_at: '2024-12-11 09:58:33.434835',
          updated_at: '2024-12-11 09:58:33.434836'
        },
        {
          id: 371,
          name: 'Thanh toán hóa đơn',
          created_at: '2024-12-11 09:58:33.437394',
          updated_at: '2024-12-11 09:58:33.437396'
        },
        {
          id: 372,
          name: 'Sức khỏe & Làm đẹp',
          created_at: '2024-12-11 09:58:33.440905',
          updated_at: '2024-12-11 09:58:33.440907'
        },
        {
          id: 373,
          name: 'Du lịch & Khách sạn',
          created_at: '2024-12-11 09:58:33.443192',
          updated_at: '2024-12-11 09:58:33.443193'
        },
        {
          id: 374,
          name: 'Sự kiện & Giải trí',
          created_at: '2024-12-11 09:58:33.445040',
          updated_at: '2024-12-11 09:58:33.445041'
        },
        {
          id: 375,
          name: 'Mã quà tặng Shoppe',
          created_at: '2024-12-11 09:58:33.446921',
          updated_at: '2024-12-11 09:58:33.446922'
        },
        {
          id: 376,
          name: 'Nhà hàng & Ăn uống',
          created_at: '2024-12-11 09:58:33.448992',
          updated_at: '2024-12-11 09:58:33.448993'
        },
        {
          id: 377,
          name: 'Nạp tiền tài khoản',
          created_at: '2024-12-11 09:58:33.450953',
          updated_at: '2024-12-11 09:58:33.450954'
        },
        {
          id: 378,
          name: 'Mua sắm',
          created_at: '2024-12-11 09:58:33.453561',
          updated_at: '2024-12-11 09:58:33.453562'
        },
        {
          id: 379,
          name: 'Khóa học',
          created_at: '2024-12-11 09:58:33.458325',
          updated_at: '2024-12-11 09:58:33.458326'
        },
        {
          id: 380,
          name: 'Dịch vụ khác',
          created_at: '2024-12-11 09:58:33.460905',
          updated_at: '2024-12-11 09:58:33.460906'
        }
      ]
    },
    {
      id: 50,
      name: 'Dụng cụ và thiết bị tiện ích',
      subcategories: [
        {
          id: 346,
          name: 'Dụng cụ điện và thiết bị lớn',
          created_at: '2024-12-11 09:54:52.295737',
          updated_at: '2024-12-11 09:54:52.295738'
        },
        {
          id: 347,
          name: 'Thiết bị mạch điện',
          created_at: '2024-12-11 09:54:52.298366',
          updated_at: '2024-12-11 09:54:52.298367'
        },
        {
          id: 348,
          name: 'Dụng cụ cầm tay',
          created_at: '2024-12-11 09:54:52.302313',
          updated_at: '2024-12-11 09:54:52.302314'
        },
        {
          id: 349,
          name: 'Vật liệu xây dựng',
          created_at: '2024-12-11 09:54:52.305360',
          updated_at: '2024-12-11 09:54:52.305361'
        },
        {
          id: 350,
          name: 'Thiết bị và phụ kiện xây dựng',
          created_at: '2024-12-11 09:54:52.309454',
          updated_at: '2024-12-11 09:54:52.309457'
        }
      ]
    }
  ]
}

export const getAllCategoryRequest = () => {
  return http.get('/api/categories', () => {
    return HttpResponse.json(categoriesRes)
  })
}

const categoryRequests = [getAllCategoryRequest]

export default categoryRequests
