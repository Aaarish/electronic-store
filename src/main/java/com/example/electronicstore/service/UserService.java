package com.example.electronicstore.service;

import com.example.electronicstore.dto.UserDto;
import com.example.electronicstore.dto.response.PageableResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

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

    String uploadUserImage(String userId, MultipartFile image);

    PageableResponse<UserDto> getUsersPage(int pageNumber, int pageSize, String sortBy, String sortDir);
}
