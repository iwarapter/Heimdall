<html>
	<head>
		<title>
			Calendar for ${environment.name}
		</title>
        <g:set var="entityName" value="${message(code: 'environment.label', default: 'Environment')}"/>
		<meta name="layout" content="main"/>
        <asset:stylesheet src="/fullcalendar/fullcalendar.css"/>
        <asset:javascript src="/fullcalendar/lib/moment.min.js"/>
        <asset:javascript src="/fullcalendar/fullcalendar.js"/>
        <asset:javascript src="calendar.js"/>

    </head>
	<body>
		<h1>Calendar for ${environment.name}</h1>

        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
            </ul>
        </div>
		<g:javascript>
			var envId = ${params.id}
		</g:javascript>

		<div id='calendar'></div>
	</body>
</html>