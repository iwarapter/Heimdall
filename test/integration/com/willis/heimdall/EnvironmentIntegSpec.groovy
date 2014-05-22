package com.willis.heimdall

import spock.lang.*

/**
 * Integration tests for the Environment model
 * @author Sion Williams
 */
class EnvironmentIntegSpec extends Specification {
	def env 

    def setup() {
		env = new Environment(name: "webapp-sit",
								description: "This is an environment",
								system: "Some System",
								integratedWith: "a different environment",
								url: "http://localhost:7001",
								phaseUsage: "SIT",
								vendor: "myCompany",
								status: "Active")
    }

    def cleanup() {
    }

    void "test saving a single record to the database"() {
		given: "I save the environment to the database"
			env.save(failOnError:true)
	
		expect: "to find an entry in the database with the same releaseId"
			env.id != null
			Environment.get(env.id).name == 'webapp-sit'
			Environment.get(env.id).description == "This is an environment"
			Environment.get(env.id).system == "Some System"
			Environment.get(env.id).integratedWith == "a different environment"
			Environment.get(env.id).url == "http://localhost:7001"
			Environment.get(env.id).phaseUsage == "SIT"
			Environment.get(env.id).vendor == "myCompany"
			Environment.get(env.id).status == "Active"
    }
	
	void "test saving and updating a record"() {
		given: "I save the environment to the database"
			env.save(failOnError:true)
			env.id != null
			
		when: "I edit the environment name"
			def foundEnv = Environment.get(env.id)
			foundEnv.name = 'Changed name'
			foundEnv.save(failOnError:true)
		
		then: "expect an entry in the database with the same name"
			Environment.get(env.id).name == 'Changed name'
	}
	
	void "test saving and deleting a record"() {
		given: "I save the environment to the database"
			env.save(failOnError:true)
			env.id != null
			
		when: "I delete the environment"
			def foundEnv = Environment.get(env.id)
			foundEnv.delete()
		
		then: "expect an entry in the database with the same ID"
			!env.exists(foundEnv.id)
	}
	
	void "test having an invalid releaseId"(){
		when: "A environment is created with an invalid name"
			def badEnv = new Environment(name: "",
								description: "This is an environment",
								system: "Some System",
								integratedWith: "a different environment",
								url: "http://localhost:7001",
								phaseUsage: "SIT",
								vendor: "myCompany",
								status: "Active")
			
		then: "the test should fail validation and have errors because cannot be blank"
			badEnv.validate() == false
			badEnv.hasErrors() == true
			def errors = badEnv.errors
			errors.getFieldError("name").code == "nullable"		
	}
	
	void "test invalid save is corrected"(){
		given:
			def badEnv = new Environment(name: "",
								description: "This is an environment",
								system: "Some System",
								integratedWith: "a different environment",
								url: "http://localhost:7001",
								phaseUsage: "SIT",
								vendor: "myCompany",
								status: "Active")
			
		when:
			badEnv.validate() == false
			badEnv.hasErrors() == true
			badEnv.save() == null
			badEnv.name = "MyEnv"
			
		then:
			badEnv.validate() == true
			badEnv.hasErrors() == false
			badEnv.save()
	}
}
