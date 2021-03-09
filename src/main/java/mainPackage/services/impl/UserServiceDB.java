package mainPackage.services.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;
import  mainPackage.repositories.UserRepository;

@Component
public class UserServiceDB implements UserDetailsService {

    private final  UserRepository userRepository;

    public UserServiceDB(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        mainPackage.models.entities.User user = userRepository.
                findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " was not found!"));

        return mapToUserDetails(user);
    }

    private UserDetails mapToUserDetails(mainPackage.models.entities.User user) {
        List<GrantedAuthority> authorities =
                user.getRoles()
                        .stream()
                       .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRole().name()))
                        .collect(Collectors.toList());

        return new User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}