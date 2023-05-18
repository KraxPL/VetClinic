package pl.krax.vetclinic.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.krax.vetclinic.entities.Role;
import pl.krax.vetclinic.entities.Vet;
import pl.krax.vetclinic.service.VetService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpringDataUserDetailsServiceTest {

    @Mock
    private VetService vetService;

    @InjectMocks
    private SpringDataUserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_WhenVetExists_ShouldReturnUserDetails() {
        String email = "vet@example.com";
        Vet vet = new Vet();
        vet.setEmail(email);
        vet.setPassword("password");
        vet.setActiveAccount(1);
        Role role = new Role(1L, "ROLE_USER");
        vet.setRoles(new HashSet<>(Collections.singletonList(role)));

        when(vetService.findByVetEmail(email)).thenReturn(vet);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.isEnabled());

        Set<String> expectedAuthorities = new HashSet<>();
        expectedAuthorities.add("ROLE_USER");
        assertEquals(expectedAuthorities.size(), userDetails.getAuthorities().size());
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            assertTrue(expectedAuthorities.contains(authority.getAuthority()));
        }

        verify(vetService, times(1)).findByVetEmail(email);
    }

    @Test
    void loadUserByUsername_WhenVetDoesNotExist_ShouldThrowUsernameNotFoundException() {
        String email = "vet@example.com";

        when(vetService.findByVetEmail(email)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(email));

        verify(vetService, times(1)).findByVetEmail(email);
    }
}