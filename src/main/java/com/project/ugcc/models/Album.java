package com.project.ugcc.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Albums")
@DiscriminatorValue(value = "Album")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Album extends Type {

    private LocalDateTime creationDate;

    @ElementCollection
    private List<String> imagesUrls;

    private String videoUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Album album = (Album) o;
        return getID() != null && Objects.equals(getID(), album.getID());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
