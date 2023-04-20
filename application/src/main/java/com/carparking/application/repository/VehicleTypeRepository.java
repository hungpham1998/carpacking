package com.carparking.application.repository;

import com.carparking.core_entity.entities.VehicleTypeModel;
import com.carparking.core_entity.repository.IBaseDocumentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VehicleTypeRepository extends IBaseDocumentRepository<VehicleTypeModel> {
	@Query(value = "select * from vehicle_type where title LIKE :title", nativeQuery = true)
	List<VehicleTypeModel> getTitle(String title);

	@Query(value = "select * from vehicle_type where title LIKE :title And id <> :id", nativeQuery = true)
	List<VehicleTypeModel> getTitleAndIdNot(String title, Long id);

	@Query(value = "select * from vehicle_type where :title is null OR title LIKE %:title%", nativeQuery = true)
	Page<VehicleTypeModel> getByTitleAndPage(String title, Pageable pageSize);
}
