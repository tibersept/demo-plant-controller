/**
 * 29.06.2015
 */
package com.isd.battery.powerplant.controller.validator;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Valid power timestamp processor.
 * @author isakov
 */
public class TimestampWithinRange implements Processor {

	/** Extractor utility */
	private XPathExtractor xPathExtractor;
	
	/**
	 * Creates a new processor.
	 */
	public TimestampWithinRange() {
		xPathExtractor = new XPathExtractor();
	}
	
	/**
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		String profile = xPathExtractor.getProfileName(exchange);
		String timestamp = xPathExtractor.getTimestamp(exchange);
		String startTime = exchange.getProperty("start", String.class);
		String endTime = exchange.getProperty("end", String.class);
		
		ValidationStatus status = new ValidationStatus(isValid(timestamp, startTime, endTime));
		
		if (!status.isOk()) {
			status.addIssue("Timestamp ["+timestamp+"] in profile ["+profile+"] is outside the interval ["+startTime+";"+endTime+"]");
		}
		
		exchange.getIn().setBody(status);
	}
	
	/**
	 * Checks whether the timestamp lies within the time interval of the profile.
	 * @param timestamp the timestamp
	 * @param startTime the start timestamp of the profile
	 * @param endTime the end timestamp of the profile
	 * @return <code>true</code> if timestamp is within profile interval
	 */
	public boolean isValid(String timestamp, String startTime, String endTime) {
		return (startTime.compareTo(timestamp)<=0 && endTime.compareTo(timestamp)>=0);
	}

}
