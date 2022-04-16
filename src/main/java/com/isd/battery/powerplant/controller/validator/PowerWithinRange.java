/**
 * 29.06.2015
 */
package com.isd.battery.powerplant.controller.validator;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Valid power value processor.
 * @author isakov
 */
public class PowerWithinRange implements Processor {
	
	/** Extractor utility */
	private XPathExtractor xPathExtractor;
	
	/**
	 * Creates a new processor.
	 */
	public PowerWithinRange() {
		xPathExtractor = new XPathExtractor();
	}

	/**
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		String profile = xPathExtractor.getProfileName(exchange);
		Integer powerValue = xPathExtractor.getPower(exchange);
		
		ValidationStatus status = new ValidationStatus(isValid(powerValue));

		if (!status.isOk()) {
			status.addIssue("Power value [" + powerValue + "] in [" + profile + "] is out of range [-6 kW ; 6 kW].");
		}
		
		exchange.getIn().setBody(status);
	}
	
	/**
	 * Checks whether the power range is valid.
	 * @param val
	 * @return <code>true</code> if power range is valid
	 */
	private boolean isValid( Integer val ) {
		if (val==null) {
			return false;
		}
		
		return (-6000<=val.intValue() && val.intValue()<6000);
	}

}
