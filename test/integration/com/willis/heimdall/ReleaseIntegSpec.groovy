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
			release.save()
		
		expect: "to find an entry in the database with the same ID"
			release.id != NULL
			Release.get(release.id) == 'rel1'
    }
	
	void "test saving and editting a record"() {
		given: "I save the release to the database"
			release.save()
			release.id != NULL
			
		when: "I edit the release name"
			def foundRelease = Release.get(release.id)
			foundRelease.releaseName = 'Changed name'
			foundRelease.save()
			
		
		then: "expect an entry in the database with the same ID"				
			Release.get(release.id).releaseName == 'Changed name'
	}
	
	void "test saving and deleting a record"() {
		given: "I save the release to the database"
			release.save()
			release.id != NULL
			
		when: "I delete the release"
			def foundRelease = Release.get(release.id)
			foundRelease.delete()
			
		
		then: "expect an entry in the database with the same ID"
			release.exists(foundRelease.id) == NULL
	}
}
