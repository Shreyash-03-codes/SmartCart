package com.smartcart.ecommerce.common.services;

import com.smartcart.ecommerce.modules.user.model.User;
import com.smartcart.ecommerce.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserService {

    private final UserRepository userRepository;

    public User getUser(){
        UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername()).orElseThrow(()-> new UsernameNotFoundException("Username not found."));
    }
}
