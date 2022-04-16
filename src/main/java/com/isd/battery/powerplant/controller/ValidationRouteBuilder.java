package com.isd.battery.powerplant.controller;

import org.apache.camel.ValidationException;
import org.apache.camel.builder.RouteBuilder;

import com.isd.battery.powerplant.controller.validator.PowerWithinRange;
import com.isd.battery.powerplant.controller.validator.StatusAggregator;
import com.isd.battery.powerplant.controller.validator.TimestampWithinRange;
import com.isd.battery.powerplant.controller.validator.ValidTimeInterval;
import com.isd.battery.powerplant.controller.validator.XPathExtractor;

/**
 * A Camel Java DSL Router.
 */
public class ValidationRouteBuilder extends RouteBuilder {

    /**
     * Let's configure the Camel routing rules using Java code...
     */
    public void configure() {
    	XPathExtractor xPathExtractor = new XPathExtractor();
    	
    	// Initial check: XSD validation - useless cause it doesn't give
    	// any real feedback. The power value restrictions [-6kW, 6kW] need to 
    	// disappear in order to be able to verify the consistency of the XML document
    	// structure only. Not doing that here, it's just a demo.
    	// Basic idea: 
    	//		1. Verify XML integrity according to XSD, on fail -> feedback and stop
    	//		2. XML structure ok -> validate the values with feedback 
        from("file:src/data?noop=true")
        	.doTry()
        		.to("validator:Profile.xsd")
        		.log("Profile ${file:name} is valid according to XSD definition.")
        	.doCatch(ValidationException.class)
        		.log("Profile ${file:name} is invalid according to XSD definition")
        	.doFinally()
        		.to("direct:detailedValidation")
        	.end().
        	to("direct:validationComplete");
        
        // perform detailed validation with feedback
        from("direct:detailedValidation")
        	.multicast(new StatusAggregator())
        		.to("direct:timeInterval", "direct:powerRange", "direct:timestampRange")
        	.end();
        
        // validates the time interval
        from("direct:timeInterval")
        	.process(new ValidTimeInterval())
        	.log("Time interval validated.");
     
        // The next two rules can of course be grouped together but the purpose is to
        // demonstrate use of properties separately ... and to have more tasks for the
        // multicast so we split twice
        
        // validates the power ranges
        from("direct:powerRange")
        	.split(xPathExtractor.getPowerSplitter(), new StatusAggregator())
        		.process(new PowerWithinRange())
        	.end()
        	.log("Power ranges validated.");
        
        // validates the timestamps
        from("direct:timestampRange")
        	.setProperty("start", xPathExtractor.getStartExpression())
        	.setProperty("end", xPathExtractor.getEndExpression())
        	.split(xPathExtractor.getPowerSplitter(), new StatusAggregator())
        		.process(new TimestampWithinRange())
        	.end()
        	.log("Timestamps validated.");
        
        // just console output for now
        from("direct:validationComplete").to("bean:console?method=out").log("Finalized");
    }
    
}
