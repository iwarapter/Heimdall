package com.willis.heimdall

import spock.lang.*

/**
 * Integration tests for the User model
 * @author Sion Williams
 */
class UserIntegSpec extends Specification {
	def user

    def setup() {
		user = new User( firstName: 'Sion',
						lastName: 'Williams',
						email: 'my@email.co.uk',
						role: 'Admin',
						status: 'Active' )
    }

    def cleanup() {
    }

    void 'test saving a single record to the database'() {
		given: 'I save the user to the database'
			user.save(failOnError:true)
	
		expect: 'to find an entry in the database with the same properties'
			user.id
			user.get(user.id).firstName == 'Sion'
			user.get(user.id).lastName == 'Williams'
			user.get(user.id).email == 'my@email.co.uk'
			user.get(user.id).role == 'Admin'
			user.get(user.id).status == 'Active'
    }
	
	void 'test saving and updating a record'() {
		given: 'I save the user to the database'
			user.save(failOnError:true)
			!user.id
			
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
			!user.id
			
		when: 'I delete the user'
			def foundUser = User.get(user.id)
			foundUser.delete()			
		
		then: 'expect an entry in the database with the same ID'
			!user.exists(foundUser.id)
	}
	
	void 'test having an invalid email'(){
		when: 'A user is created with an invalid email'
			def badEmail = new User( firstName: 'Sion', 
									lastName: 'Williams', 
									email: 'myemail.co.uk', 
									role: 'Admin',	
									status: 'Active')
			
		then: 'the test should fail validation and have errors because format not correct'
			!badEmail.validate()
			badEmail.hasErrors()
			def errors = badEmail.errors
			errors.getFieldError( 'email' ).code == 'email.invalid'
		
	}
	
	void 'test invalid save is corrected'(){
		given: 'A user is created with an invalid email'
			def badEmail = new User( firstName: 'Sion', 
									lastName: 'Williams', 
									email: 'myemail.co.uk', 
									role: 'Admin',	
									status: 'Active' )
			
		when: 'we validate its should fail so we change the email'
			!badEmail.validate()
			badEmail.hasErrors()
			badEmail.save() == null
			badEmail.email = 'sion@sion.co.uk'
			
		then: 'validation should be successful'
			badEmail.validate()
			!badEmail.hasErrors()
			badEmail.save()
	}
	
	void 'test system should have a stakeholder'(){
		given: ' a new system and user'
			def sys1 = new System( name : 'My System',
							description : 'Describe my system',
							vendor : 'Oracle',
							status : 'Active',
							organisationUnit : 'My group' ).save()
							
			def user1 = new User( firstName: 'Sion',
							lastName: 'Williams',
							email: 'my@email.co.uk',
							role: 'Admin',
							status: 'Active' )		
		
		and: 'shouldnt validate, shouldnt have an id'
			!user1.validate()
			!user1.id
			user1.system = sys1
			
		expect: 'its user: should validate and save'
			user1.validate()
			user1.save()
			user1.id
			
	}
	
	void 'test User BelongsTo System and should cascade deletes'(){
		given: 'Create and save a System and User'
			def originalUserCount = User.list().size()
			
			def sys1 = new System( name : 'My System1',
								description : 'Describe my system',
								vendor : 'IBM',
								status : 'Active',
								organisationUnit : 'My group' )
								
			def user1 = new User( firstName: 'Sion',
						lastName: 'Williams',
						email: 'my@email.co.uk',
						role: 'Admin',
						status: 'Active' )
			
			sys1.addToUsers( user1 )
			
		and: 'Saving a system should also save its users'
			!sys1.id
			!user1.id
			sys1.save()
			sys1.id
			user1.id
			
		expect: 'Deleting an album should delete its songs'
			sys1.delete()
			User.list().size() == originalUserCount
		
	}
}
