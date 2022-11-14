package com.project.ugcc.services.modelsService;

import com.project.ugcc.DTO.SiteUserDTO;
import com.project.ugcc.exceptions.NotFoundException;
import com.project.ugcc.repositories.SiteUserRepository;
import org.hibernate.AssertionFailure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootTest
class SiteUserServiceTest {

    @Autowired
    private SiteUserService siteUserService;

    @MockBean
    private SiteUserRepository siteUserRepository;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;


    @Test
    void getOneById() {

    }

    @Test
    void getAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void delete() {
    }
}