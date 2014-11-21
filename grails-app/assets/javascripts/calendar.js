/**
 * Calendar Javascript
 * @author Sion Williams
 */

$(document).ready(function() {
    renderCalendar();
});

/**
 * The render calendar function calls the bookingList action on the Environment Controller. This retrieves all of the bookings
 * linked to that environment. envId is a global property which is set in calendar.gsp.
 */
function renderCalendar() {
    $("#calendar").fullCalendar({
        events: '../bookingList/' + envId,
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        }
    });
}
