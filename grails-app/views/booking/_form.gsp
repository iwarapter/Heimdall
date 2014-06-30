<%@ page import="com.willis.heimdall.Booking" %>



<div class="fieldcontain ${hasErrors(bean: bookingInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="booking.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${bookingInstance?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: bookingInstance, field: 'startDate', 'error')} required">
	<label for="startDate">
		<g:message code="booking.startDate.label" default="Start Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="startDate" precision="day"  value="${bookingInstance?.startDate}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: bookingInstance, field: 'endDate', 'error')} required">
	<label for="endDate">
		<g:message code="booking.endDate.label" default="End Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="endDate" precision="day"  value="${bookingInstance?.endDate}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: bookingInstance, field: 'user', 'error')} ">
	<label for="user">
		<g:message code="booking.user.label" default="User" />
		
	</label>
	<g:select id="user" name="user.id" from="${com.willis.heimdall.User.list()}" optionKey="id" value="${bookingInstance?.user?.id}" class="many-to-one" noSelection="['null': '']"/>

</div>

