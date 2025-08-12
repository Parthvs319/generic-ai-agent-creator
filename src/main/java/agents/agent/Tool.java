package agents.agent;

/**
 * Simple Tool contract. In real-world, make this strongly typed.
 */
public interface Tool {
    String invoke(String input);
}
