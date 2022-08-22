package org.zerock.myapp.exception;


public class ControllerException extends Exception {
	private static final long serialVersionUID = 1L;

	public ControllerException (String message) {
		super(message);
	} // constructor #1
	
	public ControllerException(Exception oe) {
		super(oe);
	} // constructor #2

} // end class
