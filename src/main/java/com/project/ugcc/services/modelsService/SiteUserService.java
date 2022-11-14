package com.project.ugcc.services.modelsService;

import com.project.ugcc.DTO.SiteUserDTO;
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
import java.util.stream.Collectors;

@Service
public class SiteUserService implements ModelService<SiteUser, SiteUserDTO> {

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
    public SiteUserDTO getOneById(Long id) {
        LOGGER.info(String.format("Getting user by id. User id: %d", id));
        return SiteUserDTO.of(siteUserRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id %d not found", id))));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SiteUserDTO> getAll() {
        LOGGER.info("Getting all users");
        return siteUserRepository.findAll().stream().map(SiteUserDTO::of).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SiteUserDTO create(SiteUser siteUser) {
        LOGGER.info("Creating new user");
        String password = passwordEncoder.encode(siteUser.getPassword());
        siteUser.setPassword(password);
        return SiteUserDTO.of(siteUserRepository.save(siteUser));
    }

    @Override
    @Transactional
    public SiteUserDTO update(SiteUser siteUser) {
        LOGGER.info(String.format("Updating user. User id: %d", siteUser.getId()));
        SiteUser siteUserToUpdate = siteUserRepository.findById(siteUser.getId()).orElseThrow(() -> new NotFoundException(String.format("User with id %s not found", siteUser.getId())));
        siteUserToUpdate.setLogin(siteUser.getLogin());
        siteUserToUpdate.setRole(siteUser.getRole());
        return SiteUserDTO.of(siteUserRepository.save(siteUserToUpdate));
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
