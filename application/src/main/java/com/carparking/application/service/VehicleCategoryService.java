package com.carparking.application.service;

import com.carparking.application.dto.VehicleCategoryDto;
import com.carparking.application.exception.ResourceAlreadyExistsException;
import com.carparking.application.exception.ResourceNotFoundException;
import com.carparking.application.repository.VehicleCategoryRepository;
import com.carparking.application.repository.VehicleTypeRepository;
import com.carparking.application.request.VehicleCategoryRequest;
import com.carparking.application.response.ResponseObjectType;
import com.carparking.application.ultis.PageSizeObjectType;
import com.carparking.application.ultis.SortPageSize;
import com.carparking.core_entity.entities.VehicleCategoryModel;
import com.carparking.core_entity.entities.VehicleTypeModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleCategoryService {

	@Autowired
    VehicleCategoryRepository vehicleCategoryRepository;

	@Autowired
    VehicleTypeRepository vehicleTypeRepository;

	private VehicleTypeModel getVehicleTypeById(Long id) {
		VehicleTypeModel vehicleType = vehicleTypeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data vehicle type not found by id " + id));
		return vehicleType;
	};

	private VehicleCategoryDto convertVehicleCategoryToDto(VehicleCategoryModel vehicleCategory) {
		VehicleCategoryDto vehicleCategoryDto = new VehicleCategoryDto();
		BeanUtils.copyProperties(vehicleCategory, vehicleCategoryDto);
		vehicleCategoryDto.setVehicleTypeId(vehicleCategory.getVehicleType().getId());
		vehicleCategoryDto.setVehicleTypeName(vehicleCategory.getVehicleType().getTitle());
		return vehicleCategoryDto;
	};

	private VehicleCategoryDto copyWriteVehicleCategoryAndSave(VehicleCategoryModel vehicleCategory,
			VehicleCategoryRequest vehicleCategoryR) {
		VehicleTypeModel vehicleType = getVehicleTypeById(vehicleCategoryR.getVehicleTypeId());
		BeanUtils.copyProperties(vehicleCategoryR, vehicleCategory);
		vehicleCategory.setVehicleType(vehicleType);
		vehicleCategory = vehicleCategoryRepository.save(vehicleCategory);
		return convertVehicleCategoryToDto(vehicleCategory);
	}

	public VehicleCategoryDto create(VehicleCategoryRequest vehicleCategoryR) {
		VehicleCategoryModel vehicleCategory = new VehicleCategoryModel();
		List<VehicleCategoryModel> listData = vehicleCategoryRepository
				.getListByNameType(vehicleCategoryR.getNameType());
		if (listData.size() > 0) {
			throw new ResourceAlreadyExistsException(
					"Data vehicle category already exits" + vehicleCategoryR.getNameType());
		}
		return copyWriteVehicleCategoryAndSave(vehicleCategory, vehicleCategoryR);
	};

	public VehicleCategoryDto update(Long id, VehicleCategoryRequest vehicleCategoryR) {
		VehicleCategoryModel vehicleCategory = vehicleCategoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data vehicle category not found by id" + id));
		List<VehicleCategoryModel> listData = vehicleCategoryRepository
				.getListByNameTypeAndIdNot(vehicleCategoryR.getNameType(), id);
		if (listData.size() > 0) {
			throw new ResourceAlreadyExistsException(
					"Data vehicle category already exits" + vehicleCategoryR.getNameType());
		}
		vehicleCategoryR.setId(id);
		return copyWriteVehicleCategoryAndSave(vehicleCategory, vehicleCategoryR);
	};

	public ResponseObjectType<VehicleCategoryDto> getPageSize(String q, int page, int size, String field, String sort) {
		Pageable pageSize = SortPageSize.getPageSize(page, size, field, sort);
		String query = null;
		if (q.length() > 0 || q.trim().equals("") == false) {
			query = q;
		}
		Page<VehicleCategoryModel> res = vehicleCategoryRepository.getPageSizeAndKeyword(query, pageSize);
		List<VehicleCategoryDto> listData = res.getContent().stream().map(e -> convertVehicleCategoryToDto(e))
				.collect(Collectors.toList());
		return PageSizeObjectType.getPageSize(listData, size, page, res.getTotalElements(), res.getTotalPages());
	};

	public List<VehicleCategoryModel> getAll() {
		return vehicleCategoryRepository.findAll();
	};

	public ResponseObjectType<VehicleCategoryDto> getPageSizeAndVehicleTypeId(String q, Long vehicleTypeId, int page,
			int size, String field, String sort) {
		getVehicleTypeById(vehicleTypeId);
		Pageable pageSize = SortPageSize.getPageSize(page, size, field, sort);
		String query = null;
		if (q.length() > 0 || q.trim().equals("") == false) {
			query = q;
		}
		Page<VehicleCategoryModel> res = vehicleCategoryRepository.getPageSizeAndKeywordAndVehicleTypeId(query,
				vehicleTypeId, pageSize);
		List<VehicleCategoryDto> listData = res.getContent().stream().map(e -> convertVehicleCategoryToDto(e))
				.collect(Collectors.toList());
		return PageSizeObjectType.getPageSize(listData, size, page, res.getTotalElements(), res.getTotalPages());
	};

	public VehicleCategoryDto findById(Long id) {
		VehicleCategoryModel vehicleCategory = vehicleCategoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data vehicle category not found by id" + id));
		return convertVehicleCategoryToDto(vehicleCategory);
	};
}
