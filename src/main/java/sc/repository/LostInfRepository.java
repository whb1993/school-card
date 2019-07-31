package sc.repository;

import sc.domain.LostInf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LostInf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LostInfRepository extends JpaRepository<LostInf, Long> {

}
