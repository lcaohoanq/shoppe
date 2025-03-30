package com.lcaohoanq.jvservice.domain.inventory;

import com.lcaohoanq.jvservice.domain.inventory.Warehouse.WarehouseName;
import com.lcaohoanq.jvservice.enums.Country;
import jakarta.validation.constraints.NotNull;

public record WarehouseDTO(
    WarehouseName name,
    @NotNull(message = "Warehouse address is required") String address,
    @NotNull(message = "Warehouse city is required") String city,
    Country country,
    Long quantity,
    Long reserved,
    Long reorderPoint
) {}