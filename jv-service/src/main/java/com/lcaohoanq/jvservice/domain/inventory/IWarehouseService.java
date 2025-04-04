package com.lcaohoanq.jvservice.domain.inventory;

import java.util.List;

public interface IWarehouseService {
    WarehouseResponse create(WarehouseDTO request);

    List<WarehouseResponse> findAll();

    WarehouseResponse findById(Long id);
    
    void updateQuantity(Long warehouseId, Long totalQuantity);
}
