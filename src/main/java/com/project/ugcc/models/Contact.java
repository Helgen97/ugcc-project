package com.project.ugcc.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Contacts")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150)
    private String townAndIndex;

    @Column(length = 150)
    private String address;

    @Column(length = 150)
    private String country;

    @Column(length = 150)
    private String phone;

    @Column(length = 150)
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Contact contacts = (Contact) o;
        return id != null && Objects.equals(id, contacts.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
