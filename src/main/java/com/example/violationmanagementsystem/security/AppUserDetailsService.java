package com.example.violationmanagementsystem.security;

import com.example.violationmanagementsystem.entity.User;
import com.example.violationmanagementsystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepo.findUserByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("user not found");
        }

        return new AppUserDetails(user.orElse(null));
    }

    public AppUserDetails loadUserById(Long id) {
        Optional<User> user = userRepo.findById(id);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("user not found");
        }

        return new AppUserDetails(user.orElse(null));
    }
}
