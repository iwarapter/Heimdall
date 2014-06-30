package com.willis.heimdall

/**
 * User model
 * @author Sion Williams
 */
class User {
	String firstName
	String lastName
	String email
	String role
	String status
	
	static belongsTo = [ system : System, orgUnit : OrganisationGroup]
	static hasMany = [ bookings : Booking ]
	
    static constraints = {
		firstName( blank : false, nullable : false )
		lastName( blank : false, nullable : false )
		email( email : true, nullable : false )
		role( inList : ['Admin', 'Manager', 'Requestor'] )	
		status( inList : ['Pending', 'Active'] )
		system( blank : true, nullable: true )
		orgUnit( blank : true, nullable: true )
		bookings( nullable : true )
    }
}
