package ru.aston.ivantkachuk.notificationuserservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.aston.ivantkachuk.notificationuserservice.entity.Operation;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEventRequestDto {
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "Operation cannot be null")
    private Operation operation;
}
