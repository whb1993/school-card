package sc.repository;

import sc.domain.PressInf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PressInf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PressInfRepository extends JpaRepository<PressInf, Long> {

}
