package agents.service;

import com.google.adk.sessions.Session;
import com.google.adk.sessions.State;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class SessionConverter extends JsonAttributeConverter<Session> {
    public SessionConverter() {
        super(Session.class);
    }
}
