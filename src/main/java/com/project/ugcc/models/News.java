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
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "News")
@DiscriminatorValue(value = "News")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class News extends Type {

    @Column(length = 500)
    private String description;

    @Column(length = 300)
    private String imageURL;

    @Column(length = 5000)
    private String text;

    @Column
    private LocalDateTime date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        News news = (News) o;
        return getID() != null && Objects.equals(getID(), news.getID());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
