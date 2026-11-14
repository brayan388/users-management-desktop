
-- =============================================
-- Script de creacion de la tabla Child
-- Gestion de Usuarios - Arquitectura Hexagonal
-- =============================================

USE crud_usuarios;

CREATE TABLE IF NOT EXISTS children (
    id              VARCHAR(100) NOT NULL PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    birth_date      DATE NOT NULL,
    admission_date  DATE NOT NULL,
    exit_date       DATE NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

