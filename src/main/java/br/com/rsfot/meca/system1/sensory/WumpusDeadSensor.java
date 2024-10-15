package br.com.rsfot.meca.system1.sensory;

import br.com.rsfot.socket.WumpusConnectionManager;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.meca.system1.codelets.SensoryCodelet;
import org.json.JSONObject;

public class WumpusDeadSensor extends SensoryCodelet {
    private final WumpusConnectionManager wumpusConnectionManager;
    private Memory wumpusDeadMO;

    public WumpusDeadSensor(String id, WumpusConnectionManager wumpusConnectionManager) {
        super(id);
        this.wumpusConnectionManager = wumpusConnectionManager;
    }

    @Override
    public void accessMemoryObjects() {
        int index = 0;
        if (wumpusDeadMO == null) {
            this.wumpusDeadMO = this.getOutput("WUMPUS_DEAD_MO", index);
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
        wumpusDeadMO.setI(parseWumpusDeadInfo(infoWumpusWord));
    }

    private boolean parseWumpusDeadInfo(String json) {
        JSONObject wumpusDeadInfo = new JSONObject(json);
        return wumpusDeadInfo.getBoolean("wumpusDead");
    }

}
