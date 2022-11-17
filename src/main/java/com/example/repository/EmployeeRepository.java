package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.Department;
import com.example.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	
	Page<Employee> findByEmpDept(Department d,Pageable pageable);
	
	@Query("SELECT e.empId, e.empName FROM Employee e INNER JOIN e.empDept AS d WHERE d.deptId=:deptId ORDER BY e.empName")
	List<Object[]> getEmpByDeptId(Integer deptId);
	
	@Query("SELECT e FROM Employee e INNER JOIN e.empDept AS d WHERE d.deptId=:deptId")
	Page<Employee> getAllEmpByDeptId(Integer deptId, Pageable pageable);
}
