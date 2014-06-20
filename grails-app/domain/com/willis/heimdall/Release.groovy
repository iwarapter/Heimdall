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
		releaseId( size : 3..20, unique : true )
		releaseName()
		summary( maxSize : 250 )
		location( nullable : true )
		type( inList : ['Major', 'Minor', 'Patch'] )
		riskLevel( inList : ['Low', 'Medium', 'High'] )
		releaseDate( min : new Date() )
		implementationDate( min : new Date() )
    }

    String toString(){
        name
    }
}
