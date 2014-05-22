package com.willis.heimdall

import spock.lang.*

/**
 * Integration tests for the User model
 * @author Sion Williams
 */
class UserIntegSpec extends Specification {
	def user

    def setup() {
		user = new User(firstName: 'Sion',
						lastName: 'Williams',
						email: 'my@email.co.uk',
						role: 'Admin',
						status: 'Active')
    }

    def cleanup() {
    }

    void 'test saving a single record to the database'() {
		given: 'I save the user to the database'
			user.save(failOnError:true)
	
		expect: 'to find an entry in the database with the same first name'
			user.id != null
			user.get(user.id).firstName == 'Sion'
    }
	
	void 'test saving and updating a record'() {
		given: 'I save the user to the database'
			user.save(failOnError:true)
			user.id != null
			
		when: 'I edit the user name'
			def foundUser = User.get(user.id)
			user.firstName = 'Notsion'
			foundUser.save(failOnError:true)			
		
		then: 'expect an entry in the database with the same ID'
			User.get(user.id).firstName == 'Notsion'
	}
	
	void 'test saving and deleting a record'() {
		given: 'I save the user to the database'
			user.save(failOnError:true)
			user.id != null
			
		when: 'I delete the user'
			def foundUser = User.get(user.id)
			foundUser.delete()			
		
		then: 'expect an entry in the database with the same ID'
			!user.exists(foundUser.id)
	}
	
	void 'test having an invalid email'(){
		when: 'A user is created with an invalid email'
			def badEmail = new User(firstName: 'Sion', 
									lastName: 'Williams', 
									email: 'myemail.co.uk', 
									role: 'Admin',	
									status: 'Active')
			
		then: 'the test should fail validation and have errors because format not correct'
			badEmail.validate() == false
			badEmail.hasErrors() == true
			def errors = badEmail.errors
			errors.getFieldError('email').code == 'email.invalid'
		
	}
	
	void 'test invalid save is corrected'(){
		given: 'A user is created with an invalid email'
			def badEmail = new User(firstName: 'Sion', 
									lastName: 'Williams', 
									email: 'myemail.co.uk', 
									role: 'Admin',	
									status: 'Active')
			
		when: 'we validate its should fail so we change the email'
			badEmail.validate() == false
			badEmail.hasErrors() == true
			badEmail.save() == null
			badEmail.email = 'sion@sion.co.uk'
			
		then: 'validation should be successful'
			badEmail.validate() == true
			badEmail.hasErrors() == false
			badEmail.save()
	}
}
