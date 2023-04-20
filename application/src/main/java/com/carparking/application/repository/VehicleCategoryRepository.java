package com.carparking.application.repository;

import com.carparking.core_entity.entities.VehicleCategoryModel;
import com.carparking.core_entity.repository.IBaseDocumentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleCategoryRepository extends IBaseDocumentRepository<VehicleCategoryModel> {

	@Query(value = "select * from vehicle_category where :nameType is null OR name_type LIKE :nameType", nativeQuery = true)
	List<VehicleCategoryModel> getListByNameType(String nameType);

	@Query(value = "select * from vehicle_category where "
			+ " (:q is null OR name_type LIKE %:q% )", nativeQuery = true)
	Page<VehicleCategoryModel> getPageSizeAndKeyword(String q, Pageable pageSize);

	@Query(value = "select * from vehicle_category where (:nameType is null OR name_type LIKE :nameType )"
			+ " AND id <> :id ", nativeQuery = true)
	List<VehicleCategoryModel> getListByNameTypeAndIdNot(String nameType, Long id);

	@Query(value = "select * from vehicle_category where (:q is null OR name_type LIKE %:q% )"
			+ " And (:vehicleTypeId is null OR vehicle_type_id = :vehicleTypeId ) ", nativeQuery = true)
	Page<VehicleCategoryModel> getPageSizeAndKeywordAndVehicleTypeId(String q, Long vehicleTypeId, Pageable pageSize);
}
