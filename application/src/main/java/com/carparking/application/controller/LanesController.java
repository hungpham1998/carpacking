package com.carparking.application.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.carparking.application.request.LanesRequest;
import com.carparking.application.response.ResponseObjectType;
import com.carparking.application.service.LanesService;
import com.carparking.core_entity.entities.LanesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lanes")
public class LanesController {
	@Autowired
    LanesService lanesService;

	@GetMapping("getall")
	@CrossOrigin(origins = "*", maxAge = 3600)
	public ResponseEntity<?> getAll() throws Exception {
		Map<String, Object> response = new HashMap<>();
		List<LanesModel> data = lanesService.getAll();
		response.put("data", data);
		response.put("status", 200);
		response.put("message", "Get data successfully!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	};

	@GetMapping
	@CrossOrigin(origins = "*", maxAge = 3600)
	public ResponseEntity<?> getPage(@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "q", required = false, defaultValue = "") String keyword,
			@RequestParam(name = "field", required = false, defaultValue = "id") String field,
			@RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort) throws Exception {
		Map<String, Object> response = new HashMap<>();
		ResponseObjectType<LanesModel> data = lanesService.getPageSize(keyword, page, size, field, sort);
		response.put("data", data.getData());
		response.put("meta", data.getMeta());
		response.put("status", 200);
		response.put("message", "Get data successfully!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	};

	@PostMapping
	@CrossOrigin(origins = "*", maxAge = 3600)
	public ResponseEntity<?> create(@RequestBody @Valid LanesRequest request) throws Exception {
		Map<String, Object> response = new HashMap<>();
		LanesModel data = lanesService.create(request);
		response.put("data", data);
		response.put("status", 201);
		response.put("message", "Created successfuly!");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	};

	@GetMapping("{id}")
	@CrossOrigin(origins = "*", maxAge = 3600)
	public ResponseEntity<?> getById(@PathVariable(value = "id") Long id) throws Exception {
		Map<String, Object> response = new HashMap<>();
		response.put("data", lanesService.findById(id));
		response.put("status", 200);
		response.put("message", "Get data successfully!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	};

	@DeleteMapping("{id}")
	@CrossOrigin(origins = "*", maxAge = 3600)
	public ResponseEntity<?> deleteById(@PathVariable(value = "id") Long id) throws Exception {
		Map<String, Object> response = new HashMap<>();
		lanesService.deleteById(id);
		response.put("status", 200);
		response.put("message", "Delete succesfully!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	};

	@PutMapping("{id}")
	@CrossOrigin(origins = "*", maxAge = 3600)
	public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @Valid @RequestBody LanesRequest request)
			throws Exception {
		Map<String, Object> response = new HashMap<>();
		LanesModel data = lanesService.update(id, request);
		response.put("data", data);
		response.put("status", 200);
		response.put("message", "Cập nhật thành công!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	};
}
