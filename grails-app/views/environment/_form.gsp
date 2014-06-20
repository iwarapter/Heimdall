<%@ page import="com.willis.heimdall.Environment" %>



<div class="fieldcontain ${hasErrors(bean: environmentInstance, field: 'name', 'error')} required">
    <label for="name">
        <g:message code="environment.name.label" default="Name"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="name" required="" value="${environmentInstance?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: environmentInstance, field: 'description', 'error')} required">
    <label for="description">
        <g:message code="environment.description.label" default="Description"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="description" maxlength="250" required="" value="${environmentInstance?.description}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: environmentInstance, field: 'integrations', 'error')} ">
    <label for="integrations">
        <g:message code="environment.integrations.label" default="Integrations"/>

    </label>
    <g:select name="integrations" from="${com.willis.heimdall.Environment.list()}" multiple="multiple" optionKey="id"
              size="5" value="${environmentInstance?.integrations*.id}" class="many-to-many"/>

</div>

<div class="fieldcontain ${hasErrors(bean: environmentInstance, field: 'system', 'error')} ">
    <label for="system">
        <g:message code="environment.system.label" default="System"/>

    </label>
    <g:textField name="system" maxlength="100" value="${environmentInstance?.system}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: environmentInstance, field: 'url', 'error')} ">
    <label for="url">
        <g:message code="environment.url.label" default="Url"/>

    </label>
    <g:textField name="url" value="${environmentInstance?.url}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: environmentInstance, field: 'phaseUsage', 'error')} required">
    <label for="phaseUsage">
        <g:message code="environment.phaseUsage.label" default="Phase Usage"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select name="phaseUsage" from="${environmentInstance.constraints.phaseUsage.inList}" required=""
              value="${environmentInstance?.phaseUsage}" valueMessagePrefix="environment.phaseUsage"/>

</div>

<div class="fieldcontain ${hasErrors(bean: environmentInstance, field: 'vendor', 'error')} ">
    <label for="vendor">
        <g:message code="environment.vendor.label" default="Vendor"/>

    </label>
    <g:textField name="vendor" value="${environmentInstance?.vendor}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: environmentInstance, field: 'status', 'error')} required">
    <label for="status">
        <g:message code="environment.status.label" default="Status"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select name="status" from="${environmentInstance.constraints.status.inList}" required=""
              value="${environmentInstance?.status}" valueMessagePrefix="environment.status"/>

</div>

<div class="fieldcontain ${hasErrors(bean: environmentInstance, field: 'bookings', 'error')} ">
    <label for="bookings">
        <g:message code="environment.bookings.label" default="Bookings"/>

    </label>
    <g:select name="bookings" from="${com.willis.heimdall.Booking.list()}" multiple="multiple" optionKey="id" size="5"
              value="${environmentInstance?.bookings*.id}" class="many-to-many"/>

</div>

