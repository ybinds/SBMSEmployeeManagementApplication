package com.example.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.entity.Department;

public interface IDepartmentService {

	Page<Department> getAllDepartments(Pageable pageable);
	List<Department> getAllDepartments();
	Department getDeptById(Integer id);
	Integer saveDepartment(Department d);
	void deleteDepartment(Integer id);
}
