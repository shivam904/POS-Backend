package com.Shivam.POS.service.impl;

import com.Shivam.POS.Exceptions.UserException;
import com.Shivam.POS.Mapper.UserMapper;
import com.Shivam.POS.Payload.dto.UserDto;
import com.Shivam.POS.Payload.response.AuthResopnse;
import com.Shivam.POS.configuration.JwtProvider;
import com.Shivam.POS.domain.UserRole;
import com.Shivam.POS.modal.User;
import com.Shivam.POS.repository.UserRepository;
import com.Shivam.POS.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthserviceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserImplementaion customUserImplementaion;

    @Override
    public AuthResopnse register(UserDto userDto) throws UserException {
        User user= userRepository.findByEmail(userDto.getEmail());
        if(user != null){
            throw new UserException("email id already registered");
        }
        if(userDto.getRole().equals(UserRole.ROLE_ADMIN)){
            throw  new UserException("Role admin is not allowed");
        }
        User newUser= new User();
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setRole(userDto.getRole());
        newUser.setFullName(userDto.getFullName());
        newUser.setPhone(userDto.getPhone());
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setLastLogin(LocalDateTime.now());
        User savedUser= userRepository.save(newUser);

        Authentication authentication= new UsernamePasswordAuthenticationToken(userDto.getEmail(),userDto.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt= jwtProvider.generateToken(authentication);

        AuthResopnse authResopnse=new AuthResopnse();
        authResopnse.setJwt(jwt);
        authResopnse.setMsg("Registered successfully");
        authResopnse.setUser(UserMapper.toDTO(savedUser));
        return authResopnse;

    }

    @Override
    public AuthResopnse login(UserDto userDto) throws UserException {
        String email= userDto.getEmail();
        String password= userDto.getPassword();
        Authentication authentication=authenticate(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Collection<? extends GrantedAuthority>authorities=authentication.getAuthorities();
        String role=authorities.iterator().next().getAuthority();
        String jwt= jwtProvider.generateToken(authentication);
        User user=userRepository.findByEmail(email);
        user.setLastLogin(LocalDateTime.now());

        AuthResopnse authResopnse=new AuthResopnse();
        authResopnse.setJwt(jwt);
        authResopnse.setMsg("Login successfully");
        authResopnse.setUser(UserMapper.toDTO(user));
        return authResopnse;


    }
    private Authentication authenticate(String email, String password) throws UserException{
        UserDetails userDetails=customUserImplementaion.loadUserByUsername(email);
        if(userDetails ==null){
                throw new UserException("email id doesn't exist"+email);

        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new UserException("Password doesn't match");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());


    }


}
