package com.willis.heimdall

class Booking {
    String name
    Date startDate
    Date endDate

    static belongsTo = [User, Environment]

    static constraints = {
        name blank: false
        endDate required: true, validator: { val, obj -> val > obj.startDate }
    }

    String toString() {
        name
    }
}
