package sc.web.rest;
import sc.domain.PressInf;
import sc.repository.PressInfRepository;
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
 * REST controller for managing PressInf.
 */
@RestController
@RequestMapping("/api")
public class PressInfResource {

    private final Logger log = LoggerFactory.getLogger(PressInfResource.class);

    private static final String ENTITY_NAME = "pressInf";

    private final PressInfRepository pressInfRepository;

    public PressInfResource(PressInfRepository pressInfRepository) {
        this.pressInfRepository = pressInfRepository;
    }

    /**
     * POST  /press-infs : Create a new pressInf.
     *
     * @param pressInf the pressInf to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pressInf, or with status 400 (Bad Request) if the pressInf has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/press-infs")
    public ResponseEntity<PressInf> createPressInf(@RequestBody PressInf pressInf) throws URISyntaxException {
        log.debug("REST request to save PressInf : {}", pressInf);
        if (pressInf.getId() != null) {
            throw new BadRequestAlertException("A new pressInf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PressInf result = pressInfRepository.save(pressInf);
        return ResponseEntity.created(new URI("/api/press-infs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /press-infs : Updates an existing pressInf.
     *
     * @param pressInf the pressInf to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pressInf,
     * or with status 400 (Bad Request) if the pressInf is not valid,
     * or with status 500 (Internal Server Error) if the pressInf couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/press-infs")
    public ResponseEntity<PressInf> updatePressInf(@RequestBody PressInf pressInf) throws URISyntaxException {
        log.debug("REST request to update PressInf : {}", pressInf);
        if (pressInf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PressInf result = pressInfRepository.save(pressInf);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pressInf.getId().toString()))
            .body(result);
    }

    /**
     * GET  /press-infs : get all the pressInfs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pressInfs in body
     */
    @GetMapping("/press-infs")
    public List<PressInf> getAllPressInfs() {
        log.debug("REST request to get all PressInfs");
        return pressInfRepository.findAll();
    }

    /**
     * GET  /press-infs/:id : get the "id" pressInf.
     *
     * @param id the id of the pressInf to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pressInf, or with status 404 (Not Found)
     */
    @GetMapping("/press-infs/{id}")
    public ResponseEntity<PressInf> getPressInf(@PathVariable Long id) {
        log.debug("REST request to get PressInf : {}", id);
        Optional<PressInf> pressInf = pressInfRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pressInf);
    }

    /**
     * DELETE  /press-infs/:id : delete the "id" pressInf.
     *
     * @param id the id of the pressInf to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/press-infs/{id}")
    public ResponseEntity<Void> deletePressInf(@PathVariable Long id) {
        log.debug("REST request to delete PressInf : {}", id);
        pressInfRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
