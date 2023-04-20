package com.carparking.application.service;

import com.carparking.application.exception.ResourceAlreadyExistsException;
import com.carparking.application.exception.ResourceNotFoundException;
import com.carparking.application.repository.LanesRepository;
import com.carparking.application.request.LanesRequest;
import com.carparking.application.response.ResponseObjectType;
import com.carparking.application.ultis.PageSizeObjectType;
import com.carparking.application.ultis.SortPageSize;
import com.carparking.core_entity.entities.LanesModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanesService {
	@Autowired
    LanesRepository lanesRepository;

	public LanesModel create(LanesRequest lanesR) {
		List<LanesModel> listData = lanesRepository.getTitle(lanesR.getTitle());
		if (listData.size() > 0) {
			throw new ResourceAlreadyExistsException("data type ticket already exits. Created faild !");
		}
		LanesModel lanesModel = new LanesModel();
		BeanUtils.copyProperties(lanesR, lanesModel);
		return lanesRepository.save(lanesModel);
	};

	public LanesModel update(Long id, LanesRequest lanesR) {
		lanesR.setId(id);
		LanesModel lanesModel = lanesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data ticket type not found !"));
		List<LanesModel> listData = lanesRepository.getTitleAndIdNot(lanesR.getTitle(), id);
		if (listData.size() > 0) {
			throw new ResourceAlreadyExistsException("data type ticket already exits. update faild !");
		}
		BeanUtils.copyProperties(lanesR, lanesModel);
		return lanesRepository.save(lanesModel);
	};

	public void deleteById(Long id) {
		LanesModel lanesModel = lanesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data ticket type not found !"));
		if (lanesModel != null) {
			lanesRepository.deleteById(id);
		}
	};

	public List<LanesModel> getAll() {
		return lanesRepository.findAll();
	};

	public ResponseObjectType<LanesModel> getPageSize(String q, int page, int size, String field, String sort) {
		Pageable pageSize = SortPageSize.getPageSize(page, size, field, sort);
		String query = null;
		if (q.length() > 0 || q.trim().equals("") == false) {
			query = q;
		}
		Page<LanesModel> res = lanesRepository.getTitleAndPage(query, pageSize);
		return PageSizeObjectType.getPageSize(res.getContent(), size, page, res.getTotalElements(), res.getTotalPages());
	};

	public LanesModel findById(Long id) {
		LanesModel lanesModel = lanesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data ticket type not found !"));
		return lanesModel;
	};
}
