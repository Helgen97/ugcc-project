package com.project.ugcc.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "Documents")
@DiscriminatorValue(value = "Document")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Document extends Type {

    @Column(length = 500)
    private String description;

    @Column(length = 300)
    private String imageUrl;

    @Column(length = 300)
    private String documentUrl;

    private String creationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Document document = (Document) o;
        return getId() != null && Objects.equals(getId(), document.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
