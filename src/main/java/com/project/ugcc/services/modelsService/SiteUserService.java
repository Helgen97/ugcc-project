package com.project.ugcc.services.modelsService;

import com.project.ugcc.exceptions.NotFoundException;
import com.project.ugcc.models.SiteUser;
import com.project.ugcc.repositories.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SiteUserService implements ModelService<SiteUser> {

    private final SiteUserRepository siteUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public SiteUserService(SiteUserRepository siteUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.siteUserRepository = siteUserRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SiteUser> getOneById(Long id) {
        return siteUserRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SiteUser> getAll() {
        return siteUserRepository.findAll();
    }

    @Override
    @Transactional
    public SiteUser create(SiteUser siteUser) {
        String password = passwordEncoder.encode(siteUser.getPassword());
        siteUser.setPassword(password);
        return siteUserRepository.save(siteUser);
    }

    @Override
    @Transactional
    public SiteUser update(SiteUser siteUser) {
        SiteUser siteUserToUpdate = siteUserRepository.findById(siteUser.getID()).orElseThrow(() -> new NotFoundException(String.format("User with id: %s not found", siteUser.getID())));
        siteUserToUpdate.setLogin(siteUser.getLogin());
        siteUserToUpdate.setRole(siteUser.getRole());
        return siteUserRepository.save(siteUserToUpdate);
    }

    @Transactional
    public void changePassword(Long id, String rawPassword) {
        SiteUser siteUser = siteUserRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id: %s not found", id)));
        siteUser.setPassword(passwordEncoder.encode(rawPassword));
        siteUserRepository.save(siteUser);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        siteUserRepository.deleteById(id);
    }
}
