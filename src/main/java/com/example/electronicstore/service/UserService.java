package com.example.electronicstore.service;

import com.example.electronicstore.dto.UserDto;

import java.util.List;

public interface UserService {

    //create user
    UserDto addUser(UserDto userDto);

    //get single user
    UserDto getUser(String userId);

    //get all users
    List<UserDto> getUsers();

    //update user
    void updateUser(String userId, UserDto userDto);

    //remove user
    void removeUser(String userId);
}
