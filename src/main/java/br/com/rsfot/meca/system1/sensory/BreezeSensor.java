package br.com.rsfot.meca.system1.sensory;

import br.com.rsfot.socket.WumpusConnectionManager;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.meca.system1.codelets.SensoryCodelet;
import org.json.JSONObject;

public class BreezeSensor extends SensoryCodelet {
    private final WumpusConnectionManager wumpusConnectionManager;
    private Memory breezeMO;

    public BreezeSensor(String id, WumpusConnectionManager wumpusConnectionManager) {
        super(id);
        this.wumpusConnectionManager = wumpusConnectionManager;
    }

    @Override
    public void accessMemoryObjects() {
        int index = 0;
        if (breezeMO == null) {
            this.breezeMO = this.getOutput("BREEZE_MO", index);
        }
    }

    @Override
    public void calculateActivation() {}

    @Override
    public void proc() {
        String infoWumpusWord = wumpusConnectionManager.retrieveInfoWumpusWorld();
        if (infoWumpusWord == null) {
            return;
        }
        breezeMO.setI(parseBreezeInfo(infoWumpusWord));
    }

    private boolean parseBreezeInfo(String json) {
        JSONObject breezeInfo = new JSONObject(json);
        JSONObject feelings = breezeInfo.getJSONObject("feelingByCoordinate");
        return feelings.getBoolean("breeze");
    }
}
