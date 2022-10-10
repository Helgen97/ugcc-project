package com.project.ugcc.services.modelsService;

import com.project.ugcc.models.Contact;
import com.project.ugcc.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ContactsService implements ModelService<Contact> {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactsService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Contact> getOneById(Long id) {
        return contactRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contact> getAll() {
        return contactRepository.findAll();
    }

    @Override
    @Transactional
    public Contact create(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    @Transactional
    public Contact update(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        contactRepository.deleteById(id);
    }
}
