package com.willis.heimdall

class OrganisationGroup {
	String name
	
	static hasMany = [ members : User ]

    static constraints = {
		name( blank : false, nullable : false )
    }
	
}
