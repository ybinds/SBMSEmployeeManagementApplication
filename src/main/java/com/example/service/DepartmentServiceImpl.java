package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Department;
import com.example.exception.DepartmentNotFoundException;
import com.example.repository.DepartmentRepository;

@Service
@Transactional
public class DepartmentServiceImpl implements IDepartmentService {

	@Autowired
	private DepartmentRepository repo;
	
	@Override
	public Page<Department> getAllDepartments(Pageable pageable) {
		pageable = PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize(), pageable.getSort());
		System.out.println(pageable);
		return repo.findAll(pageable);
	}
	
	@Override
	public List<Department> getAllDepartments() {
		return repo.findAll();
	}
	
	@Override
	public Department getDeptById(Integer id) {
		return repo.findById(id)
				.orElseThrow(()-> new DepartmentNotFoundException("Department '" + id + "' not found"));
	}

	@Override
	public Integer saveDepartment(Department d) {
		Department dept = repo.save(d);
		return dept.getDeptId();
	}

	@Override
	public void deleteDepartment(Integer id){
		repo.delete(this.getDeptById(id));
	}

}
