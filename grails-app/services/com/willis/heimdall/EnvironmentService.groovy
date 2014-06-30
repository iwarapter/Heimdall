package com.willis.heimdall

import grails.transaction.Transactional

/**
 * Environment Service
 * @author Sion Williams
 */
@Transactional
class EnvironmentService {

    /*
     * For each of the bookings that belong to the environment
     * convert their dates and add them to the event list.
     *
     * @param bookingList A list of Bookings that relate to the Environment
     */
    def returnBookingList(environment) {
        def bookingList = []

        environment.bookings.each { booking ->
            bookingList << [
                    id: booking.id,
                    title: booking.name,
                    allDay: true,
                    start: toJson( booking.startDate ),
                    end: toJson( booking.endDate )
            ]
        }
        bookingList
    }
}
