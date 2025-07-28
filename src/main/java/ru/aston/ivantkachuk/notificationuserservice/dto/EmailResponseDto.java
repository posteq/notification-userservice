package ru.aston.ivantkachuk.notificationuserservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailResponseDto {
    private String email;
    private String message;
}
