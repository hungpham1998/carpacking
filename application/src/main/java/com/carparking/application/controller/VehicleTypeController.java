package com.carparking.application.controller;

import com.carparking.application.request.VehicleTypeRequest;
import com.carparking.application.response.ResponseObjectType;
import com.carparking.application.service.VehicleTypeService;
import com.carparking.core_entity.entities.VehicleTypeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vehicletype")
@CrossOrigin(origins = "*", maxAge = 3600)
public class VehicleTypeController {
    @Autowired
    VehicleTypeService vehicleTypeService;

    @GetMapping("getall")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<?> getAll() throws Exception {
        Map<String, Object> response = new HashMap<>();
        List<VehicleTypeModel> data = vehicleTypeService.getAll();
        response.put("data", data);
        response.put("status", 200);
        response.put("message", "Get data successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<?> getPage(@RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                     @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                     @RequestParam(name = "q", required = false, defaultValue = "") String keyword,
                                     @RequestParam(name = "field", required = false, defaultValue = "id") String field,
                                     @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort) throws Exception {
        Map<String, Object> response = new HashMap<>();
        ResponseObjectType<VehicleTypeModel> data = vehicleTypeService.getPageSize(keyword, page, size, field, sort);
        response.put("data", data.getData());
        response.put("meta", data.getMeta());
        response.put("status", 200);
        response.put("message", "Get data successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<?> create(@RequestBody @Valid VehicleTypeRequest request) throws Exception {
        Map<String, Object> response = new HashMap<>();
        VehicleTypeModel data = vehicleTypeService.create(request);
        response.put("data", data);
        response.put("status", 201);
        response.put("message", "Created successfuly!");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<?> getById(@PathVariable(value = "id") Long id) throws Exception {
        Map<String, Object> response = new HashMap<>();
        response.put("data", vehicleTypeService.findById(id));
        response.put("status", 200);
        response.put("message", "Get data successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<?> deleteById(@PathVariable(value = "id") Long id) throws Exception {
        Map<String, Object> response = new HashMap<>();
        vehicleTypeService.deleteById(id);
        response.put("status", 200);
        response.put("message", "Delete succesfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @Valid @RequestBody VehicleTypeRequest request)
            throws Exception {
        Map<String, Object> response = new HashMap<>();
        VehicleTypeModel data = vehicleTypeService.update(id, request);
        response.put("data", data);
        response.put("status", 200);
        response.put("message", "Cập nhật thành công!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
