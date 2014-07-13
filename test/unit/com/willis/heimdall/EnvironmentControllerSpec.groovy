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
        env1 = new Environment(name : 'webapp-sit',
                description : 'This is an environment',
                system : 'Some System',
                url : 'http://localhost:7001',
                phaseUsage : 'SIT',
                vendor : 'myCompany',
                status : 'Active')
        booking1 = new Booking(name : 'Booking1',
                startDate : today,
                endDate : todayPlusWeek)
        booking2 = new Booking(name : 'Booking2',
                startDate : today + 1,
                endDate : todayPlusWeek)
        env1.addToBookings( booking1 )
        env1.addToBookings( booking2 )
        env1.save(failOnError: true)
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

    /*
    // TODO : Fix this unit test mofo!
    def "Check that non-existent environment are handled with an error"() {
        given: "the id of a non-existent environment"
            params.id = "this-environment-id-does-not-exist"

        when: "the calendar is invoked"
            controller.calendar()

        then: "a 404 is sent to the browser"
            response.status == 404
    }*/

    /*
    // TODO : Fix this unit test mofo!
    def "test the booking list action"() {
        given: "A id parameter"
            def mockBookingService = Mock( BookingService )
            1 * mockBookingService.listBookingsJSON( env1 ) >> new String( 'some JSON fake' )
            params.id = env1.id


        when: "the calendar is invoked"
            def result = controller.bookingList()

        then: "the environment is in the returned model"
            result == 'some JSON fake'
    }*/

}
