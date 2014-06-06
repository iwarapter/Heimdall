package com.willis.heimdall

/**
 * Booking model
 * @author Sion Williams
 */
class Booking {
	String name
	Date startDate
	Date endDate

    static constraints = {
		name( blank: false )
		startDate( blank: false, min: new Date() )
		endDate( blank: false, min: new Date() )
    }
}
