package com.project.ugcc.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Table(name = "Sections")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(length = 200)
    private String title;

    private Category category;

    @OneToMany(mappedBy = "section")
    @ToString.Exclude
    private List<Type> items;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return ID == section.ID && category == section.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, category);
    }
}
