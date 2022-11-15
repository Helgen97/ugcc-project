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

    private SiteUserDTO(SiteUser siteUser) {
        this.id = siteUser.getId();
        this.login = siteUser.getLogin();
        this.role = siteUser.getRole();
    }

    public static SiteUserDTO of(SiteUser siteUser) {
        return new SiteUserDTO(siteUser);
    }
}
