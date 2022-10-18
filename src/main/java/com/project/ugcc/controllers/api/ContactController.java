package com.project.ugcc.controllers.api;

import com.project.ugcc.DTO.ContactDTO;
import com.project.ugcc.exceptions.NotFoundException;
import com.project.ugcc.models.Contact;
import com.project.ugcc.services.modelsService.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactsService contactsService;

    @Autowired
    public ContactController(ContactsService contactsService) {
        this.contactsService = contactsService;
    }

    @GetMapping
    public List<ContactDTO> getAll() {
        return contactsService.getAll().stream().map(ContactDTO::of).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ContactDTO getOneById(@PathVariable long id) {
        return ContactDTO.of(contactsService.getOneById(id));
    }

    @PostMapping
    public ContactDTO createContact(@RequestBody Contact contact) {
        return ContactDTO.of(contactsService.create(contact));
    }

    @PutMapping
    public ContactDTO updateContact(@RequestBody Contact contact) {
        return ContactDTO.of(contactsService.update(contact));
    }

    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable long id) {
        contactsService.delete(id);
    }
}
