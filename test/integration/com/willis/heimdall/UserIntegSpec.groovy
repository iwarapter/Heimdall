package com.willis.heimdall

import spock.lang.*

/**
 * Integration tests for the User model
 * @author Sion Williams
 */
class UserIntegSpec extends Specification {
	def user1

    def setup() {
		user1 = new User( firstName: 'Sion',
						lastName: 'Williams',
						email: 'my@email.co.uk',
						role: 'Admin',
						status: 'Active' )
    }

    def cleanup() {

    }

    void 'test saving a single record to the database'() {
		given: 'I save the user to the database'
			user1.save(failOnError:true)
	
		expect: 'to find an entry in the database with the same properties'
			user1.id
			User.get(user1.id).firstName == 'Sion'
			User.get(user1.id).lastName == 'Williams'
			User.get(user1.id).email == 'my@email.co.uk'
			User.get(user1.id).role == 'Admin'
			User.get(user1.id).status == 'Active'
    }
	
	void 'test saving and updating a record'() {
		given: 'I save the user to the database'
			user1.save(failOnError:true)
			!user1.id
			
		when: 'I edit the user name'
			def foundUser = User.get(user1.id)
			user1.firstName = 'Notsion'
			foundUser.save(failOnError:true)			
		
		then: 'expect an entry in the database with the same ID'
			User.get(user1.id).firstName == 'Notsion'
	}
	
	void 'test saving and deleting a record'() {
		given: 'I save the user to the database'
			user1.save(failOnError:true)
			!user1.id
			
		when: 'I delete the user'
			def foundUser = User.get(user1.id)
			foundUser.delete()			
		
		then: 'expect an entry in the database with the same ID'
			!user1.exists(foundUser.id)
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
							
			def user2 = new User( firstName: 'Sion',
							lastName: 'Williams',
							email: 'my@email.co.uk',
							role: 'Admin',
							status: 'Active' )		
		
		and: 'shouldnt validate, shouldnt have an id'
			!user2.validate()
			!user2.id
			user2.system = sys1
			
		expect: 'its user: should validate and save'
			user2.validate()
			user2.save()
			user2.id
			
	}
	
	void 'test User BelongsTo System and should cascade deletes'(){
		given: 'Create and save a System and User'
			def originalUserCount = User.list().size()
			
			def sys1 = new System( name : 'My System1',
								description : 'Describe my system',
								vendor : 'IBM',
								status : 'Active',
								organisationUnit : 'My group' )
								
			def user2 = new User( firstName: 'Sion',
						lastName: 'Williams',
						email: 'my@email.co.uk',
						role: 'Admin',
						status: 'Active' )
			
			sys1.addToUsers( user2 )
			
		and: 'Saving a system should also save its users'
			!sys1.id
			!user2.id
			sys1.save()
			sys1.id
			user2.id
			
		expect: 'Deleting an album should delete its songs'
			sys1.delete()
			User.list().size() == originalUserCount	
	}
	
	void 'test organisation group should have a member'(){
		given: ' a new group and user'
			def org1 = new OrganisationGroup( name : 'My Group' ).save()
							
			def user2 = new User( firstName: 'Sion',
							lastName: 'Williams',
							email: 'my@email.co.uk',
							role: 'Admin',
							status: 'Active' )
		
		and: 'shouldnt validate, shouldnt have an id'
			!user2.validate()
			!user2.id
			user2.orgUnit = org1
			
		expect: 'its user: should validate and save'
			user2.validate()
			user2.save()
			user2.id
			
	}
	
	void 'test User BelongsTo group and should cascade deletes'(){
		given: 'Create and save a group and User'
			def originalUserCount = User.list().size()
			
			def org1 = new OrganisationGroup( name : 'My Group' )
								
			def user2 = new User( firstName: 'Sion',
						lastName: 'Williams',
						email: 'my@email.co.uk',
						role: 'Admin',
						status: 'Active' )
			
			org1.addToMembers( user2 )
			
		and: 'Saving a system should also save its users'
			!org1.id
			!user2.id
			org1.save()
			org1.id
			user2.id
			
		expect: 'Deleting an album should delete its songs'
			org1.delete()
			User.list().size() == originalUserCount
	}
	
	void 'test Users creating bookings'(){
		given:
			user1.save()
			def today = new Date()
			def todayPlusWeek = today + 30
			def booking1 = new Booking(name : 'Booking1',
										startDate : today,
										endDate : todayPlusWeek)
			def booking2 = new Booking(name : 'Booking2',
										startDate : today + 1,
										endDate : todayPlusWeek)
		
		when:
			user1.addToBookings( booking1 )
			user1.addToBookings( booking2 )
			
		then:
			User.get(user1.id).bookings.size() == 2
	}
}
