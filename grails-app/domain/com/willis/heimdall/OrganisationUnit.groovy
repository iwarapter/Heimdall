package com.willis.heimdall

class OrganisationUnit {
	String name
	
	// TODO : replace with User object
	// Should be a 1:m relationship
	String members

    static constraints = {
		name(blank : false, nullable : false)
		members()
    }
}
