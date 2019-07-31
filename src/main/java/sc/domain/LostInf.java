package sc.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A LostInf.
 */
@Entity
@Table(name = "lost_inf")
public class LostInf implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lost_id")
    private String lostId;

    @Column(name = "card_id")
    private String cardId;

    @Column(name = "lostime")
    private Instant lostime;

    @Column(name = "ad_id")
    private String adId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLostId() {
        return lostId;
    }

    public LostInf lostId(String lostId) {
        this.lostId = lostId;
        return this;
    }

    public void setLostId(String lostId) {
        this.lostId = lostId;
    }

    public String getCardId() {
        return cardId;
    }

    public LostInf cardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Instant getLostime() {
        return lostime;
    }

    public LostInf lostime(Instant lostime) {
        this.lostime = lostime;
        return this;
    }

    public void setLostime(Instant lostime) {
        this.lostime = lostime;
    }

    public String getAdId() {
        return adId;
    }

    public LostInf adId(String adId) {
        this.adId = adId;
        return this;
    }

    public void setAdId(String adId) {
        this.adId = adId;
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
        LostInf lostInf = (LostInf) o;
        if (lostInf.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lostInf.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LostInf{" +
            "id=" + getId() +
            ", lostId='" + getLostId() + "'" +
            ", cardId='" + getCardId() + "'" +
            ", lostime='" + getLostime() + "'" +
            ", adId='" + getAdId() + "'" +
            "}";
    }
}
