package com.willis.heimdall

import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import org.joda.time.DateTime
import spock.lang.Shared
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(Booking)
@TestMixin(GrailsUnitTestMixin)
class BookingSpec extends Specification {
    @Shared def today = new DateTime()
    @Shared def todayPlusWeek = today.plusWeeks(1)

    def setup() {
    }

    void cleanup() {

    }

    def "test endTime constraint fails against validator"() {
        given:
        mockForConstraintsTests Booking
        def booking = new Booking(name: 'Bad endTime',
                startTime: today.toDate(),
                endTime: today.minusDays(1))

        expect:
        !booking.validate()
        'validator' == booking.errors['endTime']

    }

    def 'test toString of booking'() {
        given:
        def booking = new Booking(name: 'Correct toString',
                startTime: today.toDate(),
                endTime: todayPlusWeek.toDate())

        expect:
        booking.toString() == 'Correct toString'
    }

}
