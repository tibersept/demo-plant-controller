/**
 * 29.06.2015
 */
package com.isd.battery.powerplant.controller.validator;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Valid time interval Camel processor.
 * @author isakov
 */
public class ValidTimeInterval implements Processor {

	/** Extractor utility */
	private XPathExtractor xPathExtractor;
	
	/**
	 * Creates a new processor.
	 */
	public ValidTimeInterval() {
		xPathExtractor = new XPathExtractor();
	}
	
	/**
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		String profile = xPathExtractor.getProfileName(exchange);
		String startTime = xPathExtractor.getStart(exchange);
		String endTime = xPathExtractor.getEnd(exchange);
		
		ValidationStatus status = new ValidationStatus(isValid(startTime, endTime));
		if (!status.isOk()) {
			status.addIssue("Profile in [" + profile + "] specifies an invalid time interval ["+startTime+";"+endTime+"].");
		}
		
		exchange.getIn().setBody(status);
	}

	/**
	 * Checks whether the time interval is valid.
	 * @param startTime the start timestamp
	 * @param endTime the end timestamp
	 * @return <code>true</code> if time interval is valid
	 */
	private boolean isValid(String startTime, String endTime) {
	
		return startTime.compareTo(endTime) < 0;
	}

}
