package com.project.ugcc.services.modelsService;

import com.project.ugcc.exceptions.NotFoundException;
import com.project.ugcc.models.SiteUser;
import com.project.ugcc.repositories.SiteUserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SiteUserService implements ModelService<SiteUser> {

    private final static Logger LOGGER = LogManager.getLogger(SiteUserService.class.getName());

    private final SiteUserRepository siteUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public SiteUserService(SiteUserRepository siteUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.siteUserRepository = siteUserRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public SiteUser getOneById(Long id) {
        LOGGER.info(String.format("Getting user by id. User id: %d", id));
        return siteUserRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id %d not found", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SiteUser> getAll() {
        LOGGER.info("Getting all users");
        return siteUserRepository.findAll();
    }

    @Override
    @Transactional
    public SiteUser create(SiteUser siteUser) {
        LOGGER.info("Creating new user");
        String password = passwordEncoder.encode(siteUser.getPassword());
        siteUser.setPassword(password);
        return siteUserRepository.save(siteUser);
    }

    @Override
    @Transactional
    public SiteUser update(SiteUser siteUser) {
        LOGGER.info(String.format("Updating user. User id: %d", siteUser.getId()));
        SiteUser siteUserToUpdate = siteUserRepository.findById(siteUser.getId()).orElseThrow(() -> new NotFoundException(String.format("User with id %s not found", siteUser.getId())));
        siteUserToUpdate.setLogin(siteUser.getLogin());
        siteUserToUpdate.setRole(siteUser.getRole());
        return siteUserRepository.save(siteUserToUpdate);
    }

    @Transactional
    public void changePassword(Long id, String rawPassword) {
        LOGGER.info(String.format("Changing password for user. User id: %d", id));
        SiteUser siteUser = siteUserRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id %s not found", id)));
        siteUser.setPassword(passwordEncoder.encode(rawPassword));
        siteUserRepository.save(siteUser);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        LOGGER.info(String.format("Deleting user. User id: %d", id));
        siteUserRepository.deleteById(id);
    }
}
