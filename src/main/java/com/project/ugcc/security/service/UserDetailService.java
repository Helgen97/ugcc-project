package com.project.ugcc.security.service;

import com.project.ugcc.models.SiteUser;
import com.project.ugcc.repositories.SiteUserRepository;
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

    private final SiteUserRepository siteUserRepository;

    @Autowired
    public UserDetailService(SiteUserRepository siteUserRepository) {
        this.siteUserRepository = siteUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<SiteUser> optionalUser = siteUserRepository.findByLogin(login);

        if (optionalUser.isPresent()) {
            SiteUser user = optionalUser.get();
            List<GrantedAuthority> grantedAuthorityList = List.of(new SimpleGrantedAuthority(user.getRole()));
            return new User(user.getLogin(), user.getPassword(), grantedAuthorityList);
        }

        throw new UsernameNotFoundException(login + "not found!");
    }
}
