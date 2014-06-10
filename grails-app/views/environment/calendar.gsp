<html>
	<head>
		<title>
			Calendar for
		</title>
		<meta name="layout" content="main"/>
		<link rel='stylesheet' href="${resource(dir: 'css', file: 'fullcalendar.css')}"/>
		<script src="${resource(dir: 'js/fullcalendar/lib/', file: 'jquery.min.js')}"></script>		
		<script src="${resource(dir: 'js/fullcalendar/lib/', file: 'moment.min.js')}"></script>		
		<script src="${resource(dir: 'js/fullcalendar/', file: 'fullcalendar.js')}"></script>	
		
		<script type="text/javascript">
			$(document).ready(function() {
	
			    // page is now ready, initialize the calendar...	
			    $('#calendar').fullCalendar({
			        // put your options and callbacks here
			        // weekends: false // will hide Saturdays and Sundays
			    })	
			});
		</script>
		
	</head>
	<body>
		<h1>Calendar for</h1>
		
		<div id='calendar'></div>
	</body>
</html>