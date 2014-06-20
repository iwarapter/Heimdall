import com.willis.heimdall.Booking
import com.willis.heimdall.Environment
import com.willis.heimdall.User

class BootStrap {

    Date firstDate = new Date()
    Date secondDate = new Date() + 7

    def init = { servletContext ->
        User userJohn = new User( firstName: 'John', lastName: 'Smith', email: 'john@smith.com', role: 'Requestor', status: 'Active' ).save()
        User userTracy = new User( firstName: 'tracy', lastName: 'jones', email: 'tracy@jones.co.uk', role: 'Admin', status: 'Active' ).save()

        Booking bookingSit = new Booking( name: 'Booking 1 SIT', startDate: firstDate, endDate: secondDate, user: userJohn ).save()
        Booking bookingUat = new Booking( name: 'Booking 1 UAT', startDate: firstDate, endDate: secondDate, user: userTracy ).save()

        Environment envDev = new Environment( name : 'webappdb-dev', description : 'Development database for Webapps', phaseUsage : 'DEV', status : 'Active' ).save()
        Environment envSys = new Environment( name : 'webappdb-sit', description : 'System Integration Testing database for Webapps', phaseUsage : 'SIT', status : 'Active', bookings: bookingSit ).save()
        Environment envUat = new Environment( name : 'webappdb-uat', description : 'User Acceptance Testing database for Webapps', phaseUsage : 'UAT', status : 'Active', bookings: bookingUat ).save()
    }

    def destroy = {
    }
}
