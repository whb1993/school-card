package sc.web.rest;

import sc.SchoolCardApp;

import sc.domain.LibraryInf;
import sc.repository.LibraryInfRepository;
import sc.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static sc.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LibraryInfResource REST controller.
 *
 * @see LibraryInfResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchoolCardApp.class)
public class LibraryInfResourceIntTest {

    private static final String DEFAULT_BOOK_ID = "AAAAAAAAAA";
    private static final String UPDATED_BOOK_ID = "BBBBBBBBBB";

    private static final String DEFAULT_BOOKNO = "AAAAAAAAAA";
    private static final String UPDATED_BOOKNO = "BBBBBBBBBB";

    private static final String DEFAULT_CARDSTATES = "AAAAAAAAAA";
    private static final String UPDATED_CARDSTATES = "BBBBBBBBBB";

    private static final String DEFAULT_BORLIST = "AAAAAAAAAA";
    private static final String UPDATED_BORLIST = "BBBBBBBBBB";

    private static final String DEFAULT_ADID = "AAAAAAAAAA";
    private static final String UPDATED_ADID = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_ID = "AAAAAAAAAA";
    private static final String UPDATED_LIB_ID = "BBBBBBBBBB";

    @Autowired
    private LibraryInfRepository libraryInfRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restLibraryInfMockMvc;

    private LibraryInf libraryInf;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LibraryInfResource libraryInfResource = new LibraryInfResource(libraryInfRepository);
        this.restLibraryInfMockMvc = MockMvcBuilders.standaloneSetup(libraryInfResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LibraryInf createEntity(EntityManager em) {
        LibraryInf libraryInf = new LibraryInf()
            .bookId(DEFAULT_BOOK_ID)
            .bookno(DEFAULT_BOOKNO)
            .cardstates(DEFAULT_CARDSTATES)
            .borlist(DEFAULT_BORLIST)
            .adid(DEFAULT_ADID)
            .libId(DEFAULT_LIB_ID);
        return libraryInf;
    }

    @Before
    public void initTest() {
        libraryInf = createEntity(em);
    }

    @Test
    @Transactional
    public void createLibraryInf() throws Exception {
        int databaseSizeBeforeCreate = libraryInfRepository.findAll().size();

        // Create the LibraryInf
        restLibraryInfMockMvc.perform(post("/api/library-infs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libraryInf)))
            .andExpect(status().isCreated());

        // Validate the LibraryInf in the database
        List<LibraryInf> libraryInfList = libraryInfRepository.findAll();
        assertThat(libraryInfList).hasSize(databaseSizeBeforeCreate + 1);
        LibraryInf testLibraryInf = libraryInfList.get(libraryInfList.size() - 1);
        assertThat(testLibraryInf.getBookId()).isEqualTo(DEFAULT_BOOK_ID);
        assertThat(testLibraryInf.getBookno()).isEqualTo(DEFAULT_BOOKNO);
        assertThat(testLibraryInf.getCardstates()).isEqualTo(DEFAULT_CARDSTATES);
        assertThat(testLibraryInf.getBorlist()).isEqualTo(DEFAULT_BORLIST);
        assertThat(testLibraryInf.getAdid()).isEqualTo(DEFAULT_ADID);
        assertThat(testLibraryInf.getLibId()).isEqualTo(DEFAULT_LIB_ID);
    }

    @Test
    @Transactional
    public void createLibraryInfWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = libraryInfRepository.findAll().size();

        // Create the LibraryInf with an existing ID
        libraryInf.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibraryInfMockMvc.perform(post("/api/library-infs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libraryInf)))
            .andExpect(status().isBadRequest());

        // Validate the LibraryInf in the database
        List<LibraryInf> libraryInfList = libraryInfRepository.findAll();
        assertThat(libraryInfList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLibraryInfs() throws Exception {
        // Initialize the database
        libraryInfRepository.saveAndFlush(libraryInf);

        // Get all the libraryInfList
        restLibraryInfMockMvc.perform(get("/api/library-infs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(libraryInf.getId().intValue())))
            .andExpect(jsonPath("$.[*].bookId").value(hasItem(DEFAULT_BOOK_ID.toString())))
            .andExpect(jsonPath("$.[*].bookno").value(hasItem(DEFAULT_BOOKNO.toString())))
            .andExpect(jsonPath("$.[*].cardstates").value(hasItem(DEFAULT_CARDSTATES.toString())))
            .andExpect(jsonPath("$.[*].borlist").value(hasItem(DEFAULT_BORLIST.toString())))
            .andExpect(jsonPath("$.[*].adid").value(hasItem(DEFAULT_ADID.toString())))
            .andExpect(jsonPath("$.[*].libId").value(hasItem(DEFAULT_LIB_ID.toString())));
    }
    
    @Test
    @Transactional
    public void getLibraryInf() throws Exception {
        // Initialize the database
        libraryInfRepository.saveAndFlush(libraryInf);

        // Get the libraryInf
        restLibraryInfMockMvc.perform(get("/api/library-infs/{id}", libraryInf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(libraryInf.getId().intValue()))
            .andExpect(jsonPath("$.bookId").value(DEFAULT_BOOK_ID.toString()))
            .andExpect(jsonPath("$.bookno").value(DEFAULT_BOOKNO.toString()))
            .andExpect(jsonPath("$.cardstates").value(DEFAULT_CARDSTATES.toString()))
            .andExpect(jsonPath("$.borlist").value(DEFAULT_BORLIST.toString()))
            .andExpect(jsonPath("$.adid").value(DEFAULT_ADID.toString()))
            .andExpect(jsonPath("$.libId").value(DEFAULT_LIB_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLibraryInf() throws Exception {
        // Get the libraryInf
        restLibraryInfMockMvc.perform(get("/api/library-infs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibraryInf() throws Exception {
        // Initialize the database
        libraryInfRepository.saveAndFlush(libraryInf);

        int databaseSizeBeforeUpdate = libraryInfRepository.findAll().size();

        // Update the libraryInf
        LibraryInf updatedLibraryInf = libraryInfRepository.findById(libraryInf.getId()).get();
        // Disconnect from session so that the updates on updatedLibraryInf are not directly saved in db
        em.detach(updatedLibraryInf);
        updatedLibraryInf
            .bookId(UPDATED_BOOK_ID)
            .bookno(UPDATED_BOOKNO)
            .cardstates(UPDATED_CARDSTATES)
            .borlist(UPDATED_BORLIST)
            .adid(UPDATED_ADID)
            .libId(UPDATED_LIB_ID);

        restLibraryInfMockMvc.perform(put("/api/library-infs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLibraryInf)))
            .andExpect(status().isOk());

        // Validate the LibraryInf in the database
        List<LibraryInf> libraryInfList = libraryInfRepository.findAll();
        assertThat(libraryInfList).hasSize(databaseSizeBeforeUpdate);
        LibraryInf testLibraryInf = libraryInfList.get(libraryInfList.size() - 1);
        assertThat(testLibraryInf.getBookId()).isEqualTo(UPDATED_BOOK_ID);
        assertThat(testLibraryInf.getBookno()).isEqualTo(UPDATED_BOOKNO);
        assertThat(testLibraryInf.getCardstates()).isEqualTo(UPDATED_CARDSTATES);
        assertThat(testLibraryInf.getBorlist()).isEqualTo(UPDATED_BORLIST);
        assertThat(testLibraryInf.getAdid()).isEqualTo(UPDATED_ADID);
        assertThat(testLibraryInf.getLibId()).isEqualTo(UPDATED_LIB_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingLibraryInf() throws Exception {
        int databaseSizeBeforeUpdate = libraryInfRepository.findAll().size();

        // Create the LibraryInf

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLibraryInfMockMvc.perform(put("/api/library-infs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libraryInf)))
            .andExpect(status().isBadRequest());

        // Validate the LibraryInf in the database
        List<LibraryInf> libraryInfList = libraryInfRepository.findAll();
        assertThat(libraryInfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLibraryInf() throws Exception {
        // Initialize the database
        libraryInfRepository.saveAndFlush(libraryInf);

        int databaseSizeBeforeDelete = libraryInfRepository.findAll().size();

        // Delete the libraryInf
        restLibraryInfMockMvc.perform(delete("/api/library-infs/{id}", libraryInf.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LibraryInf> libraryInfList = libraryInfRepository.findAll();
        assertThat(libraryInfList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LibraryInf.class);
        LibraryInf libraryInf1 = new LibraryInf();
        libraryInf1.setId(1L);
        LibraryInf libraryInf2 = new LibraryInf();
        libraryInf2.setId(libraryInf1.getId());
        assertThat(libraryInf1).isEqualTo(libraryInf2);
        libraryInf2.setId(2L);
        assertThat(libraryInf1).isNotEqualTo(libraryInf2);
        libraryInf1.setId(null);
        assertThat(libraryInf1).isNotEqualTo(libraryInf2);
    }
}
