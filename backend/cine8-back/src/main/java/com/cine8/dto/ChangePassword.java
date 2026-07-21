package com.cine8.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePassword {

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;
}
