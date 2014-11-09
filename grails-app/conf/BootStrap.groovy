import com.willis.heimdall.User
import com.willis.heimdall.Role
import com.willis.heimdall.UserRole

class BootStrap {

    def init = { servletContext ->
        Role adminRole = Role.findOrSaveWhere(authority: 'ROLE_ADMIN')
        User adminUser = User.findOrSaveWhere(username: 'sion', password: 'secret', firstName: 'sion', lastName: 'williams', email: 'sion@williams.com')

        if (!adminUser.authorities.contains(adminRole)) {
            UserRole.create(adminUser, adminRole, false)
        }
    }

    def destroy = {
    }
}
