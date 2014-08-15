package com.osc.exception;

/**
 * Exception generated while reading OSC message if an error occur.
 */
public class OscMalformedBundleException extends Exception {
	private static final long serialVersionUID = 3158313455241773428L;

	/**
	 * Constructor for exception.
	 * 
	 * @param aMessage
	 *            the additional message to exception.
	 */
	public OscMalformedBundleException(String aMessage) {
		super(aMessage);
	}
}
