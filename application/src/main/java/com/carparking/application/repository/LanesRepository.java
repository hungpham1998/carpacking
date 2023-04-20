package com.carparking.application.repository;

import com.carparking.core_entity.entities.LanesModel;
import com.carparking.core_entity.repository.IBaseDocumentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LanesRepository extends IBaseDocumentRepository<LanesModel> {
	@Query(value = "select * from lanes where title LIKE :title", nativeQuery = true)
	List<LanesModel> getTitle(String title);

	@Query(value = "select * from lanes where title LIKE :title And id <> :id", nativeQuery = true)
	List<LanesModel> getTitleAndIdNot(String title, Long id);

	@Query(value = "select * from lanes where  (:title is null OR  title LIKE :title)", nativeQuery = true)
	Page<LanesModel> getTitleAndPage(String title, Pageable pageSize);

}
