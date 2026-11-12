
package com.jcaa.usersmanagement.application.service.dto.command;

import jakarta.validation.constraints.NotBlank;

public record UpdateChildCommand(
    @NotBlank(message = "id must not be blank") String id,
    @NotBlank(message = "name must not be blank") String name,
    @NotBlank(message = "birth date must not be blank") String birthDate,
    @NotBlank(message = "admission date must not be blank") String admissionDate,
    String exitDate) {

}
