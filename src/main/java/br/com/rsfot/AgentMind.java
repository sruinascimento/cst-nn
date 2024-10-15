package br.com.rsfot;

import br.com.rsfot.socket.WumpusConnectionManager;
import br.com.rsfot.system1.motor.AgentActuator;
import br.com.rsfot.system1.sensory.*;
import br.com.rsfot.system2.learning.QLearningCodelet;
import br.unicamp.cst.core.entities.MemoryObject;
import br.unicamp.meca.mind.MecaMind;

public class AgentMind extends MecaMind {

    public AgentMind(WumpusConnectionManager wumpusConnectionManager) {
        //Declare the memory objects
        MemoryObject stenchMO = createMemoryObject("STENCH_MO");
        MemoryObject breezeMO = createMemoryObject("BREEZE_MO");
        MemoryObject glitterMO = createMemoryObject("GLITTER_MO");
        MemoryObject impactMO = createMemoryObject("IMPACT_MO");
        MemoryObject agentStatusMO = createMemoryObject("AGENT_STATUS_MO");
        MemoryObject nextActionMO = createMemoryObject("NEXT_ACTION_MO");
        MemoryObject wumpusDeadMO = createMemoryObject("WUMPUS_DEAD_MO");


        //Declare and create the sensors
        StenchSensor stenchSensor = new StenchSensor("STENCH_SENSOR", wumpusConnectionManager);
        stenchSensor.addOutput(stenchMO);
        insertCodelet(stenchSensor);

        BreezeSensor breezeSensor = new BreezeSensor("BREEZE_SENSOR", wumpusConnectionManager);
        breezeSensor.addOutput(breezeMO);
        insertCodelet(breezeSensor);

        GlitterSensor glitterSensor = new GlitterSensor("GLITTER_SENSOR", wumpusConnectionManager);
        glitterSensor.addOutput(glitterMO);
        insertCodelet(glitterSensor);

        ImpactSensor impactSensor = new ImpactSensor("IMPACT_SENSOR", wumpusConnectionManager);
        impactSensor.addOutput(impactMO);
        insertCodelet(impactSensor);


        WumpusDeadSensor wumpusDeadSensor = new WumpusDeadSensor("WUMPUS_DEAD_SENSOR", wumpusConnectionManager);
        wumpusDeadSensor.addOutput(wumpusDeadMO);
        insertCodelet(wumpusDeadSensor);


        AgentStatusSensor agentStatusSensor = new AgentStatusSensor("AGENT_STATUS_SENSOR", wumpusConnectionManager);
        agentStatusSensor.addOutput(agentStatusMO);
        insertCodelet(agentStatusSensor);


        //Declare and create the learning codelet
        QLearningCodelet qLearningCodelet = new QLearningCodelet();
        qLearningCodelet.addInput(agentStatusMO);
        qLearningCodelet.addInput(breezeMO);
        qLearningCodelet.addInput(glitterMO);
        qLearningCodelet.addInput(impactMO);
        qLearningCodelet.addInput(stenchMO);
        qLearningCodelet.addInput(wumpusDeadMO);
        qLearningCodelet.addOutput(nextActionMO);
        insertCodelet(qLearningCodelet);


        //Declare and create the actuators
        AgentActuator agentActuator = new AgentActuator("AGENT_ACTUATOR", wumpusConnectionManager);
        agentActuator.addInput(nextActionMO);
        insertCodelet(agentActuator);


        this.start();

    }

    public static void main(String[] args) {
        try {

            WumpusConnectionManager wumpusConnectionManager = new WumpusConnectionManager("localhost", 7373);

            if (wumpusConnectionManager == null) {
                throw new RuntimeException("Can not connect to the Wumpus World server.");
            }

            AgentMind agentMind = new AgentMind(wumpusConnectionManager);
            agentMind.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
