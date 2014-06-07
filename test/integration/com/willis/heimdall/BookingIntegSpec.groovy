package com.willis.heimdall

import spock.lang.Specification

/**
 * Integration tests for the Booking model
 * @author Sion Williams
 */
class BookingIntegSpec extends Specification {
	def booking1, booking2
	def today = new Date()
	def todayPlusWeek = today + 30

    def setup() {
		booking1 = new Booking(name : 'Booking1',
								startDate : today,
								endDate : todayPlusWeek)
		booking2 = new Booking(name : 'Booking2',
								startDate : today + 1,
								endDate : todayPlusWeek)
    }

    void 'test saving the booking to the db'() {
		given: 'I save the booking'
			booking1.save( failOnError : true )
		
		expect: 'expect the fields to be populated correctly'
			booking1.id
			Booking.get( booking1.id ).name == 'Booking1'
			Booking.get( booking1.id ).startDate == today
			Booking.get( booking1.id ).endDate == todayPlusWeek
    }
	
	void 'test Users creating bookings'(){
		given:
			def user = new User( firstName: 'Sion',
						lastName: 'Williams',
						email: 'my@email.co.uk',
						role: 'Admin',
						status: 'Active' ).save()
		
		when:
			user.addToBookings( booking1 )
			user.addToBookings( booking2 )
			
		then:
			User.get(user.id).bookings.size() == 2
	}
}
