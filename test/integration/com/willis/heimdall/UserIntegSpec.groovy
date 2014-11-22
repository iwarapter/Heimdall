package com.willis.heimdall

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.*

/**
 * Integration tests for the User model
 * @author Sion Williams
 */
@TestFor(User)
@Mock(Booking)
class UserIntegSpec extends Specification {

    def setup() {
    }

    def cleanup() {

    }

    void 'test saving a single record to the database'() {
        given: 'I save the user to the database'
        def user1 = new User(username: 'sion1',
                password: 'secret',
                firstName: 'Sion',
                lastName: 'Williams',
                email: 'my@email.co.uk')
        user1.save(failOnError: true)

        expect: 'to find an entry in the database with the same properties'
        user1.id
        User.get(user1.id).username == 'sion1'
        User.get(user1.id).firstName == 'Sion'
        User.get(user1.id).lastName == 'Williams'
        User.get(user1.id).email == 'my@email.co.uk'
    }

    void 'test saving and updating a record'() {
        given: 'I save the user to the database'
        def user2 = new User(username: 'sion2',
                password: 'secret',
                firstName: 'Sion',
                lastName: 'Williams',
                email: 'my@email.co.uk')
        user2.save(failOnError: true)
        !user2.id

        when: 'I edit the user name'
        def foundUser = User.get(user2.id)
        user2.firstName = 'Notsion'
        foundUser.save(failOnError: true)

        then: 'expect an entry in the database with the same ID'
        User.get(user2.id).firstName == 'Notsion'
    }

    void 'test saving and deleting a record'() {
        given: 'I save the user to the database'
        def user3 = new User(username: 'sion3',
                password: 'secret',
                firstName: 'Sion',
                lastName: 'Williams',
                email: 'my@email.co.uk')
        user3.save(failOnError: true)
        !user3.id

        when: 'I delete the user'
        def foundUser = User.get(user3.id)
        foundUser.delete(flush: true)

        then: 'expect an entry in the database with the same ID'
        !user3.exists(foundUser.id)
    }

    void 'test having an invalid email'() {
        when: 'A user is created with an invalid email'
        def badEmail = new User(username: 'sion4',
                password: 'secret',
                firstName: 'Sion',
                lastName: 'Williams',
                email: 'myemail.co.uk')

        then: 'the test should fail validation and have errors because format not correct'
        !badEmail.validate()
        badEmail.hasErrors()
        def errors = badEmail.errors
        errors.getFieldError('email').code == 'email.invalid'

    }

    void 'test invalid save is corrected'() {
        given: 'A user is created with an invalid email'
        def badEmail = new User(username: 'sion5',
                password: 'secret',
                firstName: 'Sion',
                lastName: 'Williams',
                email: 'myemail.co.uk')

        when: 'we validate its should fail so we change the email'
        !badEmail.validate()
        badEmail.hasErrors()
        badEmail.save() == null
        badEmail.email = 'sion@sion.co.uk'

        then: 'validation should be successful'
        badEmail.validate()
        !badEmail.hasErrors()
        badEmail.save()
    }

    @Ignore
    void 'test system should have a stakeholder'() {
        given: ' a new system and user'
        def sys1 = new System(name: 'My System',
                description: 'Describe my system',
                vendor: 'Oracle',
                status: 'Active',
                organisationUnit: 'My group').save()

        def user2 = new User(firstName: 'Sion',
                lastName: 'Williams',
                email: 'my@email.co.uk',
                role: 'Admin',
                status: 'Active')

        and: 'shouldnt validate, shouldnt have an id'
        !user2.validate()
        !user2.id
        user2.system = sys1

        expect: 'its user: should validate and save'
        user2.validate()
        user2.save()
        user2.id

    }

    @Ignore
    void 'test User BelongsTo System and should cascade deletes'() {
        given: 'Create and save a System and User'
        def originalUserCount = User.list().size()

        def sys1 = new System(name: 'My System1',
                description: 'Describe my system',
                vendor: 'IBM',
                status: 'Active',
                organisationUnit: 'My group')

        def user2 = new User(username: 'sion',
                password: 'secret',
                firstName: 'Sion',
                lastName: 'Williams',
                email: 'my@email.co.uk')

        sys1.addToUsers(user2)

        and: 'Saving a system should also save its users'
        !sys1.id
        !user2.id
        sys1.save()
        sys1.id
        user2.id

        expect: 'Deleting an album should delete its songs'
        sys1.delete()
        User.list().size() == originalUserCount
    }

    @Ignore
    void 'test organisation group should have a member'() {
        given: ' a new group and user'
        def org1 = new OrganisationGroup(name: 'My Group').save()

        def user2 = new User(username: 'sion',
                password: 'secret',
                firstName: 'Sion',
                lastName: 'Williams',
                email: 'my@email.co.uk')

        and: 'shouldnt validate, shouldnt have an id'
        !user2.validate()
        !user2.id
        user2.orgUnit = org1

        expect: 'its user: should validate and save'
        user2.validate()
        user2.save()
        user2.id

    }

    @Ignore
    void 'test User BelongsTo group and should cascade deletes'() {
        given: 'Create and save a group and User'
        def originalUserCount = User.list().size()

        def org1 = new OrganisationGroup(name: 'My Group')

        def user2 = new User(username: 'sion',
                password: 'secret',
                firstName: 'Sion',
                lastName: 'Williams',
                email: 'my@email.co.uk')

        org1.addToMembers(user2)

        and: 'Saving a system should also save its users'
        !org1.id
        !user2.id
        org1.save()
        org1.id
        user2.id

        expect: 'Deleting an album should delete its songs'
        org1.delete()
        User.list().size() == originalUserCount
    }

    void 'test Users creating bookings'() {
        given:
        def user6 = new User(username: 'sion6',
                password: 'secret',
                firstName: 'Sion',
                lastName: 'Williams',
                email: 'my@email.co.uk')
        user6.save(failOnError: true)
        def today = new Date()
        def todayPlusWeek = today.plus(30)
        def booking1 = new Booking(name: 'Booking1',
                startTime: today,
                endTime: todayPlusWeek)
        def booking2 = new Booking(name: 'Booking2',
                startTime: today.plus(1),
                endTime: todayPlusWeek)
        user6.addToBookings(booking1)
        user6.addToBookings(booking2)

        expect:
        User.get(user6.id).bookings.size() == 2
    }
}
