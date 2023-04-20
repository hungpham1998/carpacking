package com.carparking.application.service;

import java.util.List;

import com.carparking.application.response.ResponseObjectType;
import com.carparking.application.repository.SubscriberRepository;
import com.carparking.application.request.SubscriberRequest;
import com.carparking.application.ultis.PageSizeObjectType;
import com.carparking.application.ultis.SortPageSize;
import com.carparking.core_entity.entities.SubscriberModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.carparking.application.exception.ResourceAlreadyExistsException;
import com.carparking.application.exception.ResourceNotFoundException;

@Service
public class SubscriberService {

	@Autowired
    SubscriberRepository subscriberRepository;

	private SubscriberModel save(SubscriberModel subcriberModel, SubscriberRequest subscriberR) {
		BeanUtils.copyProperties(subscriberR, subcriberModel);
		return subscriberRepository.save(subcriberModel);
	};

	public SubscriberModel create(SubscriberRequest subscriberR) {
		List<SubscriberModel> listData = subscriberRepository.getListByNameOrPhone(subscriberR.getName(),
				subscriberR.getPhone());
		if (listData.size() > 0) {
			throw new ResourceAlreadyExistsException("Data subscriber already exits. Create subscriber faild !");
		}
		SubscriberModel subcriberModel = new SubscriberModel();
		return save(subcriberModel, subscriberR);
	};

	public SubscriberModel update(Long id, SubscriberRequest subscriberR) {
		SubscriberModel op = subscriberRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data subscriber not found by id" + id));
		List<SubscriberModel> listData = subscriberRepository.getListByNameOrPhoneAndIdNot(subscriberR.getName(),
				subscriberR.getPhone(), id);
		if (listData.size() > 0) {
			throw new ResourceAlreadyExistsException("Data subscriber already exits. Create subscriber faild !");
		}
		subscriberR.setId(id);
		return save(op, subscriberR);
	};

	public SubscriberModel getById(Long id) {
		SubscriberModel op = subscriberRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data subscriber not found by id" + id));
		return op;
	};

	public ResponseObjectType<SubscriberModel> getPageSize(String q, int page, int size, String sort, String field) {
		Pageable pageSize = SortPageSize.getPageSize(page, size, field, sort);
		String query = null;
		if (q.length() > 0 || q.trim().equals("") == false) {
			query = q;
		}
		Page<SubscriberModel> res = subscriberRepository.getPageSize(query, pageSize);
		return PageSizeObjectType.getPageSize(res.getContent(), size, page, res.getTotalElements(),
				res.getTotalPages());
	};

	public List<SubscriberModel> getAll() {
		return subscriberRepository.findAll();
	};

	public void deleteById(Long id) {
		SubscriberModel op = subscriberRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data subscriber not found by id" + id));
		if (op != null) {
			subscriberRepository.deleteById(id);
		}
	};

}
