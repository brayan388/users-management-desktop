package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto;

public record CreateChildRequest(
    String id,
    String name,
    String birthDate,
    String admissionDate) {}