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

    static constraints = {
		releaseId(size: 3..20, unique: true)
		
    }
}
