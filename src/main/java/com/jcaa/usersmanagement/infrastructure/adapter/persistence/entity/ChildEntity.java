
package com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity;

import java.time.LocalDate;

public record ChildEntity(
    String id,
    String name,
    LocalDate birthDate,
    LocalDate admissionDate,
    LocalDate exitDate) {}
