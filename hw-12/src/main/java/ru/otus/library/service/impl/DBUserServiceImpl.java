package ru.otus.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.library.repository.UserRepository;
import ru.otus.library.rest.dto.UserDTO;

import java.util.Collections;

@Service("userDetailsService")
public class DBUserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public DBUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username)
                .map(UserDTO::toDomain)
                .map(user -> new User(user.getLogin(), user.getPassword(), Collections.emptyList()))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("user with name:%s not found", username)));
    }
}
