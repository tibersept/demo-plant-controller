/**
 * 29.06.2015
 */
package com.isd.battery.powerplant.controller.validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Validation status info.
 * @author isakov
 */
public class ValidationStatus {

	/** Indicates the status */
	private boolean ok;
	/** Messages explaining issues if status is not ok */
	private List<String> issues;
	
	/**
	 * Creates a new validation status.
	 */
	public ValidationStatus( boolean ok ) {
		this.ok = ok;
		this.issues = new ArrayList<String>();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ValidationStatus[ok:[");
		sb.append(ok);
		sb.append("] issues:");
		sb.append(issues);
		sb.append("]");		
		return sb.toString();
	}
	
	/**
	 * Returns <code>true</code> if the validation passed successfully.
	 * @return the status
	 */
	public boolean isOk() {
		return ok;
	}
	
	/**
	 * Returns the validation issues.
	 * @return the issues
	 */
	public List<String> getIssues() {
		return issues;
	}
	
	/**
	 * Resets the validation status.
	 */
	public void reset() {
		this.ok = false;
		this.issues.clear();
	}
	
	/**
	 * Sets the status. 
	 * @param ok <code>true</code> if validation passed successfully
	 */
	protected void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * Adds a validation issue.
	 * @param issue the issue
	 */
	protected void addIssue(String issue) {
		this.issues.add(issue);
	}
	
	/**
	 * Adds a list of issues to the current list of issues.
	 * @param issues the list of issues to be added
	 */
	protected void addIssues(List<String> issues) {
		this.issues.addAll(issues);
	}
}
