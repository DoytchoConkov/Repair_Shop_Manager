package mainPackage.init;

import mainPackage.models.entities.RoleName;
import mainPackage.models.entities.UserRole;
import mainPackage.repositories.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationInitialisation implements CommandLineRunner {
    private final UserRoleRepository userRoleRepository;

    public ApplicationInitialisation(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(String... args) {
        if (userRoleRepository.count() == 0) {
            for (RoleName r : RoleName.values()) {
                UserRole userRole = new UserRole(r);
                userRoleRepository.save(userRole);
            }
        }
    }
}
