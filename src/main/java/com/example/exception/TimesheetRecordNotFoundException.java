package com.example.exception;

public class TimesheetRecordNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TimesheetRecordNotFoundException(){
		super();
	}
	
	public TimesheetRecordNotFoundException(String message){
		super(message);
	}
}
