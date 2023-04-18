package pl.krax.vetclinic.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.krax.vetclinic.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
