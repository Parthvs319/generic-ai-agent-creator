package agents.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class JsonAttributeConverter<T> implements AttributeConverter<T, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final Class<T> clazz;

    public JsonAttributeConverter(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String convertToDatabaseColumn(T attribute) {
        try {
            if (attribute == null) return null;
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new IllegalStateException("Error converting object to JSON", e);
        }
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null || dbData.isEmpty()) return null;
            return objectMapper.readValue(dbData, clazz);
        } catch (Exception e) {
            throw new IllegalStateException("Error reading JSON to object", e);
        }
    }
}
