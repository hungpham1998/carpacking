package com.carparking.core_entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBaseDocumentRepository<T> extends JpaRepository<T, Long> {

	 
}
