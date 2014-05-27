package com.willis.heimdall

/**
 * System model
 * @author Sion Williams
 */
class System {
	String name
	String description
	String vendor
	String status
	
	// TODO : replace with Group object. Should be a 1:1 relationship
	String organisationUnit
	

    static constraints = {
		name( blank : false, nullable : false )
		description( maxSize : 250 )
		vendor()
		status( inList : ['Active', 'Inactive'] )
		organisationUnit( nullable : true )
    }
	
	static hasMany = [ users : User ]
}
