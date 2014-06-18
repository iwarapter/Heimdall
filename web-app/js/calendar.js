/**
 * Created by Sion on 18/06/2014.
 */

$(document).ready(function() {
    renderCalendar();
});


function renderCalendar() {
    $("#calendar").fullCalendar({
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        }
    });
}