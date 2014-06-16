package com.willis.heimdall

import grails.transaction.Transactional

@Transactional
class BookingService {
	
    public def findOccurrencesInRange(Booking booking, Date rangeStart, Date rangeEnd) {
        def dates = []		
		
		booking.startDate.upto(booking.endDate) {
			if( it >= rangeStart || it <= rangeEnd ) {
				dates.add( it )
			}
		}
        dates // return
    }
}
