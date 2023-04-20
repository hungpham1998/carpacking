package com.carparking.application.service;

import com.carparking.application.exception.ResourceAlreadyExistsException;
import com.carparking.application.exception.ResourceNotFoundException;
import com.carparking.application.repository.RoleRepository;
import com.carparking.application.request.RoleRequest;
import com.carparking.application.response.ResponseObjectType;
import com.carparking.application.ultis.PageSizeObjectType;
import com.carparking.application.ultis.SortPageSize;
import com.carparking.core_entity.entities.RoleModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService  {

	@Autowired
    RoleRepository roleRepository;

	public RoleModel createRole(RoleRequest roleR) {
		List<RoleModel> listData = roleRepository.getListRoleByName(roleR.getName());
		if (listData.size() > 0) {
			throw new ResourceAlreadyExistsException("Data role already exist. Create role faild !");
		}
		RoleModel role = new RoleModel();
		BeanUtils.copyProperties(roleR, role);
		return roleRepository.save(role);
	};

	public RoleModel updateRole(Long id, RoleRequest roleR) {
		RoleModel role = roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data not found by id !"));
		List<RoleModel> listData = roleRepository.getListRoleByNameAndIdNot(roleR.getName(), id);
		if (listData.size() > 0) {
			throw new ResourceAlreadyExistsException("Data role already exist. update role faild !");
		}
		roleR.setId(id);
		BeanUtils.copyProperties(roleR, role);
		return roleRepository.save(role);
	};

	public RoleModel getById(Long id) {
		RoleModel role = roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data not found by id !"));
		return role;
	};

	public List<RoleModel> getAll() {
		return roleRepository.findAll();
	};

	public ResponseObjectType<RoleModel> getPageSize(String q, int page, int size, String field, String sort) {
		Pageable pageSize = SortPageSize.getPageSize(page, size, field, sort);
		String query = null;
		if (q.length() > 0 || q.trim().equals("") == false) {
			query = q;
		}
		Page<RoleModel> res = roleRepository.getByNameAndPage(query, pageSize);
		return PageSizeObjectType.getPageSize(res.getContent(), size, page, res.getTotalElements(), res.getTotalPages());
	};

	public RoleModel findById(Long id) {
		RoleModel role = roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data not found by id !"));
		return role;
	};
}
