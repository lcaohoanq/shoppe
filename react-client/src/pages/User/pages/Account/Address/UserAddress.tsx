import React, { useEffect, useState } from "react";

type UserAddress = {
  id: number;
  name: string;
  phone: string;
  address: string;
  city: string;
  isDefault: boolean;
};

// Mock API response data
const mockUserAddresses: UserAddress[] = [
  {
    id: 4,
    name: "Nguyễn Thanh Tùng",
    phone: "(+84) 909 723 485",
    address: "42 Đường Nguyễn Huệ, Phường Bến Nghé, Quận 1",
    city: "TP. Hồ Chí Minh",
    isDefault: true,
  },
  {
    id: 5,
    name: "Trần Thị Mai",
    phone: "(+84) 912 345 678",
    address: "103 Đường Lê Lợi, Phường Bến Thành, Quận 1",
    city: "TP. Hồ Chí Minh",
    isDefault: false,
  },
  {
    id: 6,
    name: "Phạm Văn Đức",
    phone: "(+84) 978 456 123",
    address: "25 Đường Trần Phú, Phường Hải Châu 1, Quận Hải Châu",
    city: "Đà Nẵng",
    isDefault: true,
  },
];

// Simulate fetching data from an API
const fetchUserAddresses = async (): Promise<UserAddress[]> => {
  return new Promise((resolve) => {
    setTimeout(() => resolve(mockUserAddresses), 1000);
  });
};

const UserAddress = () => {
  const [addresses, setAddresses] = useState<UserAddress[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchUserAddresses().then((data) => {
      setAddresses(data);
      setLoading(false);
    });
  }, []);

  return (
    <div className="max-w-5xl mx-auto p-6 bg-white rounded-lg shadow-md">
      {/* Header */}
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-lg font-semibold">Địa chỉ của tôi</h2>
        <button className="bg-orange p-3 text-white px-4 py-2">
          + Thêm địa chỉ mới
        </button>
      </div>
        <div className='w-full mt-2 border-t-2 py-2 border-gray-100'></div>

      {/* Loading State */}
      {loading ? (
        <div className="text-center text-gray-500">Đang tải dữ liệu...</div>
      ) : (
        <div className="space-y-4">
          {addresses.map((address) => (
            <div key={address.id} className="rounded-lg">
              <div className="flex justify-between">
                <div>
                  <h3 className="font-bold uppercase">{address.name}</h3>
                  <p className="text-gray-600 text-sm">{address.phone}</p>
                  <p className="text-gray-700 text-sm mt-1">
                    {address.address}, {address.city}
                  </p>
                  {address.isDefault && (
                    <span className="text-xs bg-white text-red-500 border py-1 px-2 border-orange mt-2 inline-block">
                      Mặc định
                    </span>
                  )}
                </div>
                <div className="flex flex-col space-y-2 text-blue-500 text-sm justify-end items-end">
                  <div className="flex flex-end space-x-2 items-end">
                    <button>Cập nhật</button>
                    {!address.isDefault && <button>Xóa</button>}
                  </div>

                    <button className="border border-gray-400 px-2 py-1 rounded text-black hover:bg-gray-200">
                      Thiết lập mặc định
                    </button>
                </div>
              </div>
                <div className='w-full mt-3 border-t-2 py-2 border-gray-100'></div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default UserAddress;