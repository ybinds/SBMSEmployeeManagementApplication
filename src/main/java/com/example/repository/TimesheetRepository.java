package com.example.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.Timesheet;

public interface TimesheetRepository extends JpaRepository<Timesheet, Integer> {
	
	@Query("SELECT t.timeId, e.empName, t.timeStart, t.timeEnd FROM Timesheet t INNER JOIN t.empId AS e")
	Page<Object[]> getAllTimesheets(Pageable pageable);
	
	@Query("SELECT t.timeId, t.empId, t.timeStart, t.timeEnd, TIME_TO_SEC(TIMEDIFF(t.timeEnd, t.timeStart))/3600 as hour, MOD(TIME_TO_SEC(TIMEDIFF(t.timeEnd, t.timeStart))/60, 60) as minutes FROM Timesheet t")
	List<Object[]> getTimesheets();
	
	@Modifying
	@Query("UPDATE Timesheet SET timeStart=:start, timeEnd=:end WHERE timeId=:id")
	Integer updateTimeById(Date start, Date end, Integer id);
}
