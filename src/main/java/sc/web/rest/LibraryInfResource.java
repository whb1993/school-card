package sc.web.rest;
import sc.domain.LibraryInf;
import sc.repository.LibraryInfRepository;
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
 * REST controller for managing LibraryInf.
 */
@RestController
@RequestMapping("/api")
public class LibraryInfResource {

    private final Logger log = LoggerFactory.getLogger(LibraryInfResource.class);

    private static final String ENTITY_NAME = "libraryInf";

    private final LibraryInfRepository libraryInfRepository;

    public LibraryInfResource(LibraryInfRepository libraryInfRepository) {
        this.libraryInfRepository = libraryInfRepository;
    }

    /**
     * POST  /library-infs : Create a new libraryInf.
     *
     * @param libraryInf the libraryInf to create
     * @return the ResponseEntity with status 201 (Created) and with body the new libraryInf, or with status 400 (Bad Request) if the libraryInf has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/library-infs")
    public ResponseEntity<LibraryInf> createLibraryInf(@RequestBody LibraryInf libraryInf) throws URISyntaxException {
        log.debug("REST request to save LibraryInf : {}", libraryInf);
        if (libraryInf.getId() != null) {
            throw new BadRequestAlertException("A new libraryInf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LibraryInf result = libraryInfRepository.save(libraryInf);
        return ResponseEntity.created(new URI("/api/library-infs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /library-infs : Updates an existing libraryInf.
     *
     * @param libraryInf the libraryInf to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated libraryInf,
     * or with status 400 (Bad Request) if the libraryInf is not valid,
     * or with status 500 (Internal Server Error) if the libraryInf couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/library-infs")
    public ResponseEntity<LibraryInf> updateLibraryInf(@RequestBody LibraryInf libraryInf) throws URISyntaxException {
        log.debug("REST request to update LibraryInf : {}", libraryInf);
        if (libraryInf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LibraryInf result = libraryInfRepository.save(libraryInf);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, libraryInf.getId().toString()))
            .body(result);
    }

    /**
     * GET  /library-infs : get all the libraryInfs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of libraryInfs in body
     */
    @GetMapping("/library-infs")
    public List<LibraryInf> getAllLibraryInfs() {
        log.debug("REST request to get all LibraryInfs");
        return libraryInfRepository.findAll();
    }

    /**
     * GET  /library-infs/:id : get the "id" libraryInf.
     *
     * @param id the id of the libraryInf to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the libraryInf, or with status 404 (Not Found)
     */
    @GetMapping("/library-infs/{id}")
    public ResponseEntity<LibraryInf> getLibraryInf(@PathVariable Long id) {
        log.debug("REST request to get LibraryInf : {}", id);
        Optional<LibraryInf> libraryInf = libraryInfRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(libraryInf);
    }

    /**
     * DELETE  /library-infs/:id : delete the "id" libraryInf.
     *
     * @param id the id of the libraryInf to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/library-infs/{id}")
    public ResponseEntity<Void> deleteLibraryInf(@PathVariable Long id) {
        log.debug("REST request to delete LibraryInf : {}", id);
        libraryInfRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
