package com.bbva.rbvd.lib.r044.impl;

import com.bbva.elara.configuration.manager.application.ApplicationConfigurationService;
import com.bbva.elara.library.AbstractLibrary;
import com.bbva.rbvd.lib.r044.RBVDR044;

/**
 * This class automatically defines the libraries and utilities that it will use.
 */
public abstract class RBVDR044Abstract extends AbstractLibrary implements RBVDR044 {

	protected ApplicationConfigurationService applicationConfigurationService;


	/**
	* @param applicationConfigurationService the this.applicationConfigurationService to set
	*/
	public void setApplicationConfigurationService(ApplicationConfigurationService applicationConfigurationService) {
		this.applicationConfigurationService = applicationConfigurationService;
	}

}