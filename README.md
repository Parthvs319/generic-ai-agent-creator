# ADK Spring Generic Agent

This project is a starter skeleton for a Spring Boot application that stores agent definitions in MySQL (Hibernate),
supports pluggable tools, and dynamically constructs ADK agents at runtime.

## Structure
- `agents.entity` : JPA entities (AgentEntity, ToolEntity)
- `repository` : Spring Data JPA repositories
- `dto` : Request/response DTOs
- `agent` : AgentFactory, ToolRegistry, Tool interface
- `service` : AgentService to create agents/add tools/run
- `controller` : REST endpoints

## Notes
- The ADK SDK dependency is a placeholder. Replace with actual ADK SDK coordinates and adjust AgentFactory accordingly.
- For security, only allow tool classes from a trusted package.
