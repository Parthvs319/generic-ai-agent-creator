package agents.agent;

import agents.entity.ToolEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ToolRegistry {

    private static final String ALLOWED_PACKAGE_PREFIX = "com.example.adk.tools";

    public List<Tool> instantiateTools(List<ToolEntity> toolEntities) {
        List<Tool> tools = new ArrayList<>();
        for (ToolEntity te : toolEntities) {
            String clsName = te.getClassName();
            if (!clsName.startsWith(ALLOWED_PACKAGE_PREFIX)) {
                throw new IllegalArgumentException("Tool class not allowed: " + clsName);
            }
            try {
                Class<?> clazz = Class.forName(clsName);
                if (!Tool.class.isAssignableFrom(clazz)) {
                    throw new IllegalArgumentException("Class does not implement Tool: " + clsName);
                }
                Tool instance;
                try {
                    instance = (Tool) clazz.getDeclaredConstructor(String.class).newInstance(te.getConfigJson());
                } catch (NoSuchMethodException e) {
                    instance = (Tool) clazz.getDeclaredConstructor().newInstance();
                }
                tools.add(instance);
            } catch (Exception ex) {
                throw new RuntimeException("Failed to load tool: " + clsName, ex);
            }
        }
        return tools;
    }
}
