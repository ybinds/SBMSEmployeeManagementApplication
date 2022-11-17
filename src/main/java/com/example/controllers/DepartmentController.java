package com.example.controllers;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.entity.Department;
import com.example.exception.DepartmentNotFoundException;
import com.example.service.IDepartmentService;

@Controller
@RequestMapping("/department")
public class DepartmentController {

	@Autowired
	private IDepartmentService service;
	
	@GetMapping("/all")
	public String showDepartmentList(
			@RequestParam(value="message", required=false) String message,
			@PageableDefault(page=1, size=5, sort="deptId",direction = Sort.Direction.ASC) Pageable pageable,
			Model model) {
		Page<Department> page = service.getAllDepartments(pageable);
		model.addAttribute("page", page);
		if(page.getSort().isSorted()) {
			model.addAttribute("revdirection", (page.getSort().toList().get(0).getDirection().toString()=="ASC")?"desc":"asc");
			model.addAttribute("direction", page.getSort().toList().get(0).getDirection().toString().toLowerCase());
			model.addAttribute("property", page.getSort().toList().get(0).getProperty());
		} 
		model.addAttribute("message", message);
		model.addAttribute("module", "department");
		return "DepartmentList";
	}
	
	@GetMapping("/add")
	public String showAddDept(Model model) {
		Department d = new Department();
		model.addAttribute("dept",d);
		model.addAttribute("module", "department");
		return "DepartmentAdd";
	}
	
	@PostMapping("/save")
	public String saveDept(@ModelAttribute Department dept, RedirectAttributes attributes) {
		String word = null;
		if(dept.getDeptId()!=null) {
			word = "' Updated";
		} else{
			word = "' Created";
		}
		Integer id = service.saveDepartment(dept);
		StringBuffer message = new StringBuffer("Department '" + id + word);
		attributes.addAttribute("message", message);
		return "redirect:all";
	}
	
	@GetMapping("/edit")
	public String showEditDept(@RequestParam("id") Integer id, Model model, RedirectAttributes attribute) {
		String page = null;
		try {
			Department d = service.getDeptById(id);
			model.addAttribute("dept", d);
			model.addAttribute("module", "department");
			page = "DepartmentAdd";
		} catch (DepartmentNotFoundException de) {
			de.printStackTrace();
			attribute.addAttribute("message", de.getMessage());
			page = "redirect:all";
		}
		return page;
	}
	
	@GetMapping("/delete")
	public String deleteDepartment(@RequestParam("id") Integer id, RedirectAttributes attributes) {
		String message = null;
		try {
			service.deleteDepartment(id);
			message = new StringBuffer("Department '" + id + "' deleted").toString();
		} catch (DepartmentNotFoundException de) {
			de.printStackTrace();
			message = de.getMessage();
		}
		attributes.addAttribute("message", message);
		return "redirect:all";
	}
}
