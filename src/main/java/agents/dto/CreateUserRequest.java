package agents.dto;

import org.jetbrains.annotations.NotNull;

public record CreateUserRequest(@NotNull String name, @NotNull String email, @NotNull String userName, @NotNull String password) { }

