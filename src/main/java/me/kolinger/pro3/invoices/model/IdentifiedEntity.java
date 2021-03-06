package me.kolinger.pro3.invoices.model;

import me.kolinger.pro3.invoices.common.LoggedObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@MappedSuperclass
public abstract class IdentifiedEntity extends LoggedObject implements Serializable {

    @Id
    @Column(columnDefinition = "bigserial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !getClass().isAssignableFrom(o.getClass())) return false;

        IdentifiedEntity that = (IdentifiedEntity) o;

        if (that.getId() == null) return false;

        return that.getId().compareTo(getId()) == 0;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
