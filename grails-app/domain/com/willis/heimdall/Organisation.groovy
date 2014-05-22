package com.willis.heimdall

class Organisation {
	String name
	
	// TODO : replace with Group object
	// Should be a 1:m relationship
	String group

    static constraints = {
		name(blank : false, nullable : false)
		group()
    }
}
