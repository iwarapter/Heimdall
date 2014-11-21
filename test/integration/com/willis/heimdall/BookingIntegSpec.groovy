package com.willis.heimdall

import grails.test.mixin.TestFor
import org.joda.time.DateTime
import spock.lang.Shared
import spock.lang.Specification

/**
 * Integration tests for the Booking model
 * @author Sion Williams
 */
@TestFor(Booking)
class BookingIntegSpec extends Specification {
    @Shared def today = new DateTime()
    @Shared def todayPlusWeek = today.plusWeeks(1)

    def 'test saving a booking to the db'() {
        given: 'a new booking booking'
        def booking = new Booking(name: 'my booking',
                startTime: today.toDate(),
                endTime: todayPlusWeek.toDate())

        when: 'the booking is saved'
        booking.save()

        then: 'it can be successfully found in the database'
        booking.errors.errorCount == 0
        booking.id != null
        Booking.get(booking.id).name == 'my booking'
        Booking.get(booking.id).startTime == today.toDate()
        Booking.get(booking.id).endTime == todayPlusWeek.toDate()

    }
}
