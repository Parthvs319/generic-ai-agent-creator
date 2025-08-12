package agents.controller;

import agents.dto.AddToolRequest;
import agents.dto.CreateAgentRequest;
import agents.dto.CreateUserRequest;
import agents.dto.RunRequest;
import agents.service.AgentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agents")
public class AgentController {

    private final AgentService svc;

    public AgentController(AgentService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<String> createAgent(@RequestBody CreateAgentRequest req) {
        String uuid = svc.createAgent(req);
        return ResponseEntity.ok(uuid);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("API is working");
    }

    @PostMapping("/create/user")
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest req) {
        String uuid = svc.createUser(req);
        return ResponseEntity.ok(uuid);
    }

    @PostMapping("/{uuid}/tools")
    public ResponseEntity<Void> addTool(@PathVariable String uuid, @RequestBody AddToolRequest req) {
        svc.addToolToAgent(uuid, req);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{agentId}/{userId}/run")
    public ResponseEntity<String> runAgent(@PathVariable String agentId , @PathVariable Long userId, @RequestBody RunRequest req) {
        String out = svc.runAgent(agentId, req.input() , userId);
        return ResponseEntity.ok(out);
    }
}
