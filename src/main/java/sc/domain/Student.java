package sc.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sid")
    private String sid;

    @NotNull
    @Column(name = "sname", nullable = false)
    private String sname;

    @Column(name = "ssex")
    private String ssex;

    @Column(name = "sspecial")
    private String sspecial;

    @Column(name = "sclass")
    private String sclass;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public Student sid(String sid) {
        this.sid = sid;
        return this;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public Student sname(String sname) {
        this.sname = sname;
        return this;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSsex() {
        return ssex;
    }

    public Student ssex(String ssex) {
        this.ssex = ssex;
        return this;
    }

    public void setSsex(String ssex) {
        this.ssex = ssex;
    }

    public String getSspecial() {
        return sspecial;
    }

    public Student sspecial(String sspecial) {
        this.sspecial = sspecial;
        return this;
    }

    public void setSspecial(String sspecial) {
        this.sspecial = sspecial;
    }

    public String getSclass() {
        return sclass;
    }

    public Student sclass(String sclass) {
        this.sclass = sclass;
        return this;
    }

    public void setSclass(String sclass) {
        this.sclass = sclass;
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
        Student student = (Student) o;
        if (student.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), student.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", sid='" + getSid() + "'" +
            ", sname='" + getSname() + "'" +
            ", ssex='" + getSsex() + "'" +
            ", sspecial='" + getSspecial() + "'" +
            ", sclass='" + getSclass() + "'" +
            "}";
    }
}
