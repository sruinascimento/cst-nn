package br.com.rsfot.system1.sensory;

import br.com.rsfot.socket.WumpusConnectionManager;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.meca.system1.codelets.SensoryCodelet;
import org.json.JSONObject;

public class ImpactSensor extends SensoryCodelet {
    private final WumpusConnectionManager wumpusConnectionManager;
    private Memory impactMO;

    public ImpactSensor(String id, WumpusConnectionManager wumpusConnectionManager) {
        super(id);
        this.wumpusConnectionManager = wumpusConnectionManager;
    }

    @Override
    public void accessMemoryObjects() {
        if (impactMO == null) {
            this.impactMO = this.getOutput("IMPACT_MO");
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
        impactMO.setI(parseImpactInfo(infoWumpusWord));
    }

    private boolean parseImpactInfo(String json) {
        JSONObject impactInfo = new JSONObject(json);
        JSONObject feelings = impactInfo.getJSONObject("feelingByCoordinate");
        return feelings.getBoolean("impact");
    }
}
