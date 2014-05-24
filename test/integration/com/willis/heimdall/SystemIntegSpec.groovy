package com.willis.heimdall

import spock.lang.*

/**
 * Integration tests for the System model
 * @author Sion Williams
 */
class SystemIntegSpec extends Specification {
	def system

    def setup() {
		system = new System(name : 'My System',
							description : 'Describe my system',
							vendor : 'Oracle',
							status : 'Active',
							organisationUnit : 'My group')
    }

    def cleanup() {
    }

    void 'test saving a single record to the database'() {
		given: 'I save the system to the database'
			system.save(failOnError: true)
		 
		expect: 'to find an entry in the database with the same system properties'
			system.id != null
			System.get(system.id).name == 'My System'		
			System.get(system.id).description == 'Describe my system'
			System.get(system.id).vendor == 'Oracle'
			System.get(system.id).status == 'Active'
			System.get(system.id).organisationUnit == 'My group'
    }
	
	void 'test saving and updating a record'() {
		given: 'I save the system to the database'
			system.save(failOnError:true)
			system.id != null
			
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
			system.id != null
			
		when: 'I delete the system'
			def foundSys = System.get(system.id)
			foundSys.delete()
		
		then: 'expect an entry in the database with the same ID'
			!system.exists(foundSys.id)
	}
	
	void 'test having an invalid releaseId'(){
		when: 'A system is created with an invalid name'
			def badSys = new System(name: '',
							description: 'Describe my system',
							vendor: 'Oracle',
							status: 'Active',
							organisationUnit : 'My group')
			
		then: 'the test should fail validation and have errors because cannot be blank'
			badSys.validate() == false
			badSys.hasErrors() == true
			def errors = badSys.errors
			errors.getFieldError('name').code == 'nullable'
	}
	
	void 'test invalid save is corrected'(){
		given:
			def badSys = new System(name: '',
							description: 'Describe my system',
							vendor: 'Oracle',
							status: 'Active',
							organisationUnit : 'My group')
			
		when:
			badSys.validate() == false
			badSys.hasErrors() == true
			badSys.save() == null
			badSys.name = 'My System'
			
		then:
			badSys.validate() == true
			badSys.hasErrors() == false
			badSys.save()
	}
}
