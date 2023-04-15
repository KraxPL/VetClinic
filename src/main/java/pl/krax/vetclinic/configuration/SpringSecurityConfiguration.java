package pl.krax.vetclinic.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
                .csrf().disable()
                .authorizeHttpRequests().shouldFilterAllDispatcherTypes(false).and()
                .authorizeHttpRequests(authorization ->
                        authorization
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
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
