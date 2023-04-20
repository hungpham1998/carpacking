package com.carparking.application.repository;

import com.carparking.core_entity.entities.RoleModel;
import com.carparking.core_entity.repository.IBaseDocumentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoleRepository extends IBaseDocumentRepository<RoleModel> {
	@Query(value = "select * from role where name like %:name%", nativeQuery = true)
	RoleModel findByName(String name);

	@Query(value = "select * from role where name like %:name%", nativeQuery = true)
	List<RoleModel> getListRoleByName(String name);

	@Query(value = "select * from role where name like %:name% and id <> :id", nativeQuery = true)
	List<RoleModel> getListRoleByNameAndIdNot(String name, Long id);

	@Query(value = "select * from role where (:q is null OR name like %:q%)", nativeQuery = true)
	Page<RoleModel> getByNameAndPage(String q, Pageable page);
}
