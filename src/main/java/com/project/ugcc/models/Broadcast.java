package com.project.ugcc.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Broadcasts")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Broadcast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150)
    private String churchFrom;

    @Column(length = 150)
    private String churchName;

    @Column(length = 150)
    private String churchTown;

    @Column(length = 300)
    private String imageUrl;

    @Column(length = 500)
    private String schedule;

    @Column(length = 300)
    private String youtubeLink;

    @Column(length = 300)
    private String instagramLink;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Broadcast broadcast = (Broadcast) o;
        return id != null && Objects.equals(id, broadcast.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
