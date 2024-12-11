package com.lcaohoanq.shoppe.domain.inventory;

import com.lcaohoanq.shoppe.domain.inventory.Warehouse.WarehouseName;
import com.lcaohoanq.shoppe.enums.Country;
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