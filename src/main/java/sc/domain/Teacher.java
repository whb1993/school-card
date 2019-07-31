package sc.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Teacher.
 */
@Entity
@Table(name = "teacher")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tid")
    private String tid;

    @Column(name = "tname")
    private String tname;

    @Column(name = "tsex")
    private String tsex;

    @Column(name = "tspecial")
    private String tspecial;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTid() {
        return tid;
    }

    public Teacher tid(String tid) {
        this.tid = tid;
        return this;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public Teacher tname(String tname) {
        this.tname = tname;
        return this;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTsex() {
        return tsex;
    }

    public Teacher tsex(String tsex) {
        this.tsex = tsex;
        return this;
    }

    public void setTsex(String tsex) {
        this.tsex = tsex;
    }

    public String getTspecial() {
        return tspecial;
    }

    public Teacher tspecial(String tspecial) {
        this.tspecial = tspecial;
        return this;
    }

    public void setTspecial(String tspecial) {
        this.tspecial = tspecial;
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
        Teacher teacher = (Teacher) o;
        if (teacher.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teacher.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Teacher{" +
            "id=" + getId() +
            ", tid='" + getTid() + "'" +
            ", tname='" + getTname() + "'" +
            ", tsex='" + getTsex() + "'" +
            ", tspecial='" + getTspecial() + "'" +
            "}";
    }
}
