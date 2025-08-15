package agents.helpers;

import com.google.genai.types.Content;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserSessionConfig {

    @JdbcTypeCode(SqlTypes.JSON)
    private List<Event> eventList = new ArrayList<>();

}
