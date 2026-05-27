--liquibase formatted sql

--changeset chillobass:lab5-001-add-is-available-to-cars
ALTER TABLE cars
    ADD COLUMN is_available BOOLEAN NOT NULL DEFAULT TRUE;

CREATE INDEX idx_cars_is_available ON cars(is_available) WHERE is_available = TRUE;