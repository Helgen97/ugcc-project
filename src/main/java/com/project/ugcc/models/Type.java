package com.project.ugcc.models;

import lombok.*;

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
    private Long ID;

    @Column(length = 500)
    private String title;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type item = (Type) o;
        return Objects.equals(getID(), item.getID()) && getSection().equals(item.getSection());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID(), getSection());
    }
}
