package com.willis.heimdall

/**
 * Environment model
 * @author Sion Williams
 */
class Environment {
	String name
	String description
	String system	
	String url
	String phaseUsage
	String vendor
	String status
	
	// Needs to be a hasMany Environments relationship
	String integratedWith

    static constraints = {
		name(blank : false, unique : true)
		description(maxSize : 250)
		system(maxSize : 100)
		integratedWith()
		url()
		phaseUsage(inList : ['DEV', 'SIT', 'UAT', 'PSUP', 'PROD'])
		vendor()
		status(inList : ['Under Build/Config', 'Decommissioned', 'Active'])
    }
}
