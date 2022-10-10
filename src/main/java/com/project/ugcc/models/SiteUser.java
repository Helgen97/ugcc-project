package com.project.ugcc.models;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "Site_Users")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private String login;

    private String password;

    private String role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SiteUser user = (SiteUser) o;
        return ID == user.ID && login.equals(user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, login);
    }
}
