package com.project.ugcc.DTO;

import com.project.ugcc.models.Contact;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ContactDTO {

    private Long id;
    private String townAndIndex;
    private String address;
    private String country;
    private String phone;
    private String email;

    private ContactDTO(Long id, String townAndIndex, String address, String country, String phone, String email) {
        this.id = id;
        this.townAndIndex = townAndIndex;
        this.address = address;
        this.country = country;
        this.phone = phone;
        this.email = email;
    }

    public static ContactDTO of(Contact contact) {
        return new ContactDTO(
                contact.getId(),
                contact.getTownAndIndex(),
                contact.getAddress(),
                contact.getCountry(),
                contact.getPhone(),
                contact.getEmail()
        );
    }
}
