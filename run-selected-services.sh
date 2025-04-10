#!/bin/bash

# Danh sách các service khả dụng
AVAILABLE_SERVICES=(
  "eureka-service"
  "gateway-service"
  "auth-service"
  "asset-service"
  "chat-service"
  "product-service"
  "inventory-service"
  "notification-service"
)

echo "Danh sách services khả dụng:"
for i in "${!AVAILABLE_SERVICES[@]}"; do
  echo "$((i+1)). ${AVAILABLE_SERVICES[$i]}"
done

# Nhập số lượng service muốn chạy
read -p "Bạn muốn chạy bao nhiêu service? " COUNT

SELECTED_SERVICES=()

for (( i=0; i<$COUNT; i++ )); do
  read -p "Nhập số thứ tự của service thứ $((i+1)) bạn muốn chạy: " INDEX
  INDEX=$((INDEX-1))  # Convert to 0-based
  if [[ $INDEX -ge 0 && $INDEX -lt ${#AVAILABLE_SERVICES[@]} ]]; then
    SELECTED_SERVICES+=("${AVAILABLE_SERVICES[$INDEX]}")
  else
    echo "Số không hợp lệ, bỏ qua!"
  fi
done

# Mở từng service đã chọn trong một tab terminal mới
for SERVICE in "${SELECTED_SERVICES[@]}"; do
  echo "Đang khởi động $SERVICE..."
  gnome-terminal --tab -- bash -c "cd $SERVICE && mvn spring-boot:run; exec bash"
done
