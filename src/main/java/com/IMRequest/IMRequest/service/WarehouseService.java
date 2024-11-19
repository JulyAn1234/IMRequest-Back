package com.IMRequest.IMRequest.service;

import com.IMRequest.IMRequest.model.entities.Warehouse;
import com.IMRequest.IMRequest.model.repositories.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public Warehouse createWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Optional<Warehouse> getWarehouseById(String id) {
        return warehouseRepository.findById(id);
    }

    public Optional<Warehouse> editWarehouse(String id, Warehouse updatedWarehouse) {
        return warehouseRepository.findById(id).map(warehouse -> {
            warehouse.setActive(updatedWarehouse.isActive());
            warehouse.setName(updatedWarehouse.getName());
            warehouse.setUnidad(updatedWarehouse.getUnidad());
            return warehouseRepository.save(warehouse);
        });
    }

}
