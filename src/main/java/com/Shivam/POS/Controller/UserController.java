package com.Shivam.POS.Controller;

import com.Shivam.POS.Exceptions.UserException;
import com.Shivam.POS.Mapper.UserMapper;
import com.Shivam.POS.Payload.dto.UserDto;
import com.Shivam.POS.modal.User;
import com.Shivam.POS.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto>getUserProfile(@RequestHeader("Authorization") String jwt) throws UserException {
        User user=userService.getUserFromJwt(jwt);
        return ResponseEntity.ok(UserMapper.toDTO(user));

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto>getUserById(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws UserException {
        User user=userService.getUserById(id);
        if(user==null){
            throw new UserException("User Not Found");
        }
        return ResponseEntity.ok(UserMapper.toDTO(user));

    }
}
