import com.willis.heimdall.Environment
import com.willis.heimdall.User

class BootStrap {

    def init = { servletContext ->
        Environment envDev = new Environment( name : 'webapp-dev', description : 'Development for Webapps', phaseUsage : 'DEV', status : 'Active' ).save()
        Environment envSys = new Environment( name : 'webapp-sit', description : 'System Integration Testing for Webapps', phaseUsage : 'SIT', status : 'Active' ).save()
        Environment envUat = new Environment( name : 'webapp-uat', description : 'User Acceptance Testing for Webapps', phaseUsage : 'UAT', status : 'Active' ).save()

        User userJohn = new User( firstName: 'John', lastName: 'Smith', email: 'john@smith.com', role: 'Requestor', status: 'Active' ).save()
        User userSion = new User( firstName: 'Sion', lastName: 'Williams', email: 'my@email.co.uk', role: 'Admin', status: 'Active' ).save()
    }
    def destroy = {
    }
}
