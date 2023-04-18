package pl.krax.vetclinic.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.krax.vetclinic.entities.PetOwner;

import java.util.List;

public interface PetOwnerRepository extends JpaRepository<PetOwner, Long> {
    @Query("SELECT o FROM PetOwner o WHERE o.name LIKE %:searchPhrase%")
    List<PetOwner> findByNameSearchedPhrase(@Param("searchPhrase")String searchPhrase, Pageable pageable);
    @Query("SELECT o FROM PetOwner o WHERE o.street LIKE %:searchPhrase% OR o.city LIKE %:searchPhrase%")
    List<PetOwner> findByAddressSearchedPhrase(@Param("searchPhrase")String searchPhrase, Pageable pageable);
    @Query("SELECT o FROM PetOwner o WHERE o.phoneNumber LIKE %:searchPhrase%")
    List<PetOwner> findByPhoneNumberSearchedPhrase(@Param("searchPhrase")String searchPhrase, Pageable pageable);
    @Query("SELECT o FROM PetOwner o WHERE o.email LIKE %:searchPhrase%")
    List<PetOwner> findByEmailSearchedPhrase(@Param("searchPhrase")String searchPhrase, Pageable pageable);
    @Query("SELECT o FROM PetOwner o WHERE o.nip LIKE %:searchPhrase%")
    List<PetOwner> findByNipSearchedPhrase(@Param("searchPhrase")String searchPhrase, Pageable pageable);
}
