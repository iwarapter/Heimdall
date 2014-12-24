package com.willis.heimdall

class Environment {
    String name

    static hasMany = [bookings: Booking]

    static constraints = {
        name blank: false
    }
}
