package sc.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A LibraryInf.
 */
@Entity
@Table(name = "library_inf")
public class LibraryInf implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_id")
    private String bookId;

    @Column(name = "bookno")
    private String bookno;

    @Column(name = "cardstates")
    private String cardstates;

    @Column(name = "borlist")
    private String borlist;

    @Column(name = "adid")
    private String adid;

    @Column(name = "lib_id")
    private String libId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public LibraryInf bookId(String bookId) {
        this.bookId = bookId;
        return this;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookno() {
        return bookno;
    }

    public LibraryInf bookno(String bookno) {
        this.bookno = bookno;
        return this;
    }

    public void setBookno(String bookno) {
        this.bookno = bookno;
    }

    public String getCardstates() {
        return cardstates;
    }

    public LibraryInf cardstates(String cardstates) {
        this.cardstates = cardstates;
        return this;
    }

    public void setCardstates(String cardstates) {
        this.cardstates = cardstates;
    }

    public String getBorlist() {
        return borlist;
    }

    public LibraryInf borlist(String borlist) {
        this.borlist = borlist;
        return this;
    }

    public void setBorlist(String borlist) {
        this.borlist = borlist;
    }

    public String getAdid() {
        return adid;
    }

    public LibraryInf adid(String adid) {
        this.adid = adid;
        return this;
    }

    public void setAdid(String adid) {
        this.adid = adid;
    }

    public String getLibId() {
        return libId;
    }

    public LibraryInf libId(String libId) {
        this.libId = libId;
        return this;
    }

    public void setLibId(String libId) {
        this.libId = libId;
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
        LibraryInf libraryInf = (LibraryInf) o;
        if (libraryInf.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), libraryInf.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LibraryInf{" +
            "id=" + getId() +
            ", bookId='" + getBookId() + "'" +
            ", bookno='" + getBookno() + "'" +
            ", cardstates='" + getCardstates() + "'" +
            ", borlist='" + getBorlist() + "'" +
            ", adid='" + getAdid() + "'" +
            ", libId='" + getLibId() + "'" +
            "}";
    }
}
