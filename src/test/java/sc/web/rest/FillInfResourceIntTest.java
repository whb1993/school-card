package sc.web.rest;

import sc.SchoolCardApp;

import sc.domain.FillInf;
import sc.repository.FillInfRepository;
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
 * Test class for the FillInfResource REST controller.
 *
 * @see FillInfResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchoolCardApp.class)
public class FillInfResourceIntTest {

    private static final String DEFAULT_CARDNO = "AAAAAAAAAA";
    private static final String UPDATED_CARDNO = "BBBBBBBBBB";

    private static final String DEFAULT_CARDSTYLE = "AAAAAAAAAA";
    private static final String UPDATED_CARDSTYLE = "BBBBBBBBBB";

    private static final String DEFAULT_FILLMONEY = "AAAAAAAAAA";
    private static final String UPDATED_FILLMONEY = "BBBBBBBBBB";

    private static final Instant DEFAULT_FILLTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FILLTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_AD_ID = "AAAAAAAAAA";
    private static final String UPDATED_AD_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FILLNUM = "AAAAAAAAAA";
    private static final String UPDATED_FILLNUM = "BBBBBBBBBB";

    @Autowired
    private FillInfRepository fillInfRepository;

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

    private MockMvc restFillInfMockMvc;

    private FillInf fillInf;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FillInfResource fillInfResource = new FillInfResource(fillInfRepository);
        this.restFillInfMockMvc = MockMvcBuilders.standaloneSetup(fillInfResource)
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
    public static FillInf createEntity(EntityManager em) {
        FillInf fillInf = new FillInf()
            .cardno(DEFAULT_CARDNO)
            .cardstyle(DEFAULT_CARDSTYLE)
            .fillmoney(DEFAULT_FILLMONEY)
            .filltime(DEFAULT_FILLTIME)
            .adId(DEFAULT_AD_ID)
            .fillnum(DEFAULT_FILLNUM);
        return fillInf;
    }

    @Before
    public void initTest() {
        fillInf = createEntity(em);
    }

    @Test
    @Transactional
    public void createFillInf() throws Exception {
        int databaseSizeBeforeCreate = fillInfRepository.findAll().size();

        // Create the FillInf
        restFillInfMockMvc.perform(post("/api/fill-infs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fillInf)))
            .andExpect(status().isCreated());

        // Validate the FillInf in the database
        List<FillInf> fillInfList = fillInfRepository.findAll();
        assertThat(fillInfList).hasSize(databaseSizeBeforeCreate + 1);
        FillInf testFillInf = fillInfList.get(fillInfList.size() - 1);
        assertThat(testFillInf.getCardno()).isEqualTo(DEFAULT_CARDNO);
        assertThat(testFillInf.getCardstyle()).isEqualTo(DEFAULT_CARDSTYLE);
        assertThat(testFillInf.getFillmoney()).isEqualTo(DEFAULT_FILLMONEY);
        assertThat(testFillInf.getFilltime()).isEqualTo(DEFAULT_FILLTIME);
        assertThat(testFillInf.getAdId()).isEqualTo(DEFAULT_AD_ID);
        assertThat(testFillInf.getFillnum()).isEqualTo(DEFAULT_FILLNUM);
    }

    @Test
    @Transactional
    public void createFillInfWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fillInfRepository.findAll().size();

        // Create the FillInf with an existing ID
        fillInf.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFillInfMockMvc.perform(post("/api/fill-infs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fillInf)))
            .andExpect(status().isBadRequest());

        // Validate the FillInf in the database
        List<FillInf> fillInfList = fillInfRepository.findAll();
        assertThat(fillInfList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFillInfs() throws Exception {
        // Initialize the database
        fillInfRepository.saveAndFlush(fillInf);

        // Get all the fillInfList
        restFillInfMockMvc.perform(get("/api/fill-infs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fillInf.getId().intValue())))
            .andExpect(jsonPath("$.[*].cardno").value(hasItem(DEFAULT_CARDNO.toString())))
            .andExpect(jsonPath("$.[*].cardstyle").value(hasItem(DEFAULT_CARDSTYLE.toString())))
            .andExpect(jsonPath("$.[*].fillmoney").value(hasItem(DEFAULT_FILLMONEY.toString())))
            .andExpect(jsonPath("$.[*].filltime").value(hasItem(DEFAULT_FILLTIME.toString())))
            .andExpect(jsonPath("$.[*].adId").value(hasItem(DEFAULT_AD_ID.toString())))
            .andExpect(jsonPath("$.[*].fillnum").value(hasItem(DEFAULT_FILLNUM.toString())));
    }
    
    @Test
    @Transactional
    public void getFillInf() throws Exception {
        // Initialize the database
        fillInfRepository.saveAndFlush(fillInf);

        // Get the fillInf
        restFillInfMockMvc.perform(get("/api/fill-infs/{id}", fillInf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fillInf.getId().intValue()))
            .andExpect(jsonPath("$.cardno").value(DEFAULT_CARDNO.toString()))
            .andExpect(jsonPath("$.cardstyle").value(DEFAULT_CARDSTYLE.toString()))
            .andExpect(jsonPath("$.fillmoney").value(DEFAULT_FILLMONEY.toString()))
            .andExpect(jsonPath("$.filltime").value(DEFAULT_FILLTIME.toString()))
            .andExpect(jsonPath("$.adId").value(DEFAULT_AD_ID.toString()))
            .andExpect(jsonPath("$.fillnum").value(DEFAULT_FILLNUM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFillInf() throws Exception {
        // Get the fillInf
        restFillInfMockMvc.perform(get("/api/fill-infs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFillInf() throws Exception {
        // Initialize the database
        fillInfRepository.saveAndFlush(fillInf);

        int databaseSizeBeforeUpdate = fillInfRepository.findAll().size();

        // Update the fillInf
        FillInf updatedFillInf = fillInfRepository.findById(fillInf.getId()).get();
        // Disconnect from session so that the updates on updatedFillInf are not directly saved in db
        em.detach(updatedFillInf);
        updatedFillInf
            .cardno(UPDATED_CARDNO)
            .cardstyle(UPDATED_CARDSTYLE)
            .fillmoney(UPDATED_FILLMONEY)
            .filltime(UPDATED_FILLTIME)
            .adId(UPDATED_AD_ID)
            .fillnum(UPDATED_FILLNUM);

        restFillInfMockMvc.perform(put("/api/fill-infs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFillInf)))
            .andExpect(status().isOk());

        // Validate the FillInf in the database
        List<FillInf> fillInfList = fillInfRepository.findAll();
        assertThat(fillInfList).hasSize(databaseSizeBeforeUpdate);
        FillInf testFillInf = fillInfList.get(fillInfList.size() - 1);
        assertThat(testFillInf.getCardno()).isEqualTo(UPDATED_CARDNO);
        assertThat(testFillInf.getCardstyle()).isEqualTo(UPDATED_CARDSTYLE);
        assertThat(testFillInf.getFillmoney()).isEqualTo(UPDATED_FILLMONEY);
        assertThat(testFillInf.getFilltime()).isEqualTo(UPDATED_FILLTIME);
        assertThat(testFillInf.getAdId()).isEqualTo(UPDATED_AD_ID);
        assertThat(testFillInf.getFillnum()).isEqualTo(UPDATED_FILLNUM);
    }

    @Test
    @Transactional
    public void updateNonExistingFillInf() throws Exception {
        int databaseSizeBeforeUpdate = fillInfRepository.findAll().size();

        // Create the FillInf

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFillInfMockMvc.perform(put("/api/fill-infs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fillInf)))
            .andExpect(status().isBadRequest());

        // Validate the FillInf in the database
        List<FillInf> fillInfList = fillInfRepository.findAll();
        assertThat(fillInfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFillInf() throws Exception {
        // Initialize the database
        fillInfRepository.saveAndFlush(fillInf);

        int databaseSizeBeforeDelete = fillInfRepository.findAll().size();

        // Delete the fillInf
        restFillInfMockMvc.perform(delete("/api/fill-infs/{id}", fillInf.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FillInf> fillInfList = fillInfRepository.findAll();
        assertThat(fillInfList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FillInf.class);
        FillInf fillInf1 = new FillInf();
        fillInf1.setId(1L);
        FillInf fillInf2 = new FillInf();
        fillInf2.setId(fillInf1.getId());
        assertThat(fillInf1).isEqualTo(fillInf2);
        fillInf2.setId(2L);
        assertThat(fillInf1).isNotEqualTo(fillInf2);
        fillInf1.setId(null);
        assertThat(fillInf1).isNotEqualTo(fillInf2);
    }
}
