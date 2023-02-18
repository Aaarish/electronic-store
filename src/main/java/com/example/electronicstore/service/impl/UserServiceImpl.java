
package com.example.electronicstore.service.impl;

import com.example.electronicstore.dto.UserDto;
import com.example.electronicstore.dto.response.PageableResponse;
import com.example.electronicstore.entity.User;
import com.example.electronicstore.exception.ErrorModel;
import com.example.electronicstore.exception.ErrorType;
import com.example.electronicstore.exception.ResourceNotFoundException;
import com.example.electronicstore.helper.ImageService;
import com.example.electronicstore.helper.PageableResponseHelper;
import com.example.electronicstore.helper.PathMapper;
import com.example.electronicstore.repository.UserRepo;
import com.example.electronicstore.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
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
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setUserId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
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
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
                Arrays.asList(new ErrorModel(ErrorType.RESOURCE_NOT_FOUND, "No user found with this id"))
        ));

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
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
                Arrays.asList(new ErrorModel(ErrorType.RESOURCE_NOT_FOUND, "No user found with this id"))
        ));
        boolean isUploaded = imageService.uploadImage(image, pathMapper.getUser());

        if(isUploaded){
            user.setUserImage(image.getOriginalFilename());
            userRepo.save(user);
        }

        return "User image uploaded successfully.";
    }

    @Override
    public PageableResponse<UserDto> getUsersPage(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<User> page = userRepo.findAll(pageable);

        PageableResponse<UserDto> pageableResponse = PageableResponseHelper.getPageableResponse(page, UserDto.class);
        return pageableResponse;
    }
}
