package com.willis.heimdall

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.*

/**
 * Integration tests for the Environment model
 * @author Sion Williams
 */
@TestFor(Environment)
@Mock(Booking)
class EnvironmentIntegSpec extends Specification {
    @Shared
    def env1, env2, env3

    def setup() {
        env1 = new Environment(name: 'webapp-sit',
                description: 'This is an environment',
                system: 'Some System',
                url: 'http://localhost:7001',
                phaseUsage: 'SIT',
                vendor: 'myCompany',
                status: 'Active')
    }

    def cleanup() {

    }

    def 'test saving a single record to the database'() {
        given: 'I save the environment to the database'
        env1.save(failOnError: true)

        expect: 'to find an entry in the database with the same properties'
        env1.id
        Environment.get(env1.id).name == 'webapp-sit'
        Environment.get(env1.id).description == 'This is an environment'
        Environment.get(env1.id).system == 'Some System'
        Environment.get(env1.id).url == 'http://localhost:7001'
        Environment.get(env1.id).phaseUsage == 'SIT'
        Environment.get(env1.id).vendor == 'myCompany'
        Environment.get(env1.id).status == 'Active'
    }

    def 'test saving and updating a record'() {
        given: 'I save the environment to the database'
        env1.save(failOnError: true)
        env1.id

        when: 'I edit the environment name'
        def foundEnv = Environment.get(env1.id)
        foundEnv.name = 'Changed name'
        foundEnv.save(failOnError: true)

        then: 'expect an entry in the database with the same name'
        Environment.get(env1.id).name == 'Changed name'
    }

    def 'test saving and deleting a record'() {
        given: 'I save the environment to the database'
        env1.save(failOnError: true)
        env1.id

        when: 'I delete the environment'
        def foundEnv = Environment.get(env1.id)
        foundEnv.delete(flush: true)

        then: 'expect an entry in the database with the same ID'
        !env1.exists(foundEnv.id)
    }

    def 'test having an invalid releaseId'() {
        when: 'A environment is created with an invalid name'
        def badEnv = new Environment(name: '',
                description: 'This is an environment',
                phaseUsage: 'SIT',
                status: 'Active')

        then: 'the test should fail validation and have errors because cannot be blank'
        badEnv.validate() == false
        badEnv.hasErrors() == true
        def errors = badEnv.errors
        errors.getFieldError('name').code == 'nullable'
    }

    def 'invalid name is corrected'() {
        given: 'an environment with invalid name'
        def badEnv = new Environment(name: '',
                description: 'This is an environment',
                system: 'Some System',
                url: 'http://localhost:7001',
                phaseUsage: 'SIT',
                vendor: 'myCompany',
                status: 'Active')

        when:
        badEnv.validate() == false
        badEnv.hasErrors() == true
        badEnv.save() == null
        badEnv.name = 'MyEnv'

        then:
        badEnv.validate() == true
        badEnv.hasErrors() == false
        badEnv.save()
    }

    def 'integrate environments together'() {
        given: 'I have 3 environments'
        env2 = new Environment(name: 'env2',
                description: 'This is environment 2',
                phaseUsage: 'DEV',
                status: 'Active')
        env3 = new Environment(name: 'env3',
                description: 'This is environment 3',
                phaseUsage: 'UAT',
                status: 'Active')

        when: 'I integrate them'
        env1.save(failOnError: true)
        env2.save(failOnError: true)
        env3.save(failOnError: true)
        env1.addToIntegrations(env2)
        env1.addToIntegrations(env3)
        env2.addToIntegrations(env3)

        then: 'all environments should have the correct number of integrations'
        env1.integrations.size() == 2
        env2.integrations.size() == 1
    }

    def 'add a booking to an environment'() {
        given:
        env1.save()
        def today = new Date()
        def todayPlusWeek = today + 30
        def booking1 = new Booking(name: 'Booking1',
                startTime: today,
                endTime: todayPlusWeek)
        def booking2 = new Booking(name: 'Booking2',
                startTime: today + 1,
                endTime: todayPlusWeek)

        when:
        env1.addToBookings(booking1)
        env1.addToBookings(booking2)

        then:
        Environment.get(env1.id).bookings.size() == 2
    }
}
