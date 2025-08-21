package agents.helpers.enums;

public enum AgentType {

    PARALLEL("PARALLEL"),
    SEQUENTIAL("SEQUENTIAL"),
    SINGLE_AGENT("SINGLE_AGENT"),
    LOOP_AGENT("LOOP_AGENT");




    String dbValue;

    AgentType(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getValue() {
        return dbValue;
    }


}
