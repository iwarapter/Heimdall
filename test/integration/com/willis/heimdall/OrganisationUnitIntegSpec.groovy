package com.willis.heimdall

import spock.lang.*

/**
 * Integration tests for the organisation unit model
 * @author Sion Williams
 */
class OrganisationUnitIntegSpec extends Specification {
	def orgUnit

    def setup() {
		orgUnit = new OrganisationUnit(name : 'Web Team',
										members : 'Sion Williams')
    }

    def cleanup() {
    }

    void 'test saving a single record to the database'() {
		given: 'I save the organisation unit to the database'
			orgUnit.save(failOnError: true)
		
		expect: 'to find an entry in the database with the same organisation properties'
			orgUnit.id != null
			OrganisationUnit.get(orgUnit.id).name == 'Web Team'
			OrganisationUnit.get(orgUnit.id).members == 'Sion Williams'
    }
}
