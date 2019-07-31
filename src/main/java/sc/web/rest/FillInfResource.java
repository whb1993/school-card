package sc.web.rest;
import sc.domain.FillInf;
import sc.repository.FillInfRepository;
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
 * REST controller for managing FillInf.
 */
@RestController
@RequestMapping("/api")
public class FillInfResource {

    private final Logger log = LoggerFactory.getLogger(FillInfResource.class);

    private static final String ENTITY_NAME = "fillInf";

    private final FillInfRepository fillInfRepository;

    public FillInfResource(FillInfRepository fillInfRepository) {
        this.fillInfRepository = fillInfRepository;
    }

    /**
     * POST  /fill-infs : Create a new fillInf.
     *
     * @param fillInf the fillInf to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fillInf, or with status 400 (Bad Request) if the fillInf has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fill-infs")
    public ResponseEntity<FillInf> createFillInf(@RequestBody FillInf fillInf) throws URISyntaxException {
        log.debug("REST request to save FillInf : {}", fillInf);
        if (fillInf.getId() != null) {
            throw new BadRequestAlertException("A new fillInf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FillInf result = fillInfRepository.save(fillInf);
        return ResponseEntity.created(new URI("/api/fill-infs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fill-infs : Updates an existing fillInf.
     *
     * @param fillInf the fillInf to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fillInf,
     * or with status 400 (Bad Request) if the fillInf is not valid,
     * or with status 500 (Internal Server Error) if the fillInf couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fill-infs")
    public ResponseEntity<FillInf> updateFillInf(@RequestBody FillInf fillInf) throws URISyntaxException {
        log.debug("REST request to update FillInf : {}", fillInf);
        if (fillInf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FillInf result = fillInfRepository.save(fillInf);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fillInf.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fill-infs : get all the fillInfs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fillInfs in body
     */
    @GetMapping("/fill-infs")
    public List<FillInf> getAllFillInfs() {
        log.debug("REST request to get all FillInfs");
        return fillInfRepository.findAll();
    }

    /**
     * GET  /fill-infs/:id : get the "id" fillInf.
     *
     * @param id the id of the fillInf to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fillInf, or with status 404 (Not Found)
     */
    @GetMapping("/fill-infs/{id}")
    public ResponseEntity<FillInf> getFillInf(@PathVariable Long id) {
        log.debug("REST request to get FillInf : {}", id);
        Optional<FillInf> fillInf = fillInfRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fillInf);
    }

    /**
     * DELETE  /fill-infs/:id : delete the "id" fillInf.
     *
     * @param id the id of the fillInf to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fill-infs/{id}")
    public ResponseEntity<Void> deleteFillInf(@PathVariable Long id) {
        log.debug("REST request to delete FillInf : {}", id);
        fillInfRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
