package br.com.rsfot.system1.sensory;

import br.com.rsfot.socket.WumpusConnectionManager;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.meca.system1.codelets.SensoryCodelet;
import org.json.JSONObject;

public class AgentStatusSensor extends SensoryCodelet {
    private final WumpusConnectionManager wumpusConnectionManager;
    private Memory agentStatusMO;

    public AgentStatusSensor(String id, WumpusConnectionManager wumpusConnectionManager) {
        super(id);
        this.wumpusConnectionManager = wumpusConnectionManager;
    }

    @Override
    public void accessMemoryObjects() {
        if (agentStatusMO == null) {
            this.agentStatusMO = this.getOutput("AGENT_STATUS_MO");
        }
    }

    @Override
    public void calculateActivation() {
    }

    @Override
    public void proc() {
        String infoWumpusWord = wumpusConnectionManager.retrieveInfoWumpusWorld();
        agentStatusMO.setI(parseAgentStatusInfo(infoWumpusWord));
    }

    private AgentStatus parseAgentStatusInfo(String infoWumpusWord) {
        JSONObject agentStatusInfo = new JSONObject(infoWumpusWord)
                .getJSONObject("agentStatus");
        return new AgentStatus(
                agentStatusInfo.getString("coordinate"),
                agentStatusInfo.getBoolean("isAlive"),
                agentStatusInfo.getBoolean("hasGold"),
                agentStatusInfo.getBoolean("hasArrow")
        );
    }

    public record AgentStatus(
            int coordinateX,
            int coordinateY,
            int isAlive,
            int hasGold,
            int hasArrow
    ) {

        public AgentStatus(String coordinate, boolean isAlive, boolean hasGold, boolean hasArrow) {
            this(
                    Integer.parseInt(coordinate.split(",")[0]),
                    Integer.parseInt(coordinate.split(",")[1]),
                    isAlive ? 1 : 0,
                    hasGold ? 1 : 0,
                    hasArrow ? 1 : 0
            );
        }
    }
}
