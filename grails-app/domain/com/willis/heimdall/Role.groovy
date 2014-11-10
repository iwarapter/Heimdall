package com.willis.heimdall

/**
 * Role model - Spring Security Core
 * @author Sion Williams
 */
class Role {

    String authority

    static mapping = {
        cache true
    }

    static constraints = {
        authority blank: false, unique: true
    }
}
