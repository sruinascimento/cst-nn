package br.com.rsfot.system2.learning;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;

public class NNLearningCodelet extends Codelet {
    private Memory agentStatusMO;
    private Memory wumpusDeadMO;
    private Memory breezeMO;
    private Memory glitterMO;
    private Memory impactMO;
    private Memory stenchMO;
    private Memory nextActionMO;

    @Override
    public void accessMemoryObjects() {
        if (agentStatusMO == null) {
            this.agentStatusMO = this.getInput("AGENT_STATUS_MO");
        }

        if (wumpusDeadMO == null) {
            this.wumpusDeadMO = this.getInput("WUMPUS_DEAD_MO");
        }

        if (breezeMO == null) {
            this.breezeMO = this.getInput("BREEZE_MO");
        }

        if (stenchMO == null) {
            this.stenchMO = this.getInput("STENCH_MO");
        }

        if (glitterMO == null) {
            this.glitterMO = this.getInput("GLITTER_MO");
        }

        if (impactMO == null) {
            this.impactMO = this.getInput("IMPACT_MO");
        }

        if (nextActionMO == null) {
            this.nextActionMO = this.getOutput("NEXT_ACTION_MO");
        }
    }

    @Override
    public void calculateActivation() {

    }

    @Override
    public void proc() {
        if (
                this.agentStatusMO == null
                        || this.breezeMO == null
                        || this.glitterMO == null
                        || this.impactMO == null
                        || this.stenchMO == null
                        || this.wumpusDeadMO == null
        ) {
            return;
        }


        //TODO: instanciar o modelo para gerar a próxima ação.

        this.nextActionMO.setI("NO ACTION");
    }

}
