package br.com.rsfot.system1.motor;

import br.com.rsfot.game.GameWumpus;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.meca.system1.codelets.MotorCodelet;


public class AgentActuator extends MotorCodelet {
    private GameWumpus gameWumpus;
    private Memory nextActionMO;

    public AgentActuator(String id, GameWumpus gameWumpus) {
        super(id);
        this.gameWumpus = gameWumpus;
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
        if (gameWumpus.isGameOver()) {
            System.out.println(">>>> Game Over");
            System.out.println(">>>> report: " + gameWumpus.report());
            gameWumpus.resetGame();
            System.exit(1);
        }
        if (this.nextActionMO != null && this.nextActionMO.getI() != null) {
            gameWumpus.executeAction((String) this.nextActionMO.getI());
        }
    }
}
