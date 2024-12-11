package com.lcaohoanq.shoppe.domain.inventory;

import com.lcaohoanq.shoppe.domain.inventory.Warehouse.WarehouseName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    Boolean existsByName(WarehouseName name);

//    @Modifying
//    @Query("UPDATE Warehouse wh SET wh.quantity = wh.quantity + :quantity WHERE wh.id = :productId")
//    void increaseQuantity(
//        @Param("productId") long productId,
//        @Param("quantity") int quantity
//    );
//
//    @Modifying
//    @Query("UPDATE Warehouse wh SET wh.quantity = wh.quantity - :quantity WHERE wh.id = :productId")
//    void decreaseQuantity(
//        @Param("productId") long productId,
//        @Param("quantity") int quantity
//    );
    
}
