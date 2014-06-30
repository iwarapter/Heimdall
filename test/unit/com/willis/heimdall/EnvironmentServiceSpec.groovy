package com.willis.heimdall

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(EnvironmentService)
class EnvironmentServiceSpec extends Specification {
    def environmentService
    def env1
    def booking1, booking2

    def setup() {
        booking1 = Mock( Booking )
        booking2 = Mock( Booking )

        env1 = new Environment( name : 'webappdb-sit',
                description : 'System Integration Testing database for Webapps',
                phaseUsage : 'SIT',
                status : 'Active',
                bookings: [ booking1, booking2 ] )

    }

    def cleanup() {
    }

    void "test returnBookingList"() {
        when: "we pass an environment to taskService.addDefaultTasks"
            def e = environmentService.returnBookingList( env1 )


        then: "the environment will have 2 bookings"
            e.size() == 2
    }
}
