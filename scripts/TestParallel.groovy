import org.codehaus.groovy.grails.test.*
import org.codehaus.groovy.grails.test.support.*
import org.codehaus.groovy.grails.test.event.*

includeTargets << grailsScript("_GrailsClean")
includeTargets << grailsScript("_GrailsTest")

target(main: "Runs functional tests in parallel in sets of bucketSize") {

	depends(checkVersion, configureProxy, parseArguments, cleanTestReports)

	def tests = new SpecFinder(binding).getTestClassNames()
	def id = (argsMap.set ?: 0) as int
	def sets = (argsMap.total ?: 4) as int

	List<String> shard = []

	tests.eachWithIndex { test, index ->
		if (index % sets == id) {
			 shard << "${ tests.get(index) }"
		}
	}

	testNames = shard

	phasesToRun = ['functional']

	allTests()

}

setDefaultTarget(main)

class SpecFinder extends GrailsTestTypeSupport {

	SpecFinder(binding) {
		super('name', 'functional')
		buildBinding = binding
	}

	int doPrepare() {
		0
	}

	GrailsTestTypeResult doRun(GrailsTestEventPublisher eventPublisher) {
		null
	}

	def getTestClassNames() {
		findSourceFiles(new GrailsTestTargetPattern('**.*Spec')).sort { -it.length() }.collect { sourceFileToClassName(it) }
	}
}