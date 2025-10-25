package com.Shivam.POS.Controller;

import com.Shivam.POS.Exceptions.UserException;
import com.Shivam.POS.Payload.dto.UserDto;
import com.Shivam.POS.Payload.response.AuthResopnse;
import com.Shivam.POS.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResopnse>signUpHandler(@RequestBody UserDto userDto) throws UserException {
        return ResponseEntity.ok(
                authService.register(userDto)
        );
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResopnse>loginHnalder(@RequestBody UserDto userDto) throws UserException {
        return ResponseEntity.ok(
                authService.login(userDto)
        );
    }
}
