package com.osc.exception;

/**
 * Exception generated while reading OSC message if an error occur.
 */
public class OscBadDataTypeRequestException extends Exception {

	private static final long serialVersionUID = 7686624583323846355L;

	/**
	 * Constructor for exception.
	 * 
	 * @param aMessage
	 *            the additional message to exception.
	 */
	public OscBadDataTypeRequestException(String aMessage) {
		super(aMessage);
	}
}
