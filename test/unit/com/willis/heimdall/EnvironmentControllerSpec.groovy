package com.willis.heimdall

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification


@TestFor(EnvironmentController)
@Mock([Environment, Booking])
class EnvironmentControllerSpec extends Specification {
    Date today, todayPlusWeek
    Environment env1
    Booking booking1, booking2

    def setup() {
        today = new Date()
        todayPlusWeek = today + 30
        env1 = new Environment(name: 'webapp-sit',
                description: 'This is an environment',
                system: 'Some System',
                url: 'http://localhost:7001',
                phaseUsage: 'SIT',
                vendor: 'myCompany',
                status: 'Active')
        booking1 = new Booking(name: 'Booking1',
                startTime: today,
                endTime: todayPlusWeek)
        booking2 = new Booking(name: 'Booking2',
                startTime: today + 1,
                endTime: todayPlusWeek)
        env1.addToBookings booking1
        env1.addToBookings booking2
        env1.save failOnError: true
    }

    def cleanup() {
    }

    def "Get a environment calendar given their id"() {
        given: "A id parameter"
        params.id = env1.id

        when: "the calendar is invoked"
        def model = controller.calendar()

        then: "the environment is in the returned model"
        model.environment.name == 'webapp-sit'
        model.environment.bookings.size() == 2
    }

    def "Check that non-existent environment are handled with an error"() {
        given: "the id of a non-existent environment"
        def mockEnv = Mock(Environment)
        params.id = mockEnv.id

        when: "the calendar is invoked"
        controller.calendar()

        then: "a 404 is sent to the browser"
        response.status == 404
    }

    def "Call bookingList service from controller bookingList action"() {
        setup:
        def bookingService = Mock(BookingService)
        controller.bookingService = bookingService

        when:
        params.id = env1.id
        controller.bookingList()

        then:
        response.status != 404
        1 * bookingService.listBookingsJSON(_)
    }

}
