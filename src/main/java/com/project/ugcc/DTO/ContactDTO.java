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

    private ContactDTO(Contact contact) {
        this.id = contact.getId();
        this.townAndIndex = contact.getTownAndIndex();
        this.address = contact.getAddress();
        this.country = contact.getCountry();
        this.phone = contact.getPhone();
        this.email = contact.getEmail();
    }

    public static ContactDTO of(Contact contact) {
        return new ContactDTO(contact);
    }
}
