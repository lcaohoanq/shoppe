package com.lcaohoanq.jvservice.repository;

import com.lcaohoanq.jvservice.domain.inventory.Warehouse;
import com.lcaohoanq.jvservice.domain.inventory.Warehouse.WarehouseName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    Boolean existsByName(WarehouseName name);
    
    @Modifying
    @Query("UPDATE Warehouse wh SET wh.quantity = :totalQuantity WHERE wh.id = :warehouseId")
    void updateQuantity(
        @Param("warehouseId") long warehouseId,
        @Param("totalQuantity") long totalQuantity
    );
    
}
