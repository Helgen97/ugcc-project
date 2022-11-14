package com.project.ugcc.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
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

    private String creationDate;

    @ElementCollection
    private List<String> imagesUrls;

    @Column(length = 500)
    private String videoSource;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Album album = (Album) o;
        return getId() != null && Objects.equals(getId(), album.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
