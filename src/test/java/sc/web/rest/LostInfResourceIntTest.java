package sc.web.rest;

import sc.SchoolCardApp;

import sc.domain.LostInf;
import sc.repository.LostInfRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static sc.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LostInfResource REST controller.
 *
 * @see LostInfResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchoolCardApp.class)
public class LostInfResourceIntTest {

    private static final String DEFAULT_LOST_ID = "AAAAAAAAAA";
    private static final String UPDATED_LOST_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_ID = "AAAAAAAAAA";
    private static final String UPDATED_CARD_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_LOSTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOSTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_AD_ID = "AAAAAAAAAA";
    private static final String UPDATED_AD_ID = "BBBBBBBBBB";

    @Autowired
    private LostInfRepository lostInfRepository;

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

    private MockMvc restLostInfMockMvc;

    private LostInf lostInf;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LostInfResource lostInfResource = new LostInfResource(lostInfRepository);
        this.restLostInfMockMvc = MockMvcBuilders.standaloneSetup(lostInfResource)
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
    public static LostInf createEntity(EntityManager em) {
        LostInf lostInf = new LostInf()
            .lostId(DEFAULT_LOST_ID)
            .cardId(DEFAULT_CARD_ID)
            .lostime(DEFAULT_LOSTIME)
            .adId(DEFAULT_AD_ID);
        return lostInf;
    }

    @Before
    public void initTest() {
        lostInf = createEntity(em);
    }

    @Test
    @Transactional
    public void createLostInf() throws Exception {
        int databaseSizeBeforeCreate = lostInfRepository.findAll().size();

        // Create the LostInf
        restLostInfMockMvc.perform(post("/api/lost-infs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lostInf)))
            .andExpect(status().isCreated());

        // Validate the LostInf in the database
        List<LostInf> lostInfList = lostInfRepository.findAll();
        assertThat(lostInfList).hasSize(databaseSizeBeforeCreate + 1);
        LostInf testLostInf = lostInfList.get(lostInfList.size() - 1);
        assertThat(testLostInf.getLostId()).isEqualTo(DEFAULT_LOST_ID);
        assertThat(testLostInf.getCardId()).isEqualTo(DEFAULT_CARD_ID);
        assertThat(testLostInf.getLostime()).isEqualTo(DEFAULT_LOSTIME);
        assertThat(testLostInf.getAdId()).isEqualTo(DEFAULT_AD_ID);
    }

    @Test
    @Transactional
    public void createLostInfWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lostInfRepository.findAll().size();

        // Create the LostInf with an existing ID
        lostInf.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLostInfMockMvc.perform(post("/api/lost-infs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lostInf)))
            .andExpect(status().isBadRequest());

        // Validate the LostInf in the database
        List<LostInf> lostInfList = lostInfRepository.findAll();
        assertThat(lostInfList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLostInfs() throws Exception {
        // Initialize the database
        lostInfRepository.saveAndFlush(lostInf);

        // Get all the lostInfList
        restLostInfMockMvc.perform(get("/api/lost-infs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lostInf.getId().intValue())))
            .andExpect(jsonPath("$.[*].lostId").value(hasItem(DEFAULT_LOST_ID.toString())))
            .andExpect(jsonPath("$.[*].cardId").value(hasItem(DEFAULT_CARD_ID.toString())))
            .andExpect(jsonPath("$.[*].lostime").value(hasItem(DEFAULT_LOSTIME.toString())))
            .andExpect(jsonPath("$.[*].adId").value(hasItem(DEFAULT_AD_ID.toString())));
    }
    
    @Test
    @Transactional
    public void getLostInf() throws Exception {
        // Initialize the database
        lostInfRepository.saveAndFlush(lostInf);

        // Get the lostInf
        restLostInfMockMvc.perform(get("/api/lost-infs/{id}", lostInf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lostInf.getId().intValue()))
            .andExpect(jsonPath("$.lostId").value(DEFAULT_LOST_ID.toString()))
            .andExpect(jsonPath("$.cardId").value(DEFAULT_CARD_ID.toString()))
            .andExpect(jsonPath("$.lostime").value(DEFAULT_LOSTIME.toString()))
            .andExpect(jsonPath("$.adId").value(DEFAULT_AD_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLostInf() throws Exception {
        // Get the lostInf
        restLostInfMockMvc.perform(get("/api/lost-infs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLostInf() throws Exception {
        // Initialize the database
        lostInfRepository.saveAndFlush(lostInf);

        int databaseSizeBeforeUpdate = lostInfRepository.findAll().size();

        // Update the lostInf
        LostInf updatedLostInf = lostInfRepository.findById(lostInf.getId()).get();
        // Disconnect from session so that the updates on updatedLostInf are not directly saved in db
        em.detach(updatedLostInf);
        updatedLostInf
            .lostId(UPDATED_LOST_ID)
            .cardId(UPDATED_CARD_ID)
            .lostime(UPDATED_LOSTIME)
            .adId(UPDATED_AD_ID);

        restLostInfMockMvc.perform(put("/api/lost-infs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLostInf)))
            .andExpect(status().isOk());

        // Validate the LostInf in the database
        List<LostInf> lostInfList = lostInfRepository.findAll();
        assertThat(lostInfList).hasSize(databaseSizeBeforeUpdate);
        LostInf testLostInf = lostInfList.get(lostInfList.size() - 1);
        assertThat(testLostInf.getLostId()).isEqualTo(UPDATED_LOST_ID);
        assertThat(testLostInf.getCardId()).isEqualTo(UPDATED_CARD_ID);
        assertThat(testLostInf.getLostime()).isEqualTo(UPDATED_LOSTIME);
        assertThat(testLostInf.getAdId()).isEqualTo(UPDATED_AD_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingLostInf() throws Exception {
        int databaseSizeBeforeUpdate = lostInfRepository.findAll().size();

        // Create the LostInf

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLostInfMockMvc.perform(put("/api/lost-infs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lostInf)))
            .andExpect(status().isBadRequest());

        // Validate the LostInf in the database
        List<LostInf> lostInfList = lostInfRepository.findAll();
        assertThat(lostInfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLostInf() throws Exception {
        // Initialize the database
        lostInfRepository.saveAndFlush(lostInf);

        int databaseSizeBeforeDelete = lostInfRepository.findAll().size();

        // Delete the lostInf
        restLostInfMockMvc.perform(delete("/api/lost-infs/{id}", lostInf.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LostInf> lostInfList = lostInfRepository.findAll();
        assertThat(lostInfList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LostInf.class);
        LostInf lostInf1 = new LostInf();
        lostInf1.setId(1L);
        LostInf lostInf2 = new LostInf();
        lostInf2.setId(lostInf1.getId());
        assertThat(lostInf1).isEqualTo(lostInf2);
        lostInf2.setId(2L);
        assertThat(lostInf1).isNotEqualTo(lostInf2);
        lostInf1.setId(null);
        assertThat(lostInf1).isNotEqualTo(lostInf2);
    }
}
