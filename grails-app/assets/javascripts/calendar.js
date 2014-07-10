/**
 * Calendar Javascript
 * @author Sion Williams
 */

$(document).ready(function() {
    renderCalendar();
});

var result = ${remoteFunction(controller: 'environment', action: 'bookingList',
                                  update: 'none',
                                   params:'\'id=\'+id+\')}
function renderCalendar() {
    $("#calendar").fullCalendar({
        events: result,
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        }
    });
}
