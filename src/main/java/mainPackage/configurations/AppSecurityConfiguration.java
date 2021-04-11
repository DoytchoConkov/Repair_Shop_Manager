package mainPackage.configurations;

import mainPackage.services.impl.UserServiceDB;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class AppSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserServiceDB userServiceDB;
    private final PasswordEncoder passwordEncoder;

    public AppSecurityConfiguration(UserServiceDB userServiceDB, PasswordEncoder passwordEncoder) {
        this.userServiceDB = userServiceDB;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/js/**", "/css/**", "/img/**").permitAll()
                .antMatchers("/", "/users/register", "/users/login").anonymous()
                .antMatchers("/**").authenticated()
//                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/users/login")
                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                .defaultSuccessUrl("/home")
                .failureForwardUrl("/users/login-error")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
        http.csrf().csrfTokenRepository(csrfTokenRepository());


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                userDetailsService(userServiceDB).
                passwordEncoder(passwordEncoder);
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new
                HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;

    }
}
