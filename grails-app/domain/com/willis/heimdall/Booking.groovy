package com.willis.heimdall

class Booking {
    String name
    Date startDate
    Date endDate

    static belongsTo = [User, Environment]

    static constraints = {
        name blank: false
    }
}
