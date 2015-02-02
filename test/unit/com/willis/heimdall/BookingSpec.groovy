package com.willis.heimdall

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Shared
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class BookingSpec extends Specification {
    @Shared def today = new Date()

    def "test endDate constraint fails against validator"(){
        given:
        mockForConstraintsTests Booking
        def booking = new Booking(name: 'bad endDate', startDate: today, endDate: today.minus(1))

        expect:
        !booking.validate()
        'validator' == booking.errors['endDate']
    }
}
