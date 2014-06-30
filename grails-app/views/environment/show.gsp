<%@ page import="com.willis.heimdall.Environment" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'environment.label', default: 'Environment')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-environment" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                  default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
        <li><g:link class="calendar" action="calendar" resource="${environmentInstance}">
            <g:message code="default.button.calendar.label" default="Calendar" args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="show-environment" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list environment">

        <g:if test="${environmentInstance?.name}">
            <li class="fieldcontain">
                <span id="name-label" class="property-label"><g:message code="environment.name.label"
                                                                        default="Name"/></span>

                <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${environmentInstance}"
                                                                                        field="name"/></span>

            </li>
        </g:if>

        <g:if test="${environmentInstance?.description}">
            <li class="fieldcontain">
                <span id="description-label" class="property-label"><g:message code="environment.description.label"
                                                                               default="Description"/></span>

                <span class="property-value" aria-labelledby="description-label"><g:fieldValue
                        bean="${environmentInstance}" field="description"/></span>

            </li>
        </g:if>

        <g:if test="${environmentInstance?.integrations}">
            <li class="fieldcontain">
                <span id="integrations-label" class="property-label"><g:message code="environment.integrations.label"
                                                                                default="Integrations"/></span>

                <g:each in="${environmentInstance.integrations}" var="i">
                    <span class="property-value" aria-labelledby="integrations-label"><g:link controller="environment"
                                                                                              action="show"
                                                                                              id="${i.id}">${i?.encodeAsHTML()}</g:link></span>
                </g:each>

            </li>
        </g:if>

        <g:if test="${environmentInstance?.system}">
            <li class="fieldcontain">
                <span id="system-label" class="property-label"><g:message code="environment.system.label"
                                                                          default="System"/></span>

                <span class="property-value" aria-labelledby="system-label"><g:fieldValue bean="${environmentInstance}"
                                                                                          field="system"/></span>

            </li>
        </g:if>

        <g:if test="${environmentInstance?.url}">
            <li class="fieldcontain">
                <span id="url-label" class="property-label"><g:message code="environment.url.label"
                                                                       default="Url"/></span>

                <span class="property-value" aria-labelledby="url-label"><g:fieldValue bean="${environmentInstance}"
                                                                                       field="url"/></span>

            </li>
        </g:if>

        <g:if test="${environmentInstance?.phaseUsage}">
            <li class="fieldcontain">
                <span id="phaseUsage-label" class="property-label"><g:message code="environment.phaseUsage.label"
                                                                              default="Phase Usage"/></span>

                <span class="property-value" aria-labelledby="phaseUsage-label"><g:fieldValue
                        bean="${environmentInstance}" field="phaseUsage"/></span>

            </li>
        </g:if>

        <g:if test="${environmentInstance?.vendor}">
            <li class="fieldcontain">
                <span id="vendor-label" class="property-label"><g:message code="environment.vendor.label"
                                                                          default="Vendor"/></span>

                <span class="property-value" aria-labelledby="vendor-label"><g:fieldValue bean="${environmentInstance}"
                                                                                          field="vendor"/></span>

            </li>
        </g:if>

        <g:if test="${environmentInstance?.status}">
            <li class="fieldcontain">
                <span id="status-label" class="property-label"><g:message code="environment.status.label"
                                                                          default="Status"/></span>

                <span class="property-value" aria-labelledby="status-label"><g:fieldValue bean="${environmentInstance}"
                                                                                          field="status"/></span>

            </li>
        </g:if>

        <g:if test="${environmentInstance?.bookings}">
            <li class="fieldcontain">
                <span id="bookings-label" class="property-label"><g:message code="environment.bookings.label"
                                                                            default="Bookings"/></span>

                <g:each in="${environmentInstance.bookings}" var="b">
                    <span class="property-value" aria-labelledby="bookings-label"><g:link controller="booking"
                                                                                          action="show"
                                                                                          id="${b.id}">${b?.encodeAsHTML()}</g:link></span>
                </g:each>

            </li>
        </g:if>

    </ol>
    <g:form url="[resource: environmentInstance, action: 'delete']" method="DELETE">
        <fieldset class="buttons">
            <g:link class="edit" action="edit" resource="${environmentInstance}"><g:message
                    code="default.button.edit.label" default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
