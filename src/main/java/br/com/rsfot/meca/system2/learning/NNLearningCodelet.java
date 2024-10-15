package br.com.rsfot.meca.system2.learning;

import br.com.rsfot.meca.system1.sensory.AgentStatusSensor;
import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;

public class NNLearningCodelet extends Codelet {
    private Memory agentStatusMO;
    private Memory wumpusDeadMO;
    private Memory breezeMO;
    private Memory glitterMO;
    private Memory impactMO;
    private Memory stenchMO;
    private Memory nextActionMO;

    private String[] actions = {"GRAB", "MOVE NORTH", "MOVE SOUTH", "MOVE EAST", "MOVE WEST", "SHOOT NORTH", "SHOOT SOUTH", "SHOOT EAST", "SHOOT WEST", "NO ACTION"};
    private MultiLayerNetwork loadedModel;

    public NNLearningCodelet() {
        super();
        File modelFile = new File("4x4_matrix_trained_model_30_epochs.zip");
        try {
            this.loadedModel = ModelSerializer.restoreMultiLayerNetwork(modelFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

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
                        || this.agentStatusMO.getI() == null
                        || this.breezeMO == null
                        || this.breezeMO.getI() == null
                        || this.glitterMO == null
                        || this.glitterMO.getI() == null
                        || this.impactMO == null
                        || this.impactMO.getI() == null
                        || this.stenchMO == null
                        || this.stenchMO.getI() == null
                        || this.wumpusDeadMO == null
                        || this.wumpusDeadMO.getI() == null
        ) {
            return;
        }

        if (((AgentStatusSensor.AgentStatus) this.agentStatusMO.getI()).isAlive() == 0) {
            return;
        }


        INDArray inputExample = Nd4j.create(getInputAsNumericRepresentation());
        INDArray outputPredicted = loadedModel.output(inputExample);

        // Encontrar a ação com maior probabilidade
        int maxIndex = Nd4j.argMax(outputPredicted, 1).getInt(0);
        System.out.println("Ação predita: " + maxIndex); // 0 = GRAB, 1 = MOVE NORTH, etc.

        String predictedAction = actions[maxIndex];

        System.out.println("Ação predita: " + predictedAction);

        this.nextActionMO.setI(predictedAction);
    }

    private double[][] getInputAsNumericRepresentation() {
        int coordinateX = ((AgentStatusSensor.AgentStatus) this.agentStatusMO.getI()).coordinateX();
        int coordinateY = ((AgentStatusSensor.AgentStatus) this.agentStatusMO.getI()).coordinateY();
        int isAlive = ((AgentStatusSensor.AgentStatus) this.agentStatusMO.getI()).isAlive();
        int hasGold = ((AgentStatusSensor.AgentStatus) this.agentStatusMO.getI()).hasGold();
        int hasArrow = ((AgentStatusSensor.AgentStatus) this.agentStatusMO.getI()).hasArrow();
        int isWumpusAlive = (boolean) this.wumpusDeadMO.getI() ? 0 : 1;
        int breeze = (boolean) this.breezeMO.getI() ? 1 : 0;
        int stench = (boolean) this.stenchMO.getI() ? 1 : 0;
        int glitter = (boolean) this.glitterMO.getI() ? 1 : 0;
        int impactValue = (boolean) this.impactMO.getI() ? 1: 0;

        return new double[][]{
                {
                        coordinateX,
                        coordinateY,
                        isAlive,
                        hasGold,
                        hasArrow,
                        isWumpusAlive,
                        breeze,
                        stench,
                        glitter,
                        impactValue
                }
        };
    }
}
