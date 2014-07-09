/**
 * Calendar Javascript
 * @author Sion Williams
 */

$(document).ready(function() {
    renderCalendar();
});

function renderCalendar() {
    $("#calendar").fullCalendar({
        events: function () {
            var url = "/Heimdall/environment/bookingList/";
            var envId = "${environment.id}";
            $.get(url, { id: envId })
        },
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        }
    });
}
