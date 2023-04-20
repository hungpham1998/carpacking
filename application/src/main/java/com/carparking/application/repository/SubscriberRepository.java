package com.carparking.application.repository;

import com.carparking.core_entity.entities.SubscriberModel;
import com.carparking.core_entity.repository.IBaseDocumentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriberRepository extends IBaseDocumentRepository<SubscriberModel> {
	@Query(value = "select * from subscriber where ( name like name "
			+ " OR name like :name AND phone like :phone OR phone like :phone)", nativeQuery = true)
	List<SubscriberModel> getListByNameOrPhone(String name, String phone);

	@Query(value = "select * from subscriber where ( name like name "
			+ " OR name like :name AND phone like :phone OR phone like :phone) AND id <> :id ", nativeQuery = true)
	List<SubscriberModel> getListByNameOrPhoneAndIdNot(String name, String phone, Long id);

	@Query(value = "select * from subscriber where (:q is null OR "
			+ " name LIKE %:q% OR phone %:q% )", nativeQuery = true)
	Page<SubscriberModel> getPageSize(String q, Pageable pageSize);
}
