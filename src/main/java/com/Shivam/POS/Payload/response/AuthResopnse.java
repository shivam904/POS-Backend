package com.Shivam.POS.Payload.response;

import com.Shivam.POS.Payload.dto.UserDto;
import lombok.Data;

@Data
public class AuthResopnse {
    private String jwt;
    private String msg;
    private UserDto user;
}
