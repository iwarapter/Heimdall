package com.willis.heimdall

import spock.lang.*


/**
 * Integration tests for the Environment model
 * @author Sion Williams
 */
class ReleaseIntegSpec extends Specification {
	def release
	def dateForRelease 
	def dateForImplementation

    def setup() {		
		dateForRelease = new Date() + 30
		dateForImplementation = new Date() + 15
		
		release = new Release(releaseId : 'rel1', 
								releaseName : 'Release one', 
								summary : 'Description here', 
								type : 'Major', 
								riskLevel : 'Low', 
								location : 'Exeter',
								releaseDate : dateForRelease,
								implementationDate : dateForImplementation)
    }

    def cleanup() {

    }

    void 'test saving a single record to the database'() {
		given: 'I save the release to the database'
			release.save(failOnError:true)
		
		expect: 'to find an entry in the database with the same properties'
			release.id != null
			Release.get(release.id).releaseId == 'rel1'
			Release.get(release.id).releaseName == 'Release one'
			Release.get(release.id).summary == 'Description here'
			Release.get(release.id).type == 'Major'
			Release.get(release.id).riskLevel == 'Low'
			Release.get(release.id).location == 'Exeter'
			Release.get(release.id).releaseDate == dateForRelease
			Release.get(release.id).implementationDate == dateForImplementation
    }
	
	void 'test saving and updating a record'() {
		given: 'I save the release to the database'
			release.save(failOnError:true)
			release.id != null
			
		when: 'I edit the release name'
			def foundRelease = Release.get(release.id)
			foundRelease.releaseName = 'Changed name'
			foundRelease.save(failOnError:true)			
		
		then: 'expect an entry in the database with the same ID'				
			Release.get(release.id).releaseName == 'Changed name'
	}
	
	void 'test saving and deleting a record'() {
		given: 'I save the release to the database'
			release.save(failOnError:true)
			release.id != null
			
		when: 'I delete the release'
			def foundRelease = Release.get(release.id)
			foundRelease.delete()			
		
		then: 'expect an entry in the database with the same ID'
			!release.exists(foundRelease.id)
	}
	
	void 'test having an invalid releaseId'(){		
		when: 'A release is created with an invalid release id'
			def badId = new Release(releaseId: 'r1', 
									releaseName: 'Release one', 
									summary: 'Description here', 
									type: 'Major', 
									riskLevel: 'Low', 
									location: 'Exeter', 
									releaseDate : dateForRelease, 
									implementationDate : dateForImplementation)
			
		then: 'the test should fail validation and have errors because size is too small'
			badId.validate() == false
			badId.hasErrors() == true
			def errors = badId.errors
			errors.getFieldError('releaseId').code == 'size.toosmall'			
		
	}
	
	void 'test invalid save is corrected'(){
		given:
			def badId = new Release(releaseId: 'r1', 
									releaseName: 'Release one', 
									summary: 'Description here', 
									type: 'Major', 
									riskLevel: 'Low', 
									location: 'Exeter', 
									releaseDate : dateForRelease, 
									implementationDate : dateForImplementation)
			
		when:
			badId.validate() == false
			badId.hasErrors() == true
			badId.save() == null
			badId.releaseId = 'rel1'
			
		then:
			badId.validate() == true
			badId.hasErrors() == false
			badId.save()			
	}
}
