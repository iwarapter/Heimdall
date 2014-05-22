package com.willis.heimdall

import spock.lang.*

/**
 * Integration tests for the organisation model
 * @author Sion Williams
 */
class OrganisationIntegSpec extends Specification {
	def org

    def setup() {
		org = new Organisation(name: 'My Organisation',
								group: 'Web Team')
    }

    def cleanup() {
    }

    void 'test saving a single record to the database'() {
		given: 'I save the organisation to the database'
			org.save(failOnError: true)
		
		expect: 'to find an entry in the database with the same organisation properties'
			org.id != null
			Organisation.get(org.id).name == 'My Organisation'
			Organisation.get(org.id).group == 'Web Team'
    }
}
