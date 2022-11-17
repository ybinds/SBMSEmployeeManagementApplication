package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.entity.Employee;
import com.example.exception.DepartmentNotFoundException;
import com.example.exception.EmployeeNotFoundException;
import com.example.service.IDepartmentService;
import com.example.service.IEmployeeService;
import com.example.utils.EmployeeUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private IEmployeeService service;
	
	@Autowired
	private IDepartmentService dservice;
	
	@GetMapping("/register")
	public String showListPage(Model model) {
		EmployeeUtils.createEmployeeHobbiesList(model);
		Employee employee = new Employee();
		model.addAttribute("depts", dservice.getAllDepartments());
		model.addAttribute("employee", employee);
		model.addAttribute("module", "employee");
		return "EmployeeRegister";
	}
	
	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute Employee e, RedirectAttributes attributes) {
		Integer id = service.saveEmployee(e);
		String message = new StringBuffer("Employee '"+ id +"' created").toString();
		attributes.addAttribute("message", message);
		return "redirect:all";
	}
	
	@GetMapping("/all")
	public String showEmployeeList(
			@RequestParam(value="message", required=false) String message,
			@RequestParam(value="deptId", required=false) Integer deptId,
			@PageableDefault(page=1, size=5, sort="empId", direction = Sort.Direction.ASC) Pageable pageable,
			Model model) throws DepartmentNotFoundException {
		Page<Employee> page = null;
		if(deptId != null) {
			page = service.getAllEmployeesByDept(deptId, pageable);
		} else {
			page = service.getAllEmployees(pageable);
		}
		model.addAttribute("message", message);
		model.addAttribute("deptId", deptId);
		model.addAttribute("direction", page.getSort().toList().get(0).getDirection().toString().toLowerCase());
		model.addAttribute("property", page.getSort().toList().get(0).getProperty());
		model.addAttribute("revdirection", page.getSort().toList().get(0).getDirection().toString()=="ASC"?"desc":"asc");
		model.addAttribute("page", page);
		model.addAttribute("module","employee");
		return "EmployeeList";
	}
	
	@GetMapping("/delete")
	public String deleteEmployee(@RequestParam("id") Integer id, Model model, RedirectAttributes attributes) {
		String message = null;
		try {
			service.deleteEmployee(id);
			message = new StringBuffer("Employee '" + id +"' deleted successfully").toString();
		} catch (EmployeeNotFoundException e) {
			e.printStackTrace();
			message = e.getMessage();
		}
		attributes.addAttribute("message", message);
		return "redirect:all";
	}
	
	@GetMapping("/edit")
	public String editEmployee(@RequestParam("id") Integer id, Model model, RedirectAttributes attributes) {
		String page=null;
		try {
			Employee e = service.getEmployeeById(id);
			model.addAttribute("employee", e);
			model.addAttribute("depts", dservice.getAllDepartments());
			EmployeeUtils.createEmployeeHobbiesList(model);
			model.addAttribute("module", "employee");
			page = "EmployeeEdit";
		} catch(EmployeeNotFoundException e) {
			e.printStackTrace();
			attributes.addFlashAttribute("message", e.getMessage());
			page="redirect:all";
		}
		return page;
	}
	
	@PostMapping("/update")
	public String updateEmployee(@ModelAttribute Employee employee, Model model, RedirectAttributes attributes) {
		Integer id = service.saveEmployee(employee);
		attributes.addAttribute("message", new StringBuffer("Employee '" + id + "' updated"));
		return "redirect:all";
	}
	
	@GetMapping("/getEmployees")
	public @ResponseBody String getEmployeesByDeptIdOrAll(@RequestParam Integer deptId) throws DepartmentNotFoundException {
		String json = null;
		List<Object[]> list= service.getAllEmployeesByDeptId(deptId);
		try {
			json = new ObjectMapper().writeValueAsString(list);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
	
}
