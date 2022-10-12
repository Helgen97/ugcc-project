package com.project.ugcc.security.service;

import com.project.ugcc.models.SiteUser;
import com.project.ugcc.repositories.SiteUserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {

    private static final Logger LOGGER = LogManager.getLogger(UserDetailService.class.getName());

    private final SiteUserRepository siteUserRepository;

    @Autowired
    public UserDetailService(SiteUserRepository siteUserRepository) {
        this.siteUserRepository = siteUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<SiteUser> optionalUser = siteUserRepository.findByLogin(login);

        if (optionalUser.isPresent()) {
            LOGGER.info(String.format("User with login: %s is found", login));
            SiteUser user = optionalUser.get();
            List<GrantedAuthority> grantedAuthorityList = List.of(new SimpleGrantedAuthority(user.getRole()));
            return new User(user.getLogin(), user.getPassword(), grantedAuthorityList);
        }

        LOGGER.error(String.format("User with login: %s not found.", login));
        throw new UsernameNotFoundException(login + "not found!");
    }
}
