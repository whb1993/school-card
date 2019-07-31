package sc.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A FillInf.
 */
@Entity
@Table(name = "fill_inf")
public class FillInf implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cardno")
    private String cardno;

    @Column(name = "cardstyle")
    private String cardstyle;

    @Column(name = "fillmoney")
    private String fillmoney;

    @Column(name = "filltime")
    private Instant filltime;

    @Column(name = "ad_id")
    private String adId;

    @Column(name = "fillnum")
    private String fillnum;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardno() {
        return cardno;
    }

    public FillInf cardno(String cardno) {
        this.cardno = cardno;
        return this;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getCardstyle() {
        return cardstyle;
    }

    public FillInf cardstyle(String cardstyle) {
        this.cardstyle = cardstyle;
        return this;
    }

    public void setCardstyle(String cardstyle) {
        this.cardstyle = cardstyle;
    }

    public String getFillmoney() {
        return fillmoney;
    }

    public FillInf fillmoney(String fillmoney) {
        this.fillmoney = fillmoney;
        return this;
    }

    public void setFillmoney(String fillmoney) {
        this.fillmoney = fillmoney;
    }

    public Instant getFilltime() {
        return filltime;
    }

    public FillInf filltime(Instant filltime) {
        this.filltime = filltime;
        return this;
    }

    public void setFilltime(Instant filltime) {
        this.filltime = filltime;
    }

    public String getAdId() {
        return adId;
    }

    public FillInf adId(String adId) {
        this.adId = adId;
        return this;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getFillnum() {
        return fillnum;
    }

    public FillInf fillnum(String fillnum) {
        this.fillnum = fillnum;
        return this;
    }

    public void setFillnum(String fillnum) {
        this.fillnum = fillnum;
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
        FillInf fillInf = (FillInf) o;
        if (fillInf.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fillInf.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FillInf{" +
            "id=" + getId() +
            ", cardno='" + getCardno() + "'" +
            ", cardstyle='" + getCardstyle() + "'" +
            ", fillmoney='" + getFillmoney() + "'" +
            ", filltime='" + getFilltime() + "'" +
            ", adId='" + getAdId() + "'" +
            ", fillnum='" + getFillnum() + "'" +
            "}";
    }
}
