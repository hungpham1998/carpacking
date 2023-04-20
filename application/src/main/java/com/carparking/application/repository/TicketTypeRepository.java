package com.carparking.application.repository;

import com.carparking.core_entity.entities.TicketTypeModel;
import com.carparking.core_entity.repository.IBaseDocumentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketTypeRepository extends IBaseDocumentRepository<TicketTypeModel> {
	@Query(value = "select * from ticket_type where title LIKE :title", nativeQuery = true)
	List<TicketTypeModel> getByTitle(String title);

	@Query(value = "select * from ticket_type where title LIKE :title AND id <> :id", nativeQuery = true)
	List<TicketTypeModel> getByTitleAndIdNot(String title, Long id);

	@Query(value = "select * from ticket_type where :title is null OR title LIKE :title", nativeQuery = true)
	Page<TicketTypeModel> getByTitle(String title, Pageable pageSize);
}
