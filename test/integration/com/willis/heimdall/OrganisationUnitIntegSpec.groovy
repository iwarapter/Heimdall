package com.willis.heimdall

import spock.lang.*

/**
 * Integration tests for the organisation unit model
 * @author Sion Williams
 */
class OrganisationUnitIntegSpec extends Specification {
	def orgUnit

    def setup() {
		orgUnit = new OrganisationGroup(name : 'Web Team')
    }

    def cleanup() {
    }

    void 'test saving a single record to the database'() {
		given: 'I save the organisation unit to the database'
			orgUnit.save(failOnError: true)
		
		expect: 'to find an entry in the database with the same organisation properties'
			orgUnit.id != null
			OrganisationGroup.get(orgUnit.id).name == 'Web Team'
    }
	
	void 'test group has many members and cascade saves'(){
		when: 'Create and save a group and create a user without an group: shoudnt be valid'
			def originalUserCount = User.list().size()
			
			orgUnit.save()
								
			def user1 = new User( firstName: 'Sion',
						lastName: 'Williams',
						email: 'my@email.co.uk',
						role: 'Admin',
						status: 'Active' )
			
			orgUnit.addToMembers( user1 )
			
		then: 'References should be set up, and saving system should save user'
			user1.orgUnit == orgUnit
			!user1.id
			orgUnit.save()
			User.list().size() == originalUserCount + 1
	}
}
