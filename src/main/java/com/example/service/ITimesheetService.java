package com.example.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.entity.Timesheet;
import com.example.exception.TimesheetRecordNotFoundException;

public interface ITimesheetService {
	
	Integer saveTimesheet(Timesheet t);
	Page<Timesheet> getAllTimesheets(Pageable pageable);
	Page<Object[]> getTimesheets(Pageable pageable);
	Timesheet getTimesheetById(Integer id) throws TimesheetRecordNotFoundException;
	void deleteTimesheet(Integer id) throws TimesheetRecordNotFoundException;
	Integer updateTimesheet(Timesheet t);
}
