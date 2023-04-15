package pl.krax.vetclinic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.krax.vetclinic.entities.Vet;
import pl.krax.vetclinic.service.VetService;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class SpringDataUserDetailsService implements UserDetailsService {

    private final VetService vetService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Vet vet = vetService.findByVetEmail(email);
        if (vet == null) {
            throw new UsernameNotFoundException(email);
        }
        boolean active = vet.getActiveAccount() == 1;
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        vet.getRoles().forEach(r ->
                grantedAuthorities.add(new SimpleGrantedAuthority(r.getName())));
        return new User(
                vet.getEmail(), vet.getPassword(), active, true, true, true, grantedAuthorities);
    }

}
