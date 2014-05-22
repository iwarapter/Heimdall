package com.willis.heimdall

/**
 * Release model
 * @author Sion Williams
 */
class Release {
	String releaseId
	String releaseName
	String summary
	String type
	String riskLevel
	String location
	Date releaseDate
	Date implementationDate

    static constraints = {
		releaseId(size : 3..20, unique : true)
		summary(maxSize : 250)
		type(inList : ['Major', 'Minor', 'Patch'])
		riskLevel(inList : ['Low', 'Medium', 'High'])
		releaseDate(min : new Date())
		implementationDate(min : new Date())
    }
}
