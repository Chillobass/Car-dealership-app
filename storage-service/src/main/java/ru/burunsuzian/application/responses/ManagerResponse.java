package ru.burunsuzian.application.responses;

import java.util.UUID;

public record ManagerResponse(UUID managerId, String username, String firstName, String lastName) {}
