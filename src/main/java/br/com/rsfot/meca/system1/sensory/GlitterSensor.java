package br.com.rsfot.meca.system1.sensory;

import br.com.rsfot.socket.WumpusConnectionManager;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.meca.system1.codelets.SensoryCodelet;
import org.json.JSONObject;

public class GlitterSensor extends SensoryCodelet {
    private final WumpusConnectionManager wumpusConnectionManager;
    private Memory glitterMO;

    public GlitterSensor(String id, WumpusConnectionManager wumpusConnectionManager) {
        super(id);
        this.wumpusConnectionManager = wumpusConnectionManager;
    }

    @Override
    public void accessMemoryObjects() {
        int index = 0;
        if (glitterMO == null) {
            this.glitterMO = this.getOutput("GLITTER_MO", index);
        }
    }

    @Override
    public void calculateActivation() {
    }

    @Override
    public void proc() {
        String infoWumpusWord = wumpusConnectionManager.retrieveInfoWumpusWorld();
        if (infoWumpusWord == null) {
            return;
        }
        glitterMO.setI(parseGlitterInfo(infoWumpusWord));
    }

    private boolean parseGlitterInfo(String json) {
        JSONObject glitterInfo = new JSONObject(json);
        JSONObject feelings = glitterInfo.getJSONObject("feelingByCoordinate");
        return feelings.getBoolean("glitter");
    }
}
