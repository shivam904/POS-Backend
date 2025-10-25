package com.Shivam.POS.service;

import com.Shivam.POS.Exceptions.UserException;
import com.Shivam.POS.Payload.dto.UserDto;
import com.Shivam.POS.Payload.response.AuthResopnse;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    AuthResopnse register(UserDto userdto) throws UserException;
    AuthResopnse login(UserDto userDto) throws UserException;
}
