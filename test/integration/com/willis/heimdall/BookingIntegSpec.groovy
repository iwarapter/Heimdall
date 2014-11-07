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
        booking1 = new Booking(name: 'Booking1',
                startDate: today,
                endDate: todayPlusWeek)
    }

    void cleanup() {

    }

    void 'test saving the booking to the db'() {
        given: 'I save the booking'
        booking1.save(failOnError: true)

        expect: 'expect the fields to be populated correctly'
        booking1.id
        Booking.get(booking1.id).name == 'Booking1'
        Booking.get(booking1.id).startDate == today
        Booking.get(booking1.id).endDate == todayPlusWeek
    }
}
