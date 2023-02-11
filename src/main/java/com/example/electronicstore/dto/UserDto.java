package com.example.electronicstore.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String userId;
    private String name;

    @NotBlank(message = "Email is required!!")
    private String email;

    @NotBlank(message = "Password is required!!")
    private String password;
    private String userImage;
}
