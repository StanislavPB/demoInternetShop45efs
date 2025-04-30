package org.demointernetshop45efs.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {


    @NotBlank (message = "First name is required and must be not blank")
    @Size(min = 3, max = 15, message = "First name length not correct")
    private String firstName;

    private String lastName;

    @Email
    private String email;

    private String hashPassword;


}
