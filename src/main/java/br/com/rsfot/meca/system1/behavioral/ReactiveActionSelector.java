package br.com.rsfot.meca.system1.behavioral;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;

import java.util.Random;
import java.util.Set;

public class ReactiveActionSelector extends Codelet {
    private final Set<String> movements = Set.of("MOVE NORTH", "MOVE EAST", "MOVE SOUTH", "MOVE WEST");

    private Memory glitterMO;
    private Memory stenchMO;
    private Memory breezeMO;
    private Memory nextActionMO;

    @Override
    public void accessMemoryObjects() {
        if (glitterMO == null) {
            glitterMO = this.getInput("GLITTER_MO");
        }

        if(stenchMO == null) {
            stenchMO = this.getInput("STENCH_MO");
        }

        if (breezeMO == null) {
            this.breezeMO = this.getInput("BREEZE_MO");
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
        if (glitterMO != null && glitterMO.getI() != null && (boolean) glitterMO.getI()) {
            //TODO CONFIGURAR ACTION Binaria?
            this.nextActionMO.setI("GRAB");
            return;
        }

        //He doesn't feel stench and breeze
        if ((stenchMO != null && stenchMO.getI() != null && !(boolean) stenchMO.getI())
                && ( breezeMO != null && breezeMO.getI() != null && !(boolean) breezeMO.getI())) {
            int randomIndex = new Random().nextInt(movements.size());
            this.nextActionMO.setI(movements.stream().skip(randomIndex).findFirst().get());
        }
    }
}
