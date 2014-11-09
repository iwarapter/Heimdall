package com.willis.heimdall

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

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

    @Secured(['ROLE_ADMIN'])
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
        try {
            def environment = Environment.findById(params.id)

            if (!environment) {
                response.sendError(404)
            } else {
                [environment: environment]
            }
        } catch (Exception e) {
            log.error("Error during call to ${controllerName}/${actionName} action: ", e)
        }
    }

    def bookingList = {
        try {
            def environment = Environment.findById(params.id)

            if (!environment) {
                response.sendError(404)
            } else {
                def bookingList = bookingService.listBookingsJSON(environment)
                render bookingList
            }
        } catch (Exception e) {
            log.error("Error during call to ${controllerName}/${actionName} action: ", e)
        }
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
