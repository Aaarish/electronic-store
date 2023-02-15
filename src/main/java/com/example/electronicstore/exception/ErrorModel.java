package com.example.electronicstore.exception;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorModel {
    private ErrorType errorCode;
    private String errorMessage;
}
