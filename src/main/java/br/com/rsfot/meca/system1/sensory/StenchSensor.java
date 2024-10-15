package br.com.rsfot.meca.system1.sensory;

import br.com.rsfot.socket.WumpusConnectionManager;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.meca.system1.codelets.SensoryCodelet;
import org.json.JSONObject;

public class StenchSensor extends SensoryCodelet {
    private final WumpusConnectionManager wumpusConnectionManager;
    private Memory stenchMO;

    public StenchSensor(String id, WumpusConnectionManager wumpusConnectionManager) {
        super(id);
        this.wumpusConnectionManager = wumpusConnectionManager;
    }

    @Override
    public void accessMemoryObjects() {
        int index = 0;
        if (stenchMO == null) {
            this.stenchMO = this.getOutput("STENCH_MO", index);
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
        stenchMO.setI(parseStenchInfo(infoWumpusWord));
    }

    private boolean parseStenchInfo(String json) {
        JSONObject stenchInfo = new JSONObject(json);
        JSONObject feelings = stenchInfo.getJSONObject("feelingByCoordinate");
        return feelings.getBoolean("stench");
    }

}
