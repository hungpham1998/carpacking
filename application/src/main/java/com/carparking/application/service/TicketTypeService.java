package com.carparking.application.service;

import com.carparking.application.exception.ResourceAlreadyExistsException;
import com.carparking.application.exception.ResourceNotFoundException;
import com.carparking.application.repository.TicketTypeRepository;
import com.carparking.application.request.TicketTypeRequest;
import com.carparking.application.response.ResponseObjectType;
import com.carparking.application.ultis.PageSizeObjectType;
import com.carparking.application.ultis.SortPageSize;
import com.carparking.core_entity.entities.TicketTypeModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketTypeService {

	@Autowired
    TicketTypeRepository ticketTypeRepository;

	public TicketTypeModel create(TicketTypeRequest ticketTypeR) {
		List<TicketTypeModel> listData = ticketTypeRepository.getByTitle(ticketTypeR.getTitle());
		if (listData.size() > 0) {
			throw new ResourceAlreadyExistsException("data type ticket already exits. Created faild !");
		}
		TicketTypeModel ticketTypeModel = new TicketTypeModel();
		BeanUtils.copyProperties(ticketTypeR, ticketTypeModel);
		return ticketTypeRepository.save(ticketTypeModel);
	};

	public TicketTypeModel update(Long id, TicketTypeRequest ticketTypeR) {
		ticketTypeR.setId(id);
		TicketTypeModel ticketTypeModel = ticketTypeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data ticket type not found !"));
		List<TicketTypeModel> listData = ticketTypeRepository.getByTitleAndIdNot(ticketTypeR.getTitle(), id);
		if (listData.size() > 0) {
			throw new ResourceAlreadyExistsException("data type ticket already exits. update faild !");
		}
		BeanUtils.copyProperties(ticketTypeR, ticketTypeModel);
		return ticketTypeRepository.save(ticketTypeModel);
	};

	public void deleteById(Long id) {
		TicketTypeModel ticketTypeModel = ticketTypeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data ticket type not found !"));
		if (ticketTypeModel != null) {
			ticketTypeRepository.deleteById(id);
		}
	};

	public List<TicketTypeModel> getAll() {
		return ticketTypeRepository.findAll();
	};

	public ResponseObjectType<TicketTypeModel> getPageSize(String q, int page, int size, String field, String sort) {
		Pageable pageSize = SortPageSize.getPageSize(page, size, field, sort);
		String query = null;
		if (q.length() > 0 || q.trim().equals("") == false) {
			query = q;
		}
		Page<TicketTypeModel> res = ticketTypeRepository.getByTitle(query, pageSize);
		return PageSizeObjectType.getPageSize(res.getContent(), size, page, res.getTotalElements(), res.getTotalPages());
	};

	public TicketTypeModel findById(Long id) {
		TicketTypeModel ticketTypeModel = ticketTypeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data ticket type not found !"));
		return ticketTypeModel;
	};
}
