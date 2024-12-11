package com.lcaohoanq.shoppe.domain.inventory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lcaohoanq.shoppe.domain.inventory.Warehouse.WarehouseName;
import com.lcaohoanq.shoppe.enums.Country;
import java.time.LocalDateTime;

@JsonPropertyOrder(
    value = {
        "id",
        "name",
        "address",
        "city",
        "country",
        "quantity",
        "reserved",
        "reorder_point",
        "created_at",
        "updated_at"
    }
)
public record WarehouseResponse(
    Long id,
    WarehouseName name,
    String address,
    String city,
    Country country,
    Long quantity,
    Long reserved, //Reserved quantity for orders
    @JsonProperty("reorder_point") Long reorderPoint,
    
    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime createdAt,
    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime updatedAt
) {

}