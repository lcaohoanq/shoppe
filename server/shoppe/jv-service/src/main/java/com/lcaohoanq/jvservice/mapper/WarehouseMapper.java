package com.lcaohoanq.jvservice.mapper;

import com.lcaohoanq.jvservice.domain.inventory.Warehouse;
import com.lcaohoanq.jvservice.domain.inventory.WarehouseResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {

    WarehouseResponse toWarehouseResponse(Warehouse warehouse);
    
}
