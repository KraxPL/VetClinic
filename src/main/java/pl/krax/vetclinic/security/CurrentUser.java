package pl.krax.vetclinic.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import pl.krax.vetclinic.entities.Vet;

import java.util.Collection;

public class CurrentUser extends User {
    private final Vet vet;
    public CurrentUser(String email, String password,
                       Collection<? extends GrantedAuthority> authorities,
                       Vet vet) {
        super(email, password, authorities);
        this.vet = vet;
    }
    public Vet getUser() {return vet;}
}
