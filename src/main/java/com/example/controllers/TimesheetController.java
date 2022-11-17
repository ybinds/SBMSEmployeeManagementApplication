package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.entity.Timesheet;
import com.example.exception.TimesheetRecordNotFoundException;
import com.example.service.IDepartmentService;
import com.example.service.ITimesheetService;
import com.example.utils.TimesheetUtils;

@Controller
@RequestMapping("/timesheet")
public class TimesheetController {

	@Autowired
	private ITimesheetService service;
	
	@Autowired
	private IDepartmentService dservice;
	
	/*@GetMapping("/all")
	public String showTimesheetList(Model model,
			@RequestParam(value="message", required=false) String message,
			@PageableDefault(page=1, size=5) Pageable pageable
			) {
		Page<Timesheet> page = service.getAllTimesheets(pageable);
		TimesheetUtils tutils = new TimesheetUtils();
		model.addAttribute("tutils", tutils);
		model.addAttribute("page", page);
		model.addAttribute("module", "timesheet");
		model.addAttribute("message", message);
		return "TimesheetList";
	}*/
	
	@GetMapping("/all")
	public String showTimesheetList(Model model,
			@RequestParam(value="message", required=false) String message,
			@PageableDefault(page=1, size=5) Pageable pageable
			) {
		Page<Object[]> page = service.getTimesheets(pageable);
		TimesheetUtils tutils = new TimesheetUtils();
		model.addAttribute("tutils", tutils);
		model.addAttribute("page", page);
		model.addAttribute("module", "timesheet");
		model.addAttribute("message", message);
		return "TimesheetList";
	}
	
	@GetMapping("/add")
	public String showTimesheetAdd(Model model) {
		Timesheet t = new Timesheet();
		model.addAttribute("depts", dservice.getAllDepartments());
		//model.addAttribute("employees", eservice.getAllEmployees());
		model.addAttribute("timesheet", t);
		model.addAttribute("module", "timesheet");
		return "TimesheetAdd";
	}
	
	@PostMapping("/save")
	public String saveTimesheet(@ModelAttribute Timesheet timesheet, RedirectAttributes attributes) {
		Integer id = service.saveTimesheet(timesheet);
		StringBuffer message = new StringBuffer("Timesheet '" + id + "' Created");
		attributes.addAttribute("message", message);
		return "redirect:all";
	}
	
	@GetMapping("/edit")
	public String showEditDept(@RequestParam("id") Integer id, 
			Model model,
			RedirectAttributes attributes) throws TimesheetRecordNotFoundException {
		String page = null;
		try {
			Timesheet t = service.getTimesheetById(id);
			System.out.println("Timesheet is " + t);
			model.addAttribute("depts", dservice.getAllDepartments());
			model.addAttribute("timesheet", t);
			model.addAttribute("module", "timesheet");
			page = "TimesheetEdit";
		} catch (TimesheetRecordNotFoundException te) {
			te.printStackTrace();
			attributes.addAttribute("message", te.getMessage());
			page = "redirect:all";
		}
		return page;
	}
	
	@PostMapping("/update")
	public String updateTimesheet(@ModelAttribute Timesheet timesheet, RedirectAttributes attributes) {
		Integer id = service.updateTimesheet(timesheet);
		StringBuffer message = new StringBuffer("Timesheet '" + id + "' Updated");
		attributes.addAttribute("message", message);
		return "redirect:all";
	}
	
	@GetMapping("/delete")
	public String deleteTimesheet(@RequestParam Integer id, RedirectAttributes attributes) {
		String message = null;
		try {
			service.deleteTimesheet(id);
			message = new StringBuffer("Timesheet '" + id + "' Deleted").toString();
		} catch (TimesheetRecordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message = e.getMessage();
		}
		attributes.addAttribute("message", message);
		return "redirect:all";
	}
}
