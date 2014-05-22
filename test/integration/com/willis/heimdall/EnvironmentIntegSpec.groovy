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
		given: "I save the release to the database"
			env.save(failOnError:true)
	
		expect: "to find an entry in the database with the same releaseId"
			env.id != null
			env.get(env.id).name == 'webapp-sit'
    }
}
