package sc.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Admin.
 */
@Entity
@Table(name = "admin")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "adid")
    private String adid;

    @Column(name = "adname")
    private String adname;

    @Column(name = "adsex")
    private String adsex;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdid() {
        return adid;
    }

    public Admin adid(String adid) {
        this.adid = adid;
        return this;
    }

    public void setAdid(String adid) {
        this.adid = adid;
    }

    public String getAdname() {
        return adname;
    }

    public Admin adname(String adname) {
        this.adname = adname;
        return this;
    }

    public void setAdname(String adname) {
        this.adname = adname;
    }

    public String getAdsex() {
        return adsex;
    }

    public Admin adsex(String adsex) {
        this.adsex = adsex;
        return this;
    }

    public void setAdsex(String adsex) {
        this.adsex = adsex;
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
        Admin admin = (Admin) o;
        if (admin.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), admin.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Admin{" +
            "id=" + getId() +
            ", adid='" + getAdid() + "'" +
            ", adname='" + getAdname() + "'" +
            ", adsex='" + getAdsex() + "'" +
            "}";
    }
}
