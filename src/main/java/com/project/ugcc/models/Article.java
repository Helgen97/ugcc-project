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
@Table(name = "Articles")
@DiscriminatorValue(value = "Article")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Article extends Type {

    @Column(length = 300)
    private String imageURL;

    @Column(length = 120)
    private String imageDescription;

    @Column(length = 3000)
    private String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Article article = (Article) o;
        return getID() != null && Objects.equals(getID(), article.getID());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
