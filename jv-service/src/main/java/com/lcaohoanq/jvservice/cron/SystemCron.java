package com.lcaohoanq.jvservice.cron;

import com.lcaohoanq.jvservice.domain.inventory.IWarehouseService;
import com.lcaohoanq.jvservice.domain.inventory.WarehouseResponse;
import com.lcaohoanq.jvservice.domain.product.IProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SystemCron {

    private final IProductService productService;
    private final IWarehouseService warehouseService;

    @Scheduled(cron = "0 */10 * * * *")
    public void updateQuantityWarehouse() {

        List<WarehouseResponse> warehouses = warehouseService.findAll();
        for(WarehouseResponse warehouse : warehouses) {
            Long totalQuantity = productService.countByWarehouseId(warehouse.id());
            warehouseService.updateQuantity(warehouse.id(), totalQuantity);
        }
        
    }    
    
}
