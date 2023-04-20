package com.carparking.application.service;

import com.carparking.application.dto.TicketDto;
import com.carparking.application.exception.ResourceAlreadyExistsException;
import com.carparking.application.exception.ResourceNotFoundException;
import com.carparking.application.repository.TicketRepository;
import com.carparking.application.repository.TicketTypeRepository;
import com.carparking.application.repository.VehicleCategoryRepository;
import com.carparking.application.request.TicketRequest;
import com.carparking.application.response.ResponseObjectType;
import com.carparking.application.ultis.PageSizeObjectType;
import com.carparking.application.ultis.SortPageSize;
import com.carparking.core_entity.entities.TicketModel;
import com.carparking.core_entity.entities.TicketTypeModel;
import com.carparking.core_entity.entities.VehicleCategoryModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {

	@Autowired
    TicketRepository ticketRepository;

	@Autowired
    VehicleCategoryRepository vehicleCategoryRepository;

	@Autowired
    TicketTypeRepository ticketTypeRepository;

	private TicketTypeModel getTicketTypeById(Long id) {
		TicketTypeModel ticketType = ticketTypeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("data ticket type not found by id " + id));
		return ticketType;
	};

	private VehicleCategoryModel getVehicleCategoryById(Long id) {
		VehicleCategoryModel vehicleCategory = vehicleCategoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("data vehicle category not found by id " + id));
		return vehicleCategory;
	};

	private TicketDto convertTicketToDto(TicketModel ticket) {
		TicketDto ticketDto = new TicketDto();
		BeanUtils.copyProperties(ticket, ticketDto);
		ticketDto.setTicketTypeId(ticket.getTicketType().getId());
		ticketDto.setTicketTypeName(ticket.getTicketType().getTitle());
		ticketDto.setVehicleCategoryId(ticket.getVehicleCategory().getId());
		ticketDto.setVehicleCategoryName(ticket.getVehicleCategory().getNameType());
		return ticketDto;
	};

	private TicketDto writeAndSaveTicket(TicketModel ticket, TicketRequest ticketR) {
		BeanUtils.copyProperties(ticketR, ticket);
		VehicleCategoryModel vehicleCategory = getVehicleCategoryById(ticketR.getVehicleCategoryId());
		TicketTypeModel ticketType = getTicketTypeById(ticketR.getTicketTypeId());
		ticket.setTicketType(ticketType);
		ticket.setVehicleCategory(vehicleCategory);
		ticket = ticketRepository.save(ticket);
		return convertTicketToDto(ticket);
	};

	public TicketDto create(TicketRequest ticketR) {
		TicketModel ticket = new TicketModel();
		List<TicketModel> listData = ticketRepository.getListByIdCardORTicketCode(ticketR.getIdCard(),
				ticketR.getTicketCode());
		if (listData.size() > 0) {
			throw new ResourceAlreadyExistsException("Data ticket already exits");
		}
		return writeAndSaveTicket(ticket, ticketR);
	};

	public TicketDto update(Long id, TicketRequest ticketR) {
		TicketModel ticket = ticketRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("data ticket  not found by id " + id));
		List<TicketModel> listData = ticketRepository.getListByIdCardORTicketCodeAndIdNot(ticketR.getIdCard(),
				ticketR.getTicketCode(), id);
		if (listData.size() > 0) {
			throw new ResourceAlreadyExistsException("Data ticket already exits");
		}
		return writeAndSaveTicket(ticket, ticketR);
	};

	public TicketDto findById(Long id) {
		TicketModel ticket = ticketRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("data ticket  not found by id " + id));
		return convertTicketToDto(ticket);
	};

	public List<TicketModel> getAll() {
		return ticketRepository.findAll();
	};

	public ResponseObjectType<TicketDto> getPageSize(String q, Long ticketTypeId, Long vehicleCategoryId, int page,
													 int size, String field, String sort) {
		Pageable pageSize = SortPageSize.getPageSize(page, size, field, sort);
		String query = null;
		if (q.length() > 0 || q.trim().equals("") == false) {
			query = q;
		}
		Page<TicketModel> res = ticketRepository.getPageSize(query, ticketTypeId, vehicleCategoryId, pageSize);
		List<TicketDto> listData = res.getContent().stream().map(e -> convertTicketToDto(e))
				.collect(Collectors.toList());
		return PageSizeObjectType.getPageSize(listData, size, page, res.getTotalElements(), res.getTotalPages());
	};

}
