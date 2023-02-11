
package com.example.electronicstore.service.impl;

import com.example.electronicstore.dto.UserDto;
import com.example.electronicstore.entity.User;
import com.example.electronicstore.helper.ImageService;
import com.example.electronicstore.helper.PathMapper;
import com.example.electronicstore.repository.UserRepo;
import com.example.electronicstore.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PathMapper pathMapper;

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setUserId(UUID.randomUUID().toString());
        User savedUser = userRepo.save(user);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUser(String userId) {
        User user = userRepo.findById(userId).get();

        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepo.findAll();
        List<UserDto> userDtos = users.stream()
                .map(u -> modelMapper.map(u, UserDto.class))
                .collect(Collectors.toList());

        return userDtos;
    }

    @Override
    public void updateUser(String userId, UserDto userDto) {
        User user = userRepo.findById(userId).get();

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        userRepo.save(user);
    }

    @Override
    public void removeUser(String userId) {
        userRepo.deleteById(userId);
    }

    @Override
    public String uploadUserImage(String userId, MultipartFile image) {
        User user = userRepo.findById(userId).get();
        boolean isUploaded = imageService.uploadImage(image, pathMapper.getUser());

        if(isUploaded){
            user.setUserImage(image.getOriginalFilename());
            userRepo.save(user);
        }

        return "User image uploaded successfully.";
    }
}
