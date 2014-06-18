<html>
	<head>
		<title>
			Calendar for ${environment.name}
		</title>
		<meta name="layout" content="main"/>
		<link rel='stylesheet' href="${resource(dir: 'css', file: 'fullcalendar.css')}"/>
		<script src="${resource(dir: 'js/fullcalendar/lib/', file: 'jquery.min.js')}"></script>		
		<script src="${resource(dir: 'js/fullcalendar/lib/', file: 'moment.min.js')}"></script>		
		<script src="${resource(dir: 'js/fullcalendar/', file: 'fullcalendar.js')}"></script>	
		
		<script type="text/javascript">
			$(document).ready(function() {
			    $('#calendar').fullCalendar({
                    header: {
                        left: 'prev,next today',
                        center: 'title',
                        right: 'month,agendaWeek,agendaDay'
                    }
			    })	
			});
		</script>
		
	</head>
	<body>
		<h1>Calendar for ${environment.name}</h1>
		
		<div id='calendar'></div>
	</body>
</html>