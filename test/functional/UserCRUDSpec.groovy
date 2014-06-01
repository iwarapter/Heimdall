import geb.spock.GebReportingSpec

import spock.lang.*

import pages.*

@Stepwise
class UserCRUDSpec extends GebReportingSpec {

	def "there are no users"() {
		when:
		to ListPage
		then:
		userRows.size() == 0
	}

	def "add a user"() {
		when:
		newUserButton.click()
		then:
		at CreatePage
	}

	def "enter the details"() {
		when:
		$("#enabled").click()
		firstName = "Sion"
		lastName = "Williams"
		createButton.click()
		then:
		at ShowPage
	}

	def "check the entered details"() {
		expect:
		firstName == "Sion"
		lastName == "Williams"
		enabled == true
	}

	def "edit the details"() {
		when:
		editButton.click()
		then:
		at EditPage
		when:
		$("#enabled").click()
		updateButton.click()
		then:
		at ShowPage
	}

	def "check in listing"() {
		when:
		to ListPage
		then:
		userRows.size() == 1
		def row = userRow(0)
		row.firstName == "Sion"
		row.lastName == "Williams"
	}

	def "show user"() {
		when:
		userRow(0).showLink.click()
		then:
		at ShowPage
	}

	def "delete user"() {
		given:
		def deletedId = id
		when:
		withConfirm { deleteButton.click() }
		then:
		at ListPage
		message == "User $deletedId deleted"
		userRows.size() == 0
	}
}