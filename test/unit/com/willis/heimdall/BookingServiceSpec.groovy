package com.willis.heimdall

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import groovy.json.JsonOutput
import spock.lang.Specification

@TestFor(BookingService)
@Mock([Booking, Environment])
class BookingServiceSpec extends Specification {
    Date today, todayPlusWeek
    Environment env1
    Booking booking1, booking2


    def setup(){
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
        env1.save( failOnError: true )
    }

    def "ListBookings"() {
        when:
            env1.id != null
            def bookingList = service.listBookings( env1 )

        then: "expect the return of the ListBooking method to have a size of 2"
            bookingList.size() == 2
    }


    def "ListBookingsJSON"() {
        when:
            env1.id != null
            def bookingList = service.listBookingsJSON(env1)

        then:
            println JsonOutput.prettyPrint( JsonOutput.toJson( bookingList ))
            bookingList.content.title[0] == "Booking1"
            bookingList.content.title[1] == "Booking2"
    }
}
