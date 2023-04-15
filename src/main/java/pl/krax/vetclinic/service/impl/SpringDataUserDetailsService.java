package pl.krax.vetclinic.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.krax.vetclinic.entities.Vet;
import pl.krax.vetclinic.security.CurrentUser;
import pl.krax.vetclinic.service.VetService;

import java.util.HashSet;
import java.util.Set;
@Service
@Transactional
public class SpringDataUserDetailsService implements UserDetailsService {
    @Autowired
    private VetService vetService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Vet vet = vetService.findByVetEmail(email);
        if (vet == null) {
            throw new UsernameNotFoundException(email);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        vet.getRoles().forEach(r ->
                grantedAuthorities.add(new SimpleGrantedAuthority(r.getName())));
        return new CurrentUser(
                vet.getEmail(), vet.getPassword(),
                grantedAuthorities, vet);
    }
}
