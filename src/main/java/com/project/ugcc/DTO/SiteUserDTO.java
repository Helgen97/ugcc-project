package com.project.ugcc.DTO;

import com.project.ugcc.models.SiteUser;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SiteUserDTO {

    private Long id;
    private String login;
    private String role;

    private SiteUserDTO(Long id, String login, String role) {
        this.id = id;
        this.login = login;
        this.role = role;
    }

    public static SiteUserDTO of(SiteUser siteUser) {
        return new SiteUserDTO(
                siteUser.getId(),
                siteUser.getLogin(),
                siteUser.getRole()
        );
    }
}
