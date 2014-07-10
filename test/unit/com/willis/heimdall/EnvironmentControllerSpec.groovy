package com.willis.heimdall

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(EnvironmentController)
@Mock([Environment, Booking])
class EnvironmentControllerSpec extends Specification {
    Date today, todayPlusWeek

    def setup() {
        today = new Date()
        todayPlusWeek = today + 30
    }

    def cleanup() {
    }

    def "Get a environment calendar given their id"() {
        given: "A environment with bookings in the db"
            Environment env1 = new Environment(name : 'webapp-sit',
                    description : 'This is an environment',
                    system : 'Some System',
                    url : 'http://localhost:7001',
                    phaseUsage : 'SIT',
                    vendor : 'myCompany',
                    status : 'Active')
            Booking booking1 = new Booking(name : 'Booking1',
                    startDate : today,
                    endDate : todayPlusWeek)
            Booking booking2 = new Booking(name : 'Booking2',
                    startDate : today + 1,
                    endDate : todayPlusWeek)
            env1.addToBookings( booking1 )
            env1.addToBookings( booking2 )
            env1.save(failOnError: true)

        and: "A id parameter"
            params.id = env1.id

        when: "the calendar is invoked"
            def model = controller.calendar()

        then: "the environment is in the returned model"
            model.environment.name == 'webapp-sit'
            model.environment.bookings.size() == 2
    }

    def "Check that non-existent environment are handled with an error"() {
        given: "the id of a non-existent environment"
            params.id = "this-environment-id-does-not-exist"

        when: "the calendar is invoked"
            controller.timeline()

        then: "a 404 is sent to the browser"
            response.status == 404
    }
}
