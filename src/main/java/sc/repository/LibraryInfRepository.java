package sc.repository;

import sc.domain.LibraryInf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LibraryInf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LibraryInfRepository extends JpaRepository<LibraryInf, Long> {

}
