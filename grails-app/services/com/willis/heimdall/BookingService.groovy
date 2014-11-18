package com.willis.heimdall

import grails.transaction.Transactional
import groovy.json.JsonBuilder

@Transactional
class BookingService {

    /*
     * Return a list of Bookings for a given Environment
     *
     * @param environment
     * @return bookingList
     */

    def listBookings(Environment environment) {
        def bookingList = []

        environment.bookings.each { booking ->
            bookingList << [
                    id   : booking.id,
                    title: booking.name,
                    allDay: true,
                    start: booking.startDate,
                    end  : booking.endDate
            ]
        }
        return bookingList
    }

    /*
     *  Return a list of Bookings for a given Environment in JSON format
     *
     * @param environment
     * @return bookingListJson
     */

    def listBookingsJSON(Environment environment) {
        def bookingList = []

        environment.bookings.each { booking ->
            bookingList << [
                    id   : booking.id,
                    title: booking.name,
                    allDay: true,
                    start: booking.startDate,
                    end  : booking.endDate
            ]
        }
        def bookingListJson = new JsonBuilder(bookingList)
        return bookingListJson
    }

    def findOccurrencesInRange(Booking booking, Date rangeStart, Date rangeEnd) {
        def dates = []

        booking.startDate.upto(booking.endDate) {
            if (it >= rangeStart || it <= rangeEnd) {
                dates.add(it)
            }
        }
        return dates
    }
}
