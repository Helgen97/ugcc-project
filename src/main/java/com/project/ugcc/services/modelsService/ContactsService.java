package com.project.ugcc.services.modelsService;

import com.project.ugcc.DTO.ContactDTO;
import com.project.ugcc.exceptions.NotFoundException;
import com.project.ugcc.models.Contact;
import com.project.ugcc.repositories.ContactRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactsService implements ModelService<Contact, ContactDTO> {

    private final static Logger LOGGER = LogManager.getLogger(ContactsService.class.getName());

    private final ContactRepository contactRepository;

    @Autowired
    public ContactsService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ContactDTO getOneById(Long id) {
        LOGGER.info(String.format("Getting contact by id. Contact id: %d", id));
        return ContactDTO.of(contactRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Contact with id %d not found", id))));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactDTO> getAll() {
        LOGGER.info("Getting all contacts");
        return contactRepository.findAll().stream().map(ContactDTO::of).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ContactDTO create(Contact contact) {
        LOGGER.info("Creating new contact");
        return ContactDTO.of(contactRepository.save(contact));
    }

    @Override
    @Transactional
    public ContactDTO update(Contact contact) {
        LOGGER.info(String.format("Updating contact. Contact id: %d", contact.getId()));
        return ContactDTO.of(contactRepository.save(contact));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        LOGGER.info(String.format("Deleting contact. Contact id: %d", id));
        contactRepository.deleteById(id);
    }
}
