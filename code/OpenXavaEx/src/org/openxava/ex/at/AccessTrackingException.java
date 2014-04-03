package org.openxava.ex.at;

public class AccessTrackingException extends RuntimeException {
	private static final long serialVersionUID = 20140403L;

	public AccessTrackingException() {
		super();
	}

	public AccessTrackingException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccessTrackingException(String message) {
		super(message);
	}

	public AccessTrackingException(Throwable cause) {
		super(cause);
	}
	
}
