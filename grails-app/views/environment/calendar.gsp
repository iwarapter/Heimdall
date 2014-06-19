<html>
	<head>
		<title>
			Calendar for ${environment.name}
		</title>
		<meta name="layout" content="main"/>
        <asset:stylesheet src="/fullcalendar/fullcalendar.css"/>
        <asset:javascript src="/fullcalendar/lib/moment.min.js"/>
        <asset:javascript src="/fullcalendar/fullcalendar.js"/>
        <asset:javascript src="calendar.js"/>

    </head>
	<body>
		<h1>Calendar for ${environment.name}</h1>
		
		<div id='calendar'></div>
	</body>
</html>