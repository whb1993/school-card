package sc.web.rest;

import sc.SchoolCardApp;

import sc.domain.PressInf;
import sc.repository.PressInfRepository;
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
 * Test class for the PressInfResource REST controller.
 *
 * @see PressInfResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchoolCardApp.class)
public class PressInfResourceIntTest {

    private static final String DEFAULT_PRESS_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRESS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PPLACE = "AAAAAAAAAA";
    private static final String UPDATED_PPLACE = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_ID = "AAAAAAAAAA";
    private static final String UPDATED_CARD_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PMONEY = "AAAAAAAAAA";
    private static final String UPDATED_PMONEY = "BBBBBBBBBB";

    private static final Instant DEFAULT_PTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PressInfRepository pressInfRepository;

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

    private MockMvc restPressInfMockMvc;

    private PressInf pressInf;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PressInfResource pressInfResource = new PressInfResource(pressInfRepository);
        this.restPressInfMockMvc = MockMvcBuilders.standaloneSetup(pressInfResource)
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
    public static PressInf createEntity(EntityManager em) {
        PressInf pressInf = new PressInf()
            .pressId(DEFAULT_PRESS_ID)
            .pplace(DEFAULT_PPLACE)
            .cardId(DEFAULT_CARD_ID)
            .pmoney(DEFAULT_PMONEY)
            .ptime(DEFAULT_PTIME);
        return pressInf;
    }

    @Before
    public void initTest() {
        pressInf = createEntity(em);
    }

    @Test
    @Transactional
    public void createPressInf() throws Exception {
        int databaseSizeBeforeCreate = pressInfRepository.findAll().size();

        // Create the PressInf
        restPressInfMockMvc.perform(post("/api/press-infs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pressInf)))
            .andExpect(status().isCreated());

        // Validate the PressInf in the database
        List<PressInf> pressInfList = pressInfRepository.findAll();
        assertThat(pressInfList).hasSize(databaseSizeBeforeCreate + 1);
        PressInf testPressInf = pressInfList.get(pressInfList.size() - 1);
        assertThat(testPressInf.getPressId()).isEqualTo(DEFAULT_PRESS_ID);
        assertThat(testPressInf.getPplace()).isEqualTo(DEFAULT_PPLACE);
        assertThat(testPressInf.getCardId()).isEqualTo(DEFAULT_CARD_ID);
        assertThat(testPressInf.getPmoney()).isEqualTo(DEFAULT_PMONEY);
        assertThat(testPressInf.getPtime()).isEqualTo(DEFAULT_PTIME);
    }

    @Test
    @Transactional
    public void createPressInfWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pressInfRepository.findAll().size();

        // Create the PressInf with an existing ID
        pressInf.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPressInfMockMvc.perform(post("/api/press-infs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pressInf)))
            .andExpect(status().isBadRequest());

        // Validate the PressInf in the database
        List<PressInf> pressInfList = pressInfRepository.findAll();
        assertThat(pressInfList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPressInfs() throws Exception {
        // Initialize the database
        pressInfRepository.saveAndFlush(pressInf);

        // Get all the pressInfList
        restPressInfMockMvc.perform(get("/api/press-infs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pressInf.getId().intValue())))
            .andExpect(jsonPath("$.[*].pressId").value(hasItem(DEFAULT_PRESS_ID.toString())))
            .andExpect(jsonPath("$.[*].pplace").value(hasItem(DEFAULT_PPLACE.toString())))
            .andExpect(jsonPath("$.[*].cardId").value(hasItem(DEFAULT_CARD_ID.toString())))
            .andExpect(jsonPath("$.[*].pmoney").value(hasItem(DEFAULT_PMONEY.toString())))
            .andExpect(jsonPath("$.[*].ptime").value(hasItem(DEFAULT_PTIME.toString())));
    }
    
    @Test
    @Transactional
    public void getPressInf() throws Exception {
        // Initialize the database
        pressInfRepository.saveAndFlush(pressInf);

        // Get the pressInf
        restPressInfMockMvc.perform(get("/api/press-infs/{id}", pressInf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pressInf.getId().intValue()))
            .andExpect(jsonPath("$.pressId").value(DEFAULT_PRESS_ID.toString()))
            .andExpect(jsonPath("$.pplace").value(DEFAULT_PPLACE.toString()))
            .andExpect(jsonPath("$.cardId").value(DEFAULT_CARD_ID.toString()))
            .andExpect(jsonPath("$.pmoney").value(DEFAULT_PMONEY.toString()))
            .andExpect(jsonPath("$.ptime").value(DEFAULT_PTIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPressInf() throws Exception {
        // Get the pressInf
        restPressInfMockMvc.perform(get("/api/press-infs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePressInf() throws Exception {
        // Initialize the database
        pressInfRepository.saveAndFlush(pressInf);

        int databaseSizeBeforeUpdate = pressInfRepository.findAll().size();

        // Update the pressInf
        PressInf updatedPressInf = pressInfRepository.findById(pressInf.getId()).get();
        // Disconnect from session so that the updates on updatedPressInf are not directly saved in db
        em.detach(updatedPressInf);
        updatedPressInf
            .pressId(UPDATED_PRESS_ID)
            .pplace(UPDATED_PPLACE)
            .cardId(UPDATED_CARD_ID)
            .pmoney(UPDATED_PMONEY)
            .ptime(UPDATED_PTIME);

        restPressInfMockMvc.perform(put("/api/press-infs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPressInf)))
            .andExpect(status().isOk());

        // Validate the PressInf in the database
        List<PressInf> pressInfList = pressInfRepository.findAll();
        assertThat(pressInfList).hasSize(databaseSizeBeforeUpdate);
        PressInf testPressInf = pressInfList.get(pressInfList.size() - 1);
        assertThat(testPressInf.getPressId()).isEqualTo(UPDATED_PRESS_ID);
        assertThat(testPressInf.getPplace()).isEqualTo(UPDATED_PPLACE);
        assertThat(testPressInf.getCardId()).isEqualTo(UPDATED_CARD_ID);
        assertThat(testPressInf.getPmoney()).isEqualTo(UPDATED_PMONEY);
        assertThat(testPressInf.getPtime()).isEqualTo(UPDATED_PTIME);
    }

    @Test
    @Transactional
    public void updateNonExistingPressInf() throws Exception {
        int databaseSizeBeforeUpdate = pressInfRepository.findAll().size();

        // Create the PressInf

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPressInfMockMvc.perform(put("/api/press-infs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pressInf)))
            .andExpect(status().isBadRequest());

        // Validate the PressInf in the database
        List<PressInf> pressInfList = pressInfRepository.findAll();
        assertThat(pressInfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePressInf() throws Exception {
        // Initialize the database
        pressInfRepository.saveAndFlush(pressInf);

        int databaseSizeBeforeDelete = pressInfRepository.findAll().size();

        // Delete the pressInf
        restPressInfMockMvc.perform(delete("/api/press-infs/{id}", pressInf.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PressInf> pressInfList = pressInfRepository.findAll();
        assertThat(pressInfList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PressInf.class);
        PressInf pressInf1 = new PressInf();
        pressInf1.setId(1L);
        PressInf pressInf2 = new PressInf();
        pressInf2.setId(pressInf1.getId());
        assertThat(pressInf1).isEqualTo(pressInf2);
        pressInf2.setId(2L);
        assertThat(pressInf1).isNotEqualTo(pressInf2);
        pressInf1.setId(null);
        assertThat(pressInf1).isNotEqualTo(pressInf2);
    }
}
