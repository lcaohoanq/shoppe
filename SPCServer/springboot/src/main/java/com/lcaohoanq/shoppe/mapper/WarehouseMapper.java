package com.lcaohoanq.shoppe.mapper;

import com.lcaohoanq.shoppe.domain.inventory.Warehouse;
import com.lcaohoanq.shoppe.domain.inventory.WarehouseResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {

    WarehouseResponse toWarehouseResponse(Warehouse warehouse);
    
}
