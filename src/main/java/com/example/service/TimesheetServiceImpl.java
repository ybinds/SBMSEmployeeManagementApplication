package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Timesheet;
import com.example.exception.TimesheetRecordNotFoundException;
import com.example.repository.TimesheetRepository;

@Service
@Transactional
public class TimesheetServiceImpl implements ITimesheetService {

	@Autowired
	private TimesheetRepository repo;
	
	@Override
	public Integer saveTimesheet(Timesheet t) {
		return repo.save(t).getTimeId();
	}

	@Override
	public void deleteTimesheet(Integer id) throws TimesheetRecordNotFoundException {
		repo.delete(this.getTimesheetById(id));
	}

	@Override
	public Page<Timesheet> getAllTimesheets(Pageable pageable) {
		pageable = PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize());
		repo.getAllTimesheets(pageable).stream().map(obj-> obj[0]+"--"+obj[1]+"--"+obj[2]+"--"+obj[3]).forEach(System.out::println);
		return repo.findAll(pageable);
	}
	
	@Override
	public Page<Object[]> getTimesheets(Pageable pageable) {
		pageable = PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize());
		//repo.getAllTimesheets(pageable).stream().map(obj-> obj[0]+"--"+obj[1]+"--"+obj[2]+"--"+obj[3]).forEach(System.out::println);
		return repo.getAllTimesheets(pageable);
	}
	
	@Override
	public Timesheet getTimesheetById(Integer id) throws TimesheetRecordNotFoundException {
		return repo.findById(id).orElseThrow(()-> new TimesheetRecordNotFoundException("TIMESHEET NOT FOUND"));
	}

	@Override
	public Integer updateTimesheet(Timesheet t) {
		return repo.updateTimeById(t.getTimeStart(), t.getTimeEnd(), t.getTimeId());
	}

}
