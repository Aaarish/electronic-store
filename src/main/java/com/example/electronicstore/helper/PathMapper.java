package com.example.electronicstore.helper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "image.path")
@Getter
@Setter
public class PathMapper {
    private String user;
    private String category;
}
