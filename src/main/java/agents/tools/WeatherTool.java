package agents.tools;

import agents.agent.Tool;
import lombok.Data;

@Data
public class WeatherTool implements Tool {

    private final String apiKey;

    public WeatherTool() { this.apiKey = null; }

    public WeatherTool(String configJson) {
        // in real application parse JSON and extract keys; simplified here
        this.apiKey = configJson;
    }

    @Override
    public String invoke(String input) {
        // simple mock
        return "Sunny 25°C in " + input;
    }

    @Override
    public String toString() {
        return "WeatherTool";
    }
}
