package agents.dto;

public record CreateAgentRequest(String name, String description, String model, String instruction) { }
