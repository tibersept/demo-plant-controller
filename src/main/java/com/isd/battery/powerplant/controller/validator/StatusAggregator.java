/**
 * 29.06.2015
 */
package com.isd.battery.powerplant.controller.validator;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

/**
 * Validation status aggregator.
 * @author isakov
 */
public class StatusAggregator implements AggregationStrategy {

	/**
	 * @see org.apache.camel.processor.aggregate.AggregationStrategy#aggregate(org.apache.camel.Exchange, org.apache.camel.Exchange)
	 */
	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		if (oldExchange==null) {
			return newExchange;
		}
		
		ValidationStatus aggregated = oldExchange.getIn().getBody(ValidationStatus.class);
		ValidationStatus newStatus = newExchange.getIn().getBody(ValidationStatus.class);
	
		aggregated.setOk(aggregated.isOk() && newStatus.isOk());
		aggregated.addIssues(newStatus.getIssues());
		
		oldExchange.getIn().setBody(aggregated);
		return oldExchange;
	}

}
