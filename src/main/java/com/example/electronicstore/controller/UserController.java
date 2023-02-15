package com.example.electronicstore.controller;

import com.example.electronicstore.dto.UserDto;
import com.example.electronicstore.dto.response.PageableResponse;
import com.example.electronicstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(userDto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId){
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> removeUser(@PathVariable String userId){
        userService.removeUser(userId);
        return ResponseEntity.ok("User deleted");
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId, @RequestBody UserDto userDto){
        userService.updateUser(userId, userDto);
        return ResponseEntity.ok("User updated");
    }

    @PostMapping("/image/{userId}")
    public ResponseEntity<String> uploadUserImage(@PathVariable String userId, @RequestParam("img") MultipartFile image){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.uploadUserImage(userId, image));
    }

    @GetMapping("/page")
    public ResponseEntity<PageableResponse<UserDto>> getUserPage(@RequestParam(defaultValue = "0") int pageNumber,@RequestParam(defaultValue = "2") int pageSize, @RequestParam(defaultValue = "name") String sortBy, @RequestParam(defaultValue = "asc") String sortDir){
        return ResponseEntity.ok(userService.getUsersPage(pageNumber, pageSize, sortBy, sortDir));
    }
}
