package com.example.TaskBoard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// Temporarily stores a JWT token, this class exists for readability

@Data
@AllArgsConstructor
public class Token {
    private String token;
}
