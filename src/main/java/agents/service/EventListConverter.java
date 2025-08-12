package agents.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.adk.events.Event;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter(autoApply = false)
public class EventListConverter implements AttributeConverter<List<Event>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Event> attribute) {
        try {
            if (attribute == null) return null;
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new IllegalStateException("Error converting List<Event> to JSON", e);
        }
    }

    @Override
    public List<Event> convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null || dbData.isEmpty()) return null;
            return objectMapper.readValue(dbData, new TypeReference<List<Event>>() {});
        } catch (Exception e) {
            throw new IllegalStateException("Error reading JSON to List<Event>", e);
        }
    }
}