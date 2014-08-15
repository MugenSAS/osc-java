package com.osc.exception;

/**
 * Exception generated while reading OSC message if an error occur.
 */
public class OscMalformedMessageException extends Exception {
	private static final long serialVersionUID = -3852108679519395974L;

	/**
	 * Constructor for exception.
	 * 
	 * @param aMessage
	 *            the additional message to exception.
	 */
	public OscMalformedMessageException(String aMessage) {
		super(aMessage);
	}
}