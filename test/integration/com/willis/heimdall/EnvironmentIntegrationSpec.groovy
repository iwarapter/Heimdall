package com.willis.heimdall

import grails.test.spock.IntegrationSpec

class EnvironmentIntegrationSpec extends IntegrationSpec {
    Date now

    def setup() {
        now = new Date()
    }

    def "Bookings linked to an Environment can be retrieved"() {
        given: "an environment with several bookings"
        def env = new Environment(name: 'env1')
        env.addToBookings(new Booking(name: 'booking1', startDate: now, endDate: now.plus(10)))
        env.addToBookings(new Booking(name: 'booking2', startDate: now, endDate: now.plus(20)))
        env.addToBookings(new Booking(name: 'booking3', startDate: now, endDate: now.plus(30)))
        env.save(failOnError: true)

        when: "the Environment is retrieved by its id"
        def foundEnv = Environment.get(env.id)
        def sortedBookingNames = foundEnv.bookings.collect {
            it.name
        }.sort()

        then: "the Bookings appear on the retrieved User"
        sortedBookingNames == ['booking1', 'booking2', 'booking3']
    }

    def "Bookings belongsTo an Environment and should cascade when deleted"() {
        given: "an environment with several bookings"
        def originalBookingCount = Booking.list().size()
        def env = new Environment(name: 'env1')
        env.addToBookings(new Booking(name: 'booking1', startDate: now, endDate: now.plus(10)))
        env.addToBookings(new Booking(name: 'booking2', startDate: now, endDate: now.plus(20)))
        env.addToBookings(new Booking(name: 'booking3', startDate: now, endDate: now.plus(30)))

        and: "the environment and bookings are saved to the database"
        env.save(failOnError: true)
        env.id
        assert Booking.list().size() != originalBookingCount

        when: "the environment is deleted"
        env.delete(flush: true)

        then: "the booking count in the database is returned to its original value"
        Booking.list().size() == originalBookingCount
    }
}
