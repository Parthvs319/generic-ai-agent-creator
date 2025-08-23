package agents.helpers;

import com.google.gson.JsonObject;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SuccessResponse {

    private Long id;
    private boolean success = true;
    private JsonObject data = new JsonObject();
    private String fileName;
    private boolean safe = false;

    public static SuccessResponse generate() {
        return new SuccessResponse();
    }

    public SuccessResponse(boolean success) {
        this.success = success;
    }

    public SuccessResponse(Long id, boolean success) {
        this.id = id;
        this.success = success;
    }

    public SuccessResponse(Long id) {
        this.id = id;
    }

    /** Add key-value to data object */
    public SuccessResponse put(String key, Object value) {
        if (value == null) {
            this.data.addProperty(key, (String) null);
        } else if (value instanceof Number) {
            this.data.addProperty(key, (Number) value);
        } else if (value instanceof Boolean) {
            this.data.addProperty(key, (Boolean) value);
        } else {
            this.data.addProperty(key, value.toString());
        }
        return this;
    }

    public SuccessResponse withId(Long id) {
        this.id = id;
        return this;
    }

    public SuccessResponse withFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public SuccessResponse markSafe(boolean safe) {
        this.safe = safe;
        return this;
    }

    /** Final JSON builder */
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("success", this.success);

        if (this.id != null) {
            json.addProperty("id", this.id);
        }
        if (this.fileName != null) {
            json.addProperty("fileName", this.fileName);
        }
        json.addProperty("safe", this.safe);

        if (this.data.size() > 0) {
            this.data.entrySet().forEach(entry -> json.add(entry.getKey(), entry.getValue()));
        }

        return json;
    }
}