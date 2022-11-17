package com.example.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimesheetUtils {

	public String getTimeDiff(Date start, Date end) {
	    long diffInMillies = (end.getTime() - start.getTime());
	    long minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillies);
	    long hours = TimeUnit.MINUTES.toHours(minutes); 
	    return String.format("%d:%02d", (hours-1), (minutes%60));
	}
}
