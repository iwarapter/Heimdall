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
	
	System stakeholder
	
    static constraints = {
		firstName( blank : false, nullable : false )
		lastName( blank : false, nullable : false )
		email( email : true, blank: false )
		role( inList : ['Admin', 'Manager', 'Requestor'] )	
		status( inList : ['Pending', 'Active'] )
		stakeholder( blank : true, nullable: true )
    }
}
