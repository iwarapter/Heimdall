package com.willis.heimdall

/**
 * Environment model
 * @author Sion Williams
 */
class Environment {
	String name
	String description
	String system
	String integratedWith
	String url
	String phaseUsage
	String vendor
	String status

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
