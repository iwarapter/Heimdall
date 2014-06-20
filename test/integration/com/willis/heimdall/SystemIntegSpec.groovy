package com.willis.heimdall

import spock.lang.*

/**
 * Integration tests for the System model
 * @author Sion Williams
 */
class SystemIntegSpec extends Specification {
	def system

    def setup() {
		system = new System( name : 'My System',
							description : 'Describe my system',
							vendor : 'Oracle',
							status : 'Active',
							organisationUnit : 'My group' )
    }

    def cleanup() {

    }

    void 'test saving a single record to the database'() {
		given: 'I save the system to the database'
			system.save(failOnError: true)
		 
		expect: 'to find an entry in the database with the same system properties'
			system.id
			System.get(system.id).name == 'My System'		
			System.get(system.id).description == 'Describe my system'
			System.get(system.id).vendor == 'Oracle'
			System.get(system.id).status == 'Active'
			System.get(system.id).organisationUnit == 'My group'
    }
	
	void 'test saving and updating a record'() {
		given: 'I save the system to the database'
			system.save(failOnError:true)
			system.id
			
		when: 'I edit the system name'
			def foundSys = System.get(system.id)
			foundSys.name = 'Changed name'
			foundSys.save(failOnError:true)
		
		then: 'expect an entry in the database with the same name'
			System.get(system.id).name == 'Changed name'
	}
	
	void 'test saving and deleting a record'() {
		given: 'I save the system to the database'
			system.save(failOnError:true)
			system.id
			
		when: 'I delete the system'
			def foundSys = System.get(system.id)
			foundSys.delete()
		
		then: 'expect an entry in the database with the same ID'
			!system.exists(foundSys.id)
	}
	
	void 'test having an invalid releaseId'(){
		when: 'A system is created with an invalid name'
			def badSys = new System( name: '',
							description: 'Describe my system',
							vendor: 'Oracle',
							status: 'Active',
							organisationUnit : 'My group' )
			
		then: 'the test should fail validation and have errors because cannot be blank'
			!badSys.validate()
			badSys.hasErrors()
			def errors = badSys.errors
			errors.getFieldError('name').code == 'nullable'
	}
	
	void 'test invalid save is corrected'(){
		given:
			def badSys = new System( name: '',
							description: 'Describe my system',
							vendor: 'Oracle',
							status: 'Active',
							organisationUnit : 'My group' )
			
		when:
			!badSys.validate()
			badSys.hasErrors()
			!badSys.save()
			badSys.name = 'My System'
			
		then:
			badSys.validate()
			!badSys.hasErrors()
			badSys.save()
	}
	
	void 'test system has many stakeholders and cascade saves'(){
		when: 'Create and save a system and create a user without an system: shoudnt be valid'
			def originalUserCount = User.list().size()
			
			def sys1 = new System( name : 'My System1',
								description : 'Describe my system',
								vendor : 'IBM',
								status : 'Active',
								organisationUnit : 'My group' ).save()
								
			def user1 = new User( firstName: 'Sion',
						lastName: 'Williams',
						email: 'my@email.co.uk',
						role: 'Admin',
						status: 'Active' )
			
			sys1.addToUsers( user1 )
			
		then: 'References should be set up, and saving system should save user'
			user1.system == sys1
			!user1.id
			sys1.save()
			User.list().size() == originalUserCount + 1		
	}
	
	void 'test we can query for Users via finders or HQL'(){
		when: 'Create and save a System'			
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
			def user2 = new User( firstName: 'John',
									lastName: 'Smith',
									email: 'john@smith.co.uk',
									role: 'Requestor',
									status: 'Active' )
			
			sys1.addToUsers( user1 )
			sys1.addToUsers( user2 )
			sys1.save()
			
		then: 'Dynamic finder'
			User.findAllBySystem( sys1 ).size() == 2
			
		and: 'HQL'
			User.executeQuery( "from User s where s.system = ?", [ sys1 ] ).size() == 2
			
	}
}
