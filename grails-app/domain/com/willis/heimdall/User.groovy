package com.willis.heimdall

/**
 * User model - Spring Security Core
 * @author Sion Williams
 */
class User {

	transient springSecurityService

	String firstName
	String lastName
	String email
	String username
	String password
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static hasMany = [ bookings : Booking ]

	static transients = ['springSecurityService']

	static constraints = {
		firstName( blank : false, nullable : false )
		lastName( blank : false, nullable : false )
		email( email : true, nullable : false )
		username blank: false, unique: true
		password blank: false
		bookings( nullable : true )
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role }
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}

	String toString(){
		"${lastName}, ${firstName}"
	}
}
