package com.willis.heimdall

import org.joda.time.DateTime

import grails.converters.JSON
import java.text.SimpleDateFormat

/**
 * Environment Controller
 * @author Sion Williams
 */
class EnvironmentController {

    BookingService bookingService

    def scaffold = true
	
	def calendar = {
		def environment = Environment.findById(params.id)
		[ environment : environment ]
	}

    def bookingList = {
        def environment = Environment.findById(params.id)
        def eventList = []
        def displayDateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

        /*
         * For each of the bookings that belong to the environment
         * convert their dates and add them to the event list.
         */
        environment.bookings.each { booking ->

            DateTime startDate = new DateTime(booking.startDate)
            DateTime endDate = new DateTime(booking.endDate)

            eventList << [
                    id: booking.id,
                    title: booking.name,
                    allDay: true,
                    start: displayDateFormatter.format(startDate.toDate()),
                    end: displayDateFormatter.format(endDate.toDate())
            ]
        }

        withFormat {
            html {
                [eventInstanceList: eventList]
            }
            json {
                render eventList as JSON
            }
        }
    }
}
