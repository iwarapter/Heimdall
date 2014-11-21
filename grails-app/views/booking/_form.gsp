<%@ page import="com.willis.heimdall.Booking" %>



<div class="fieldcontain ${hasErrors(bean: bookingInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="booking.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${bookingInstance?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: bookingInstance, field: 'startTime', 'error')} required">
	<label for="startTime">
		<g:message code="booking.startTime.label" default="Start Date"/>
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="startTime" precision="day" value="${bookingInstance?.startTime}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: bookingInstance, field: 'endTime', 'error')} required">
	<label for="endTime">
		<g:message code="booking.endTime.label" default="End Date"/>
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="endTime" precision="day" value="${bookingInstance?.endTime}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: bookingInstance, field: 'user', 'error')} ">
	<label for="user">
		<g:message code="booking.user.label" default="User" />
		
	</label>
	<g:select id="user" name="user.id" from="${com.willis.heimdall.User.list()}" optionKey="id" value="${bookingInstance?.user?.id}" class="many-to-one" noSelection="['null': '']"/>

</div>

