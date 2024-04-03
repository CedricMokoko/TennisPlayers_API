package com.mokoko.security;

import com.mokoko.entities.RoleEntity;
import com.mokoko.entities.UserEntity;
import com.mokoko.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findOneWithRolesByLoginIgnoreCase(login)
                .map(this::createSecurityUser)
                .orElseThrow(() -> new UsernameNotFoundException("User with login " + login + " could not be found."));
    }

    private org.springframework.security.core.userdetails.User createSecurityUser(UserEntity user) {
        List<SimpleGrantedAuthority> grantedRoles = user
                .getRoles()
                .stream()
                .map(RoleEntity::getNome)
                .map(SimpleGrantedAuthority::new)
                .toList();
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), grantedRoles);
    }
}
