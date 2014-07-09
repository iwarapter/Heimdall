package com.willis.heimdall

import grails.converters.JSON
import org.joda.time.DateTime

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

import java.text.SimpleDateFormat

/**
 * Environment Controller
 * @author Sion Williams
 */
@Transactional(readOnly = true)
class EnvironmentController {
    def bookingService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Environment.list(params), model: [environmentInstanceCount: Environment.count()]
    }

    def show(Environment environmentInstance) {
        respond environmentInstance
    }

    def create() {
        respond new Environment(params)
    }

    @Transactional
    def save(Environment environmentInstance) {
        if (environmentInstance == null) {
            notFound()
            return
        }

        if (environmentInstance.hasErrors()) {
            respond environmentInstance.errors, view: 'create'
            return
        }

        environmentInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'environment.label', default: 'Environment'), environmentInstance.id])
                redirect environmentInstance
            }
            '*' { respond environmentInstance, [status: CREATED] }
        }
    }

    def edit(Environment environmentInstance) {
        respond environmentInstance
    }

    @Transactional
    def update(Environment environmentInstance) {
        if (environmentInstance == null) {
            notFound()
            return
        }

        if (environmentInstance.hasErrors()) {
            respond environmentInstance.errors, view: 'edit'
            return
        }

        environmentInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Environment.label', default: 'Environment'), environmentInstance.id])
                redirect environmentInstance
            }
            '*' { respond environmentInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Environment environmentInstance) {

        if (environmentInstance == null) {
            notFound()
            return
        }

        environmentInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Environment.label', default: 'Environment'), environmentInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    def calendar = {
        def environment = Environment.findById( params.id )
        [ environment : environment ]
    }

    def bookingList = {
        def environment = Environment.findById( params.id )
        def bookingList = bookingService.listBookingsJSON( environment )
        bookingList
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'environment.label', default: 'Environment'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
