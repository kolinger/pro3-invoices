package me.kolinger.pro3.invoices.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@MappedSuperclass
public abstract class DeletableEntity extends IdentifiedEntity implements Serializable {

    @Column(nullable = false)
    private Boolean deleted = false;

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
