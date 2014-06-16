package com.willis.heimdall

import spock.lang.*

/**
 * Integration tests for the Booking Service
 * @author Sion Williams
 */
class BookingServiceIntegSpec extends Specification {
	def booking1, booking2
	def today, todayPlusWeek
	def rangeStart, rangeEnd

    def setup() {
		today = new Date()
		todayPlusWeek = today + 7
		
		booking1 = new Booking(name : 'Booking1',
								startDate : today,
								endDate : todayPlusWeek)
		rangeStart = today - 1
		rangeEnd = today + 14
    }

    def cleanup() {
    }

	/* TODO : Sort this test out!
    void "test number of booking entries in range is correct"() {	
		given:
		def bookingService = new BookingService()
		
		when: "I request a list of dates in a range"
		def dateList = bookingService.findOccurrencesInRange(booking1, rangeStart, rangeEnd)
		
		then: "expect a list with 7 entries"
		dateList.size() == 7
    }*/
}
