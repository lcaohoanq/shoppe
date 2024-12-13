package com.lcaohoanq.shoppe.domain.inventory;

import com.lcaohoanq.shoppe.base.exception.DataNotFoundException;
import com.lcaohoanq.shoppe.enums.Country;
import com.lcaohoanq.shoppe.mapper.WarehouseMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WarehouseService implements IWarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;

    @Override
    public WarehouseResponse create(WarehouseDTO request) {
        if(warehouseRepository.existsByName(request.name())) {
            throw new IllegalArgumentException("Warehouse name already exists");
        }
        
        Warehouse newWarehouse = Warehouse.builder()
            .name(request.name())
            .address(request.address())
            .name(request.name())
            .city(request.city())
            .country(Country.VIETNAM)
            .quantity(request.quantity() == null ? 0 : request.quantity())
            .reserved(request.reserved() == null ? 0 : request.reserved())
            .reorderPoint(request.reorderPoint() == null ? 0 : request.reorderPoint())
            .build();
        
        return warehouseMapper.toWarehouseResponse(
            warehouseRepository.save(newWarehouse)
        );
    }

    @Override
    public List<WarehouseResponse> findAll() {
        return warehouseRepository
            .findAll()
            .stream()
            .map(warehouseMapper::toWarehouseResponse)
            .toList();
    }

    @Override
    public WarehouseResponse findById(Long id) {
        Warehouse warehouse = warehouseRepository
            .findById(id)
            .orElseThrow(() -> new DataNotFoundException("Warehouse not found"));
        
        return warehouseMapper.toWarehouseResponse(warehouse);
    }

    @Override
    @Transactional
    public void updateQuantity(Long warehouseId, Long totalQuantity) {
        warehouseRepository.updateQuantity(warehouseId, totalQuantity);
    }
    
}
