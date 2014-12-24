package com.willis.heimdall

class User {
    String loginId
    String password
    Date dateCreated

    static hasMany = [bookings: Booking]

    static constraints = {
        loginId size: 3..20, unique: true, nullable: false
        password size: 6..8, blank: false, validator: { passwd, user -> passwd != user.loginId }
    }
}
