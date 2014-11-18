import com.willis.heimdall.User
import com.willis.heimdall.Role
import com.willis.heimdall.UserRole

class BootStrap {

    def init = { servletContext ->
        Role adminRole = Role.findOrSaveWhere(authority: 'ROLE_ADMIN')
        Role userRole = Role.findOrSaveWhere(authority: 'ROLE_USER')
        User adminUser = User.findOrSaveWhere(username: 'admin', password: 'admin123', firstName: 'admin', lastName: 'admin', email: 'admin@heimdall.com')

        if (!adminUser.authorities.contains(adminRole)) {
            UserRole.create(adminUser, adminRole, false)
        }
    }

    def destroy = {
    }
}
