package sc.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Card.
 */
@Entity
@Table(name = "card")
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_id")
    private String cardId;

    @Column(name = "cardnum")
    private String cardnum;

    @Column(name = "sname")
    private String sname;

    @Column(name = "ssex")
    private String ssex;

    @Column(name = "cardstyle")
    private String cardstyle;

    @Column(name = "cardmoney")
    private String cardmoney;

    @Column(name = "cardstates")
    private String cardstates;

    @Column(name = "cardtime")
    private Instant cardtime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardId() {
        return cardId;
    }

    public Card cardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardnum() {
        return cardnum;
    }

    public Card cardnum(String cardnum) {
        this.cardnum = cardnum;
        return this;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum;
    }

    public String getSname() {
        return sname;
    }

    public Card sname(String sname) {
        this.sname = sname;
        return this;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSsex() {
        return ssex;
    }

    public Card ssex(String ssex) {
        this.ssex = ssex;
        return this;
    }

    public void setSsex(String ssex) {
        this.ssex = ssex;
    }

    public String getCardstyle() {
        return cardstyle;
    }

    public Card cardstyle(String cardstyle) {
        this.cardstyle = cardstyle;
        return this;
    }

    public void setCardstyle(String cardstyle) {
        this.cardstyle = cardstyle;
    }

    public String getCardmoney() {
        return cardmoney;
    }

    public Card cardmoney(String cardmoney) {
        this.cardmoney = cardmoney;
        return this;
    }

    public void setCardmoney(String cardmoney) {
        this.cardmoney = cardmoney;
    }

    public String getCardstates() {
        return cardstates;
    }

    public Card cardstates(String cardstates) {
        this.cardstates = cardstates;
        return this;
    }

    public void setCardstates(String cardstates) {
        this.cardstates = cardstates;
    }

    public Instant getCardtime() {
        return cardtime;
    }

    public Card cardtime(Instant cardtime) {
        this.cardtime = cardtime;
        return this;
    }

    public void setCardtime(Instant cardtime) {
        this.cardtime = cardtime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Card card = (Card) o;
        if (card.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), card.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Card{" +
            "id=" + getId() +
            ", cardId='" + getCardId() + "'" +
            ", cardnum='" + getCardnum() + "'" +
            ", sname='" + getSname() + "'" +
            ", ssex='" + getSsex() + "'" +
            ", cardstyle='" + getCardstyle() + "'" +
            ", cardmoney='" + getCardmoney() + "'" +
            ", cardstates='" + getCardstates() + "'" +
            ", cardtime='" + getCardtime() + "'" +
            "}";
    }
}
