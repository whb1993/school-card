package sc.repository;

import sc.domain.FillInf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FillInf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FillInfRepository extends JpaRepository<FillInf, Long> {

}
