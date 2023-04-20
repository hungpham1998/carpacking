package com.carparking.application.controller;

import com.carparking.application.dto.AccountDto;
import com.carparking.application.request.AccountRequest;
import com.carparking.application.response.ResponseObjectType;
import com.carparking.application.service.AccountService;
import com.carparking.core_entity.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/account")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AccountController {
	@Autowired
    AccountService accountService;

	@GetMapping("getall")
	@CrossOrigin(origins = "*", maxAge = 3600)
	public ResponseEntity<?> getAll() throws Exception {
		Map<String, Object> response = new HashMap<>();
		List<Account> data = accountService.getAll();
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
		ResponseObjectType<AccountDto> data = accountService.getPage(keyword, page, size, field, sort);
		response.put("data", data.getData());
		response.put("meta", data.getMeta());
		response.put("status", 200);
		response.put("message", "Get data successfully!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	};

	@PostMapping
	@CrossOrigin(origins = "*", maxAge = 3600)
	public ResponseEntity<?> create(@RequestBody @Valid AccountRequest request) throws Exception {
		Map<String, Object> response = new HashMap<>();
		Account data = accountService.create(request);
		response.put("data", data);
		response.put("status", 201);
		response.put("message", "Created successfuly!");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	};

	@GetMapping("{id}")
	@CrossOrigin(origins = "*", maxAge = 3600)
	public ResponseEntity<?> getById(@PathVariable(value = "id") Long id) throws Exception {
		Map<String, Object> response = new HashMap<>();
		response.put("data", accountService.findById(id));
		response.put("status", 200);
		response.put("message", "Get data successfully!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	};

	@DeleteMapping("{id}")
	@CrossOrigin(origins = "*", maxAge = 3600)
	public ResponseEntity<?> deleteById(@PathVariable(value = "id") Long id) throws Exception {
		Map<String, Object> response = new HashMap<>();
		accountService.delete(id);
		response.put("status", 200);
		response.put("message", "Delete succesfully!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	};

	@PutMapping("{id}")
	@CrossOrigin(origins = "*", maxAge = 3600)
	public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @Valid @RequestBody AccountRequest request)
			throws Exception {
		Map<String, Object> response = new HashMap<>();
		Account data = accountService.update(id, request);
		response.put("data", data);
		response.put("status", 200);
		response.put("message", "Cập nhật thành công!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	};

}
