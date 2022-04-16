/**
 * 29.06.2015
 */
package com.isd.battery.powerplant.controller.validator;

import org.apache.camel.Exchange;
import org.apache.camel.builder.xml.XPathBuilder;

/**
 * Utility class for extraction of Younicos relevant data from XML files defined
 * by the XSD schema definition in "Profile.xsd"
 * @author isakov
 */
public class XPathExtractor {
	
	/** Younicos namespace */
	private final static String YOUNICOS_NS = "http://www.younicos.com/namespace";
	
	/**
	 * Returns the profile name, i.e. the actual XML file name.
	 * @param exchange the exchange
	 * @return the profile name
	 */
	public String getProfileName(Exchange exchange) {
		return exchange.getIn().getHeader("CamelFileName",String.class);
	}
	
	/**
	 * Returns the start time of the profile.
	 * @param exchange the input exchange
	 * @return the start time
	 */
	public String getStart(Exchange exchange) {
		return getStartExpression().evaluate(exchange, String.class);
	}
	
	/**
	 * Returns the end time of the profile.
	 * @param exchange the input exchange
	 * @return the end time
	 */
	public String getEnd(Exchange exchange) {
		return getEndExpression().evaluate(exchange, String.class);
	}
	
	/**
	 * Returns the power value initiation timestamp.
	 * @param exchange the exchange
	 * @return the timestamp (XML timestamp)
	 */
	public String getTimestamp(Exchange exchange) {
		return getBase("//younicos:timestamp/text()").evaluate(exchange, String.class);
	}
	
	/**
	 * Returns the power value contained in the current exchange.
	 * @param exchange the input exchange
	 * @return the power value
	 */
	public Integer getPower(Exchange exchange) {
		return getBase("//younicos:power_kW/text()").evaluate(exchange, Integer.class);
	}
	
	/**
	 * Returns the XPathBuilder for the power splitter expression.
	 * @return the power splitter expression XPathBuilder object
	 */
	public XPathBuilder getPowerSplitter() {
		return getBase("//younicos:power");
	}
	
	/**
	 * Returns the XPathBuilder for the start expression.
	 * @return the start expression XPathBuilder object
	 */
	public XPathBuilder getStartExpression() {
		return getBase("//younicos:start/text()");
	}
	
	/**
	 * Returns the XPathBuilder for the end expression.
	 * @return the end expression XPathBuilder object
	 */
	public XPathBuilder getEndExpression() {
		return getBase("//younicos:end/text()");
	}
	
	/**
	 * Returns an XPathBuilder for the expression with namespaces set.
	 * @param expression the expression
	 * @return XPathBuilder with namespaces
	 */
	private XPathBuilder getBase(String expression) {
		XPathBuilder xpathBuilder = XPathBuilder.xpath(expression);
		return xpathBuilder.namespace("younicos", YOUNICOS_NS); 		
	}

}
