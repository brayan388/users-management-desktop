
package com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto;

import java.time.LocalDate;

public record ChildPersistenceDto(
    String id,
    String name,
    LocalDate birthDate,
    LocalDate admissionDate,
    LocalDate exitDate) {}
