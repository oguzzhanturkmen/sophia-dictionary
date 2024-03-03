package com.sophia.security.service;

import com.sophia.entity.concrates.user.User;
import com.sophia.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user != null) {
            return new UserDetailsImpl(
                    user.getId(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getUserRole().getRoleType().name());
        } else {
            throw new UsernameNotFoundException("User Not Found with username: " + username);

        }
    }
}
