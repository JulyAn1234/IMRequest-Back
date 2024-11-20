package com.IMRequest.IMRequest.controller;

import com.IMRequest.IMRequest.model.entities.Warehouse;
import com.IMRequest.IMRequest.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/warehouse")
@RestController
public class WarehouseController {

    private final WarehouseService warehouseService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping("/getWarehouse/{id}")
    public ResponseEntity<?> getWarehouse(@PathVariable String id) {
        try{
            Optional<Warehouse> warehouse = warehouseService.getWarehouseById(id);
            return warehouse.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getAllWarehouses")
    public ResponseEntity<?> getAllWarehouses() {
        try{
            List<Warehouse> warehouseList = warehouseService.getAllWarehouses();
            return warehouseList.isEmpty()? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.ok(warehouseList);
        }catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/createWarehouse")
    public ResponseEntity<?> createWarehouse(@RequestBody Warehouse warehouse) {
        try{
            Warehouse createdWarehouse = warehouseService.createWarehouse(warehouse);
            return ResponseEntity.ok(createdWarehouse);
        }catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping("/editWarehouse/{id}")
    public ResponseEntity<?> editWarehouse(@PathVariable String id, @RequestBody Warehouse warehouse) {
        try{
            Optional<Warehouse> updatedWarehouse = warehouseService.editWarehouse(id, warehouse);
            return updatedWarehouse.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
