package agents.helpers;

public class RoutingError extends RuntimeException {

    private int statusCode=409;

    public RoutingError(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
    public RoutingError(String message) {
        super(message);
    }

    public int getStatusCode() {
        return statusCode;
    }

}
