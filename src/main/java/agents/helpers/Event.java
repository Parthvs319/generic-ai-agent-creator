package agents.helpers;

import com.google.genai.types.Content;
import lombok.Data;

import java.util.Optional;

@Data
public class Event {

    private String eventId;

    private String invocationId;

    private String input;

}
