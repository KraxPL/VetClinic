package pl.krax.vetclinic.security.configuration;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pl.krax.vetclinic.service.impl.SpringDataUserDetailsService;

@Configuration
@EnableWebSecurity
class SpringSecurityConfiguration {
    @Bean
    public UserDetailsService userDetailsService() {
        return new SpringDataUserDetailsService();
    }
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeHttpRequests(authorization ->
                        authorization
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/booking/**").permitAll()
                                .requestMatchers("/vets/**").hasRole("ADMIN")
                                .anyRequest()
                                .authenticated())
                .formLogin(form ->
                        form
                                .loginPage("/login")
                                .usernameParameter("email")
                                .defaultSuccessUrl("/index")
                                .permitAll())
                .logout().logoutSuccessUrl("/login?logout")
                .and().exceptionHandling()
                                .accessDeniedPage("/403")
                .and().authenticationProvider(authenticationProvider());
        return httpSecurity.build();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
