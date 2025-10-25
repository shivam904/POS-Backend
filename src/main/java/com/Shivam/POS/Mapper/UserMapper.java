package com.Shivam.POS.Mapper;

import com.Shivam.POS.Payload.dto.UserDto;
import com.Shivam.POS.modal.User;

public class UserMapper {

    public static UserDto toDTO(User savedUser){
        UserDto userDto= new UserDto();
        userDto.setId(savedUser.getId());
        userDto.setFullName(savedUser.getFullName());
        userDto.setEmail(savedUser.getEmail());
        userDto.setRole(savedUser.getRole());
        userDto.setPhone(savedUser.getPhone());
        userDto.setCreatedAt(savedUser.getCreatedAt());
        userDto.setLastLogin(savedUser.getLastLogin());
        userDto.setUpdatedAt(savedUser.getUpdatedAt());
        userDto.setBranchId(savedUser.getBranch() !=null?savedUser.getBranch().getId():null);
        userDto.setStoreId(savedUser.getStore() !=null ? savedUser.getStore().getId():null);
        return userDto;
    }
    public static User toEntity(UserDto userDto){
        User createdUser= new User();
        createdUser.setEmail(userDto.getEmail());
        createdUser.setFullName(userDto.getFullName());
        createdUser.setRole(userDto.getRole());
        createdUser.setCreatedAt(userDto.getCreatedAt());
        createdUser.setUpdatedAt(userDto.getUpdatedAt());
        createdUser.setLastLogin(userDto.getLastLogin());
        createdUser.setPhone(userDto.getPhone());
        createdUser.setPassword(userDto.getPassword());

        return createdUser;



    }
}
