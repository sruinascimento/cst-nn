package br.com.rsfot.system1.motor;

import br.com.rsfot.socket.WumpusConnectionManager;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.meca.system1.codelets.MotorCodelet;


public class AgentActuator extends MotorCodelet {
    private final WumpusConnectionManager wumpusConnectionManager;
    private Memory nextActionMO;

    public AgentActuator(String id, WumpusConnectionManager wumpusConnectionManager) {
        super(id);
        this.wumpusConnectionManager = wumpusConnectionManager;
    }

    @Override
    public void accessMemoryObjects() {
        if (nextActionMO == null) {
            this.nextActionMO = this.getInput("NEXT_ACTION_MO");
        }
    }

    @Override
    public void calculateActivation() {

    }

    @Override
    public void proc() {
        if (this.nextActionMO != null && this.nextActionMO.getI() != null) {
            if(!wumpusConnectionManager.isClosed()) {
                wumpusConnectionManager.sendCommand(nextActionMO.getI().toString());
            }
        }
    }
}
