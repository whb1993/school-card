package sc.web.rest;
import sc.domain.LostInf;
import sc.repository.LostInfRepository;
import sc.web.rest.errors.BadRequestAlertException;
import sc.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LostInf.
 */
@RestController
@RequestMapping("/api")
public class LostInfResource {

    private final Logger log = LoggerFactory.getLogger(LostInfResource.class);

    private static final String ENTITY_NAME = "lostInf";

    private final LostInfRepository lostInfRepository;

    public LostInfResource(LostInfRepository lostInfRepository) {
        this.lostInfRepository = lostInfRepository;
    }

    /**
     * POST  /lost-infs : Create a new lostInf.
     *
     * @param lostInf the lostInf to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lostInf, or with status 400 (Bad Request) if the lostInf has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lost-infs")
    public ResponseEntity<LostInf> createLostInf(@RequestBody LostInf lostInf) throws URISyntaxException {
        log.debug("REST request to save LostInf : {}", lostInf);
        if (lostInf.getId() != null) {
            throw new BadRequestAlertException("A new lostInf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LostInf result = lostInfRepository.save(lostInf);
        return ResponseEntity.created(new URI("/api/lost-infs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lost-infs : Updates an existing lostInf.
     *
     * @param lostInf the lostInf to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lostInf,
     * or with status 400 (Bad Request) if the lostInf is not valid,
     * or with status 500 (Internal Server Error) if the lostInf couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lost-infs")
    public ResponseEntity<LostInf> updateLostInf(@RequestBody LostInf lostInf) throws URISyntaxException {
        log.debug("REST request to update LostInf : {}", lostInf);
        if (lostInf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LostInf result = lostInfRepository.save(lostInf);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lostInf.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lost-infs : get all the lostInfs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lostInfs in body
     */
    @GetMapping("/lost-infs")
    public List<LostInf> getAllLostInfs() {
        log.debug("REST request to get all LostInfs");
        return lostInfRepository.findAll();
    }

    /**
     * GET  /lost-infs/:id : get the "id" lostInf.
     *
     * @param id the id of the lostInf to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lostInf, or with status 404 (Not Found)
     */
    @GetMapping("/lost-infs/{id}")
    public ResponseEntity<LostInf> getLostInf(@PathVariable Long id) {
        log.debug("REST request to get LostInf : {}", id);
        Optional<LostInf> lostInf = lostInfRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lostInf);
    }

    /**
     * DELETE  /lost-infs/:id : delete the "id" lostInf.
     *
     * @param id the id of the lostInf to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lost-infs/{id}")
    public ResponseEntity<Void> deleteLostInf(@PathVariable Long id) {
        log.debug("REST request to delete LostInf : {}", id);
        lostInfRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
