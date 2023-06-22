package com.carparking.application.repository;

import java.util.List;
import java.util.Optional;

import com.carparking.core_entity.entities.Account;
import com.carparking.core_entity.repository.IBaseDocumentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface AccountRepository extends IBaseDocumentRepository<Account> {

	Optional<Account> findByEmail(String email);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	@Query(value = "SELECT * FROM account u WHERE  username LIKE :username", nativeQuery = true)
	Account findByUsername(String username);

	@Query(value = "select * from account WHERE (:username is null OR username LIKE :username)"
			+ " AND (:email is null OR email LIKE :email)", nativeQuery = true)
	List<Account> getListAccountByUserNameAndEmail(String username, String email);

	@Query(value = "select * from account where (:username is null OR username like :username)"
			+ " AND (:email is null  OR email like :email) " + " AND ( id <> :id)", nativeQuery = true)
	List<Account> getListAccountByUserNameAndEmailAndIdNot(String username, String email, Long id);

	@Query(value = "select * from account where :keyword is null OR username LIKE %:keyword% "
			+ " OR email LIKE %:keyword% OR fullname LIKE %:keyword% ", nativeQuery = true)
	Page<Account> getPageSizeAndKeyword(String keyword, Pageable page);

	@Query(value = "SELECT * FROM account WHERE is_delete IS NOT true AND username = :username", nativeQuery = true)
	Account findByUsernameToLogin(String username);

}
