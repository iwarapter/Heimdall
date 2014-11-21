package com.willis.heimdall

/**
 * Booking model
 * @author Sion Williams
 */
class Booking {
    String name
    String description
    Date startTime
    Date endTime

    static belongsTo = [user: User]

    static constraints = {
        name(nullable: false, blank: false)
        description(nullable: true, blank: true)
        startTime(required: true, nullable: false)
        endTime(required: true, validator: { val, obj -> val > obj.startTime })
        user(nullable: true)
    }

    String toString() {
        name
    }
}
