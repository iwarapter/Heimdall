
<%@ page import="com.willis.heimdall.Booking" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'booking.label', default: 'Booking')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-booking" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-booking" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'booking.name.label', default: 'Name')}" />

						<g:sortableColumn property="startTime"
										  title="${message(code: 'booking.startTime.label', default: 'Start Date')}"/>

						<g:sortableColumn property="endTime"
										  title="${message(code: 'booking.endTime.label', default: 'End Date')}"/>
					
						<th><g:message code="booking.user.label" default="User" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${bookingInstanceList}" status="i" var="bookingInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${bookingInstance.id}">${fieldValue(bean: bookingInstance, field: "name")}</g:link></td>

						<td><g:formatDate date="${bookingInstance.startTime}"/></td>

						<td><g:formatDate date="${bookingInstance.endTime}"/></td>
					
						<td>${fieldValue(bean: bookingInstance, field: "user")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${bookingInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
