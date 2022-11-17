package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Employee;
import com.example.exception.DepartmentNotFoundException;
import com.example.exception.EmployeeNotFoundException;
import com.example.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private EmployeeRepository repo;
	
	@Override
	public Page<Employee> getAllEmployees(Pageable pageable) {
		pageable = PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize(), pageable.getSort());
		return repo.findAll(pageable);
	}
	
	@Override
	public Page<Employee> getAllEmployeesByDept(Integer deptId, Pageable pageable) {
		pageable = PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize(), pageable.getSort());
		return repo.getAllEmpByDeptId(deptId, pageable);
	}
	
	@Override
	public Employee getEmployeeById(Integer id) throws EmployeeNotFoundException {
		return repo.findById(id)
				.orElseThrow(
						()->new EmployeeNotFoundException("Employee '"+ id +"'Not Found"));
	}

	@Override
	public Integer saveEmployee(Employee e) {
		return repo.save(e).getEmpId();
	}

	@Override
	public void deleteEmployee(Integer id) throws EmployeeNotFoundException {
		repo.delete(this.getEmployeeById(id));
	}

	@Override
	public List<Object[]> getAllEmployeesByDeptId(Integer deptId) throws DepartmentNotFoundException {
		return repo.getEmpByDeptId(deptId);
	}

}
