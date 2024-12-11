package com.lcaohoanq.shoppe.domain.inventory;

import java.util.List;

public interface IWarehouseService {
    WarehouseResponse create(WarehouseDTO request);

    List<WarehouseResponse> findAll();

    WarehouseResponse findById(Long id);
}
