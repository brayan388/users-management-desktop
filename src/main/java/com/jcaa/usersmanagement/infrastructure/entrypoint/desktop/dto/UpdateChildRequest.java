package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto;

public record UpdateChildRequest(
    String id,
    String name,
    String birthDate,
    String admissionDate,
    String exitDate) {}