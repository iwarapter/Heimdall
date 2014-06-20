/**
 * Calendar Javascript
 * @author Sion Williams
 */

$(document).ready(function() {
    renderCalendar();
});


function renderCalendar() {
    $("#calendar").fullCalendar({
        events: 'bookingList.json',
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        }
    });
}