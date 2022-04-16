package com.isd.battery.powerplant.controller.beans;

import com.isd.battery.powerplant.controller.validator.ValidationStatus;

/**
 * Very very basic console output bean.
 * @author isakov
 */
public class Console {
	
	/**
	 * Prints the validation status to {@link System.out}.
	 * @param status the status
	 */
	public void out(ValidationStatus status) {
		System.out.println(status);
	}

}
