package agents.service;

import com.google.adk.sessions.State;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class StateConverter extends JsonAttributeConverter<State> {
    public StateConverter() {
        super(State.class);
    }
}
