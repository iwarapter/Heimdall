package com.willis.heimdall



import spock.lang.*

/**
 *
 */
class ReleaseIntegSpec extends Specification {
	def release

    def setup() {
		
		release = new Release(releaseId: 'rel1', releaseName: 'Release one', summary: 'Description here', type: 'Business Service', riskLevel: 'Low', location: 'Exeter')
    }

    def cleanup() {
    }

    void "test saving a single record to the database"() {
		given: "I save the release to the database"
			release.save(failOnError:true)
		
		expect: "to find an entry in the database with the same releaseId"
			release.id != null
			Release.get(release.id).releaseId == 'rel1'
    }
	
	void "test saving and editting a record"() {
		given: "I save the release to the database"
			release.save(failOnError:true)
			release.id != null
			
		when: "I edit the release name"
			def foundRelease = Release.get(release.id)
			foundRelease.releaseName = 'Changed name'
			foundRelease.save(failOnError:true)
			
		
		then: "expect an entry in the database with the same ID"				
			Release.get(release.id).releaseName == 'Changed name'
	}
	
	void "test saving and deleting a record"() {
		given: "I save the release to the database"
			release.save(failOnError:true)
			release.id != null
			
		when: "I delete the release"
			def foundRelease = Release.get(release.id)
			foundRelease.delete()
			
		
		then: "expect an entry in the database with the same ID"
			!release.exists(foundRelease.id)
	}
	
	void "test having an invalid releaseId"(){		
		when: "A release is instanciated with an invalid release id"
			def badId = new Release(releaseId: 'r1', releaseName: 'Release one', summary: 'Description here', type: 'Business Service', riskLevel: 'Low', location: 'Exeter')
			
		then: "the test should fail validation and have errors because size is too small"
			badId.validate() == false
			badId.hasErrors() == true
			def errors = badId.errors
			errors.getFieldError("releaseId").code == "size.toosmall"			
		
	}
}
