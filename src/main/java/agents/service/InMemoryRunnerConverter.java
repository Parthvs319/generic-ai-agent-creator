package agents.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class InMemoryRunnerConverter implements AttributeConverter<agents.service.Runner, String> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Runner attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            // Convert InMemoryRunner object to JSON string
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to convert InMemoryRunner to JSON string.", e);
        }
    }


    @Override
    public Runner convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        try {
            // Convert JSON string back to InMemoryRunner object
            return mapper.readValue(dbData, Runner.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to convert JSON string to InMemoryRunner.", e);
        }
    }
}
