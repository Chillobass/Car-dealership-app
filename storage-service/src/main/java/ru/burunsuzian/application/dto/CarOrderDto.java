package ru.burunsuzian.application.dto;

import java.util.UUID;

public record CarOrderDto(UUID carId, UUID managerId) {
}
