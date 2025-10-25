package com.Shivam.POS.service.impl;

import com.Shivam.POS.Exceptions.UserException;
import com.Shivam.POS.configuration.JwtProvider;
import com.Shivam.POS.modal.User;
import com.Shivam.POS.repository.UserRepository;
import com.Shivam.POS.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public User getUserFromJwt(String token) throws UserException {
        String email = jwtProvider.getEmailFromToken(token);
        User user=userRepository.findByEmail(email);
        if(user==null){
            throw new UserException("Invalid token");
        }
        return user;
    }

    @Override
    public User getCurrentUser() throws UserException {
        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        User user= userRepository.findByEmail(email);
        if(user==null){
            throw  new UserException("Invalid user not found");
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) throws UserException {
        User user= userRepository.findByEmail(email);
        if(user==null){
            throw new UserException("user not found");
        }
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(
                null
        );
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
