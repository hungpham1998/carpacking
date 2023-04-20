package com.carparking.application.repository;

import com.carparking.core_entity.entities.TicketModel;
import com.carparking.core_entity.repository.IBaseDocumentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends IBaseDocumentRepository<TicketModel> {

	@Query(value = "select * from ticket where ( :idCard is null OR id_card like :idCard )"
			+ " And ( :ticketCode is null OR ticket_code LIKE :ticketCode )", nativeQuery = true)
	List<TicketModel> getListByIdCardORTicketCode(String idCard, String ticketCode);

	@Query(value = "select * from ticket where ( :idCard is null OR id_card like :idCard )"
			+ " And ( :ticketCode is null OR ticket_code LIKE :ticketCode )  AND id <> :id ", nativeQuery = true)
	List<TicketModel> getListByIdCardORTicketCodeAndIdNot(String idCard, String ticketCode, Long id);

	@Query(value = "select * from ticket where" + "(:q is null OR id_card like %:q% OR ticket_code LIKE %:q% )"
			+ " AND (:ticketTypeId is null OR ticket_type_id = :ticketTypeId)"
			+ " AND (:vehicelCategoryId is null OR vehicle_category_id = : vehicelCategoryId) ", nativeQuery = true)
	Page<TicketModel> getPageSize(String q, Long ticketTypeId, Long vehicelCategoryId, Pageable pageSize);
}
