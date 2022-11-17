package com.example.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.entity.Employee;
import com.example.exception.DepartmentNotFoundException;
import com.example.exception.EmployeeNotFoundException;

public interface IEmployeeService {
	
	// get employees by department id
	List<Object[]> getAllEmployeesByDeptId(Integer deptId) throws DepartmentNotFoundException;
	
	// get all employees page wise
	Page<Employee> getAllEmployees(Pageable pageable);
	
	// get all employees in a department page wise
	Page<Employee> getAllEmployeesByDept(Integer deptId, Pageable pageable);
	
	// get a single employee
	Employee getEmployeeById(Integer id) throws EmployeeNotFoundException;
	
	// create or update an employee
	Integer saveEmployee(Employee e);
	
	// delete employee
	void deleteEmployee(Integer id) throws EmployeeNotFoundException;
}
