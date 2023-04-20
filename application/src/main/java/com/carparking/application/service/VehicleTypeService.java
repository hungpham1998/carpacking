package com.carparking.application.service;

import com.carparking.application.exception.ResourceAlreadyExistsException;
import com.carparking.application.exception.ResourceNotFoundException;
import com.carparking.application.repository.VehicleTypeRepository;
import com.carparking.application.request.VehicleTypeRequest;
import com.carparking.application.response.ResponseObjectType;
import com.carparking.application.ultis.PageSizeObjectType;
import com.carparking.application.ultis.SortPageSize;
import com.carparking.core_entity.entities.VehicleTypeModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleTypeService {

	@Autowired
    VehicleTypeRepository vehicleTypeRepository;

	public VehicleTypeModel create(VehicleTypeRequest vehicleTypeR) {
		VehicleTypeModel vehicleTypeModel = new VehicleTypeModel();
		List<VehicleTypeModel> listData = vehicleTypeRepository.getTitle(vehicleTypeR.getTitle());
		if (listData.size() > 0) {
			throw new ResourceAlreadyExistsException("Data vehicle type already exits. Create vehicle type faild !");
		}
		BeanUtils.copyProperties(vehicleTypeR, vehicleTypeModel);
		return vehicleTypeRepository.save(vehicleTypeModel);
	};

	public VehicleTypeModel update(Long id, VehicleTypeRequest vehicleTypeR) {
		VehicleTypeModel vehicleTypeModel = vehicleTypeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data vehicle type not found "));
		vehicleTypeR.setId(id);
		List<VehicleTypeModel> listData = vehicleTypeRepository.getTitleAndIdNot(vehicleTypeR.getTitle(), id);
		if (listData.size() > 0) {
			throw new ResourceAlreadyExistsException("Data vehicle type already exits. Update vehicle type faild !");
		}
		BeanUtils.copyProperties(vehicleTypeR, vehicleTypeModel);
		return vehicleTypeRepository.save(vehicleTypeModel);
	};

	public List<VehicleTypeModel> getAll() {
		return vehicleTypeRepository.findAll();
	};

	public VehicleTypeModel findById(Long id) {
		VehicleTypeModel vehicleTypeModel = vehicleTypeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data vehicle type not found "));
		return vehicleTypeModel;
	};

	public ResponseObjectType<VehicleTypeModel> getPageSize(String q, int page, int size, String field, String sort) {
		Pageable pageSize = SortPageSize.getPageSize(page, size, field, sort);
		String query = null;
		if (q.length() > 0 || q.trim().equals("") == false) {
			query = q;
		}
		Page<VehicleTypeModel> res = vehicleTypeRepository.getByTitleAndPage(query, pageSize);
		return PageSizeObjectType.getPageSize(res.getContent(), size, page, res.getTotalElements(), res.getTotalPages());
	};

	public void deleteById(Long id) {
		VehicleTypeModel vehicleTypeModel = vehicleTypeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data vehicle type not found "));
		if (vehicleTypeModel != null) {
			vehicleTypeRepository.deleteById(id);
		}
	};

}
