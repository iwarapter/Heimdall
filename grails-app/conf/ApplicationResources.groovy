modules = {
    application {
        resource url:'js/application.js'
    }

    fullCalendar {
        resource url:'css/fullcalendar.css'
        resource url:'js/fullcalendar/lib/jquery.min.js'
        resource url:'js/fullcalendar/lib/moment.min.js'
        resource url:'js/fullcalendar/fullcalendar.js'
    }

    calendar {
        dependsOn 'fullCalendar'

        resource url: 'js/calendar.js'
    }
}