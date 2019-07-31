package sc.web.rest;

import sc.SchoolCardApp;

import sc.domain.Card;
import sc.repository.CardRepository;
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
 * Test class for the CardResource REST controller.
 *
 * @see CardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchoolCardApp.class)
public class CardResourceIntTest {

    private static final String DEFAULT_CARD_ID = "AAAAAAAAAA";
    private static final String UPDATED_CARD_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CARDNUM = "AAAAAAAAAA";
    private static final String UPDATED_CARDNUM = "BBBBBBBBBB";

    private static final String DEFAULT_SNAME = "AAAAAAAAAA";
    private static final String UPDATED_SNAME = "BBBBBBBBBB";

    private static final String DEFAULT_SSEX = "AAAAAAAAAA";
    private static final String UPDATED_SSEX = "BBBBBBBBBB";

    private static final String DEFAULT_CARDSTYLE = "AAAAAAAAAA";
    private static final String UPDATED_CARDSTYLE = "BBBBBBBBBB";

    private static final String DEFAULT_CARDMONEY = "AAAAAAAAAA";
    private static final String UPDATED_CARDMONEY = "BBBBBBBBBB";

    private static final String DEFAULT_CARDSTATES = "AAAAAAAAAA";
    private static final String UPDATED_CARDSTATES = "BBBBBBBBBB";

    private static final Instant DEFAULT_CARDTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CARDTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CardRepository cardRepository;

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

    private MockMvc restCardMockMvc;

    private Card card;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CardResource cardResource = new CardResource(cardRepository);
        this.restCardMockMvc = MockMvcBuilders.standaloneSetup(cardResource)
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
    public static Card createEntity(EntityManager em) {
        Card card = new Card()
            .cardId(DEFAULT_CARD_ID)
            .cardnum(DEFAULT_CARDNUM)
            .sname(DEFAULT_SNAME)
            .ssex(DEFAULT_SSEX)
            .cardstyle(DEFAULT_CARDSTYLE)
            .cardmoney(DEFAULT_CARDMONEY)
            .cardstates(DEFAULT_CARDSTATES)
            .cardtime(DEFAULT_CARDTIME);
        return card;
    }

    @Before
    public void initTest() {
        card = createEntity(em);
    }

    @Test
    @Transactional
    public void createCard() throws Exception {
        int databaseSizeBeforeCreate = cardRepository.findAll().size();

        // Create the Card
        restCardMockMvc.perform(post("/api/cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(card)))
            .andExpect(status().isCreated());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeCreate + 1);
        Card testCard = cardList.get(cardList.size() - 1);
        assertThat(testCard.getCardId()).isEqualTo(DEFAULT_CARD_ID);
        assertThat(testCard.getCardnum()).isEqualTo(DEFAULT_CARDNUM);
        assertThat(testCard.getSname()).isEqualTo(DEFAULT_SNAME);
        assertThat(testCard.getSsex()).isEqualTo(DEFAULT_SSEX);
        assertThat(testCard.getCardstyle()).isEqualTo(DEFAULT_CARDSTYLE);
        assertThat(testCard.getCardmoney()).isEqualTo(DEFAULT_CARDMONEY);
        assertThat(testCard.getCardstates()).isEqualTo(DEFAULT_CARDSTATES);
        assertThat(testCard.getCardtime()).isEqualTo(DEFAULT_CARDTIME);
    }

    @Test
    @Transactional
    public void createCardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cardRepository.findAll().size();

        // Create the Card with an existing ID
        card.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCardMockMvc.perform(post("/api/cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(card)))
            .andExpect(status().isBadRequest());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCards() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList
        restCardMockMvc.perform(get("/api/cards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(card.getId().intValue())))
            .andExpect(jsonPath("$.[*].cardId").value(hasItem(DEFAULT_CARD_ID.toString())))
            .andExpect(jsonPath("$.[*].cardnum").value(hasItem(DEFAULT_CARDNUM.toString())))
            .andExpect(jsonPath("$.[*].sname").value(hasItem(DEFAULT_SNAME.toString())))
            .andExpect(jsonPath("$.[*].ssex").value(hasItem(DEFAULT_SSEX.toString())))
            .andExpect(jsonPath("$.[*].cardstyle").value(hasItem(DEFAULT_CARDSTYLE.toString())))
            .andExpect(jsonPath("$.[*].cardmoney").value(hasItem(DEFAULT_CARDMONEY.toString())))
            .andExpect(jsonPath("$.[*].cardstates").value(hasItem(DEFAULT_CARDSTATES.toString())))
            .andExpect(jsonPath("$.[*].cardtime").value(hasItem(DEFAULT_CARDTIME.toString())));
    }
    
    @Test
    @Transactional
    public void getCard() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get the card
        restCardMockMvc.perform(get("/api/cards/{id}", card.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(card.getId().intValue()))
            .andExpect(jsonPath("$.cardId").value(DEFAULT_CARD_ID.toString()))
            .andExpect(jsonPath("$.cardnum").value(DEFAULT_CARDNUM.toString()))
            .andExpect(jsonPath("$.sname").value(DEFAULT_SNAME.toString()))
            .andExpect(jsonPath("$.ssex").value(DEFAULT_SSEX.toString()))
            .andExpect(jsonPath("$.cardstyle").value(DEFAULT_CARDSTYLE.toString()))
            .andExpect(jsonPath("$.cardmoney").value(DEFAULT_CARDMONEY.toString()))
            .andExpect(jsonPath("$.cardstates").value(DEFAULT_CARDSTATES.toString()))
            .andExpect(jsonPath("$.cardtime").value(DEFAULT_CARDTIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCard() throws Exception {
        // Get the card
        restCardMockMvc.perform(get("/api/cards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCard() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        int databaseSizeBeforeUpdate = cardRepository.findAll().size();

        // Update the card
        Card updatedCard = cardRepository.findById(card.getId()).get();
        // Disconnect from session so that the updates on updatedCard are not directly saved in db
        em.detach(updatedCard);
        updatedCard
            .cardId(UPDATED_CARD_ID)
            .cardnum(UPDATED_CARDNUM)
            .sname(UPDATED_SNAME)
            .ssex(UPDATED_SSEX)
            .cardstyle(UPDATED_CARDSTYLE)
            .cardmoney(UPDATED_CARDMONEY)
            .cardstates(UPDATED_CARDSTATES)
            .cardtime(UPDATED_CARDTIME);

        restCardMockMvc.perform(put("/api/cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCard)))
            .andExpect(status().isOk());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeUpdate);
        Card testCard = cardList.get(cardList.size() - 1);
        assertThat(testCard.getCardId()).isEqualTo(UPDATED_CARD_ID);
        assertThat(testCard.getCardnum()).isEqualTo(UPDATED_CARDNUM);
        assertThat(testCard.getSname()).isEqualTo(UPDATED_SNAME);
        assertThat(testCard.getSsex()).isEqualTo(UPDATED_SSEX);
        assertThat(testCard.getCardstyle()).isEqualTo(UPDATED_CARDSTYLE);
        assertThat(testCard.getCardmoney()).isEqualTo(UPDATED_CARDMONEY);
        assertThat(testCard.getCardstates()).isEqualTo(UPDATED_CARDSTATES);
        assertThat(testCard.getCardtime()).isEqualTo(UPDATED_CARDTIME);
    }

    @Test
    @Transactional
    public void updateNonExistingCard() throws Exception {
        int databaseSizeBeforeUpdate = cardRepository.findAll().size();

        // Create the Card

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCardMockMvc.perform(put("/api/cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(card)))
            .andExpect(status().isBadRequest());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCard() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        int databaseSizeBeforeDelete = cardRepository.findAll().size();

        // Delete the card
        restCardMockMvc.perform(delete("/api/cards/{id}", card.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Card.class);
        Card card1 = new Card();
        card1.setId(1L);
        Card card2 = new Card();
        card2.setId(card1.getId());
        assertThat(card1).isEqualTo(card2);
        card2.setId(2L);
        assertThat(card1).isNotEqualTo(card2);
        card1.setId(null);
        assertThat(card1).isNotEqualTo(card2);
    }
}
