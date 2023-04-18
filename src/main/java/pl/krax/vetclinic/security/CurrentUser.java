package pl.krax.vetclinic.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import pl.krax.vetclinic.dto.VetDto;
import pl.krax.vetclinic.entities.Vet;

import java.util.Collection;
public class CurrentUser extends User {
    private final VetDto vetDto;
    private final int activeAccount;
    public CurrentUser(String email, String password,
                       Collection<? extends GrantedAuthority> authorities,
                       VetDto vetDto, int activeAccount) {
        super(email, password, authorities);
        this.vetDto = vetDto;
        this.activeAccount = activeAccount;
    }
    public VetDto getUser() {return vetDto;}
    public int getActiveAccount() {return activeAccount;}
}
