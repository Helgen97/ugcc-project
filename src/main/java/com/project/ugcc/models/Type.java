package com.project.ugcc.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Types")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "ItemType")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public abstract class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String namedId;

    @Column(length = 200)
    private String title;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type item = (Type) o;
        return Objects.equals(getId(), item.getId()) && getSection().equals(item.getSection());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSection());
    }
}
