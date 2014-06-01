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
	
	static hasMany = [ integrations : Environment ]

    static constraints = {
		name( blank : false, unique : true )
		description( maxSize : 250 )
		integrations( nullable : true )
		system( maxSize : 100, nullable : true )		
		url( nullable : true )
		phaseUsage( inList : ['DEV', 'SIT', 'UAT', 'PSUP', 'PROD'] )
		vendor( nullable : true )
		status( inList : ['Under Build/Config', 'Decommissioned', 'Active'] )
    }
}
