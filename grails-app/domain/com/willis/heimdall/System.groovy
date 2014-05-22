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

    static constraints = {
		name(blank : false, nullable : false)
		description(maxSize : 250)
		vendor()
		status(inList : ['Active', 'Inactive']) 
    }
}
