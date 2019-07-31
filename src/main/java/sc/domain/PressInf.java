package sc.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A PressInf.
 */
@Entity
@Table(name = "press_inf")
public class PressInf implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "press_id")
    private String pressId;

    @Column(name = "pplace")
    private String pplace;

    @Column(name = "card_id")
    private String cardId;

    @Column(name = "pmoney")
    private String pmoney;

    @Column(name = "ptime")
    private Instant ptime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPressId() {
        return pressId;
    }

    public PressInf pressId(String pressId) {
        this.pressId = pressId;
        return this;
    }

    public void setPressId(String pressId) {
        this.pressId = pressId;
    }

    public String getPplace() {
        return pplace;
    }

    public PressInf pplace(String pplace) {
        this.pplace = pplace;
        return this;
    }

    public void setPplace(String pplace) {
        this.pplace = pplace;
    }

    public String getCardId() {
        return cardId;
    }

    public PressInf cardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getPmoney() {
        return pmoney;
    }

    public PressInf pmoney(String pmoney) {
        this.pmoney = pmoney;
        return this;
    }

    public void setPmoney(String pmoney) {
        this.pmoney = pmoney;
    }

    public Instant getPtime() {
        return ptime;
    }

    public PressInf ptime(Instant ptime) {
        this.ptime = ptime;
        return this;
    }

    public void setPtime(Instant ptime) {
        this.ptime = ptime;
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
        PressInf pressInf = (PressInf) o;
        if (pressInf.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pressInf.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PressInf{" +
            "id=" + getId() +
            ", pressId='" + getPressId() + "'" +
            ", pplace='" + getPplace() + "'" +
            ", cardId='" + getCardId() + "'" +
            ", pmoney='" + getPmoney() + "'" +
            ", ptime='" + getPtime() + "'" +
            "}";
    }
}
