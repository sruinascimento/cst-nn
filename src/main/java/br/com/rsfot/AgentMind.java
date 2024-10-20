package br.com.rsfot;

import br.com.rsfot.game.GameWumpus;
import br.com.rsfot.system1.motor.AgentActuator;
import br.com.rsfot.system1.sensory.*;
import br.com.rsfot.system2.learning.QLearningCodelet;
import br.unicamp.cst.core.entities.MemoryObject;
import br.unicamp.meca.mind.MecaMind;

public class AgentMind extends MecaMind {

    public AgentMind(GameWumpus gameWumpus) {
        //Declare the memory objects
        MemoryObject stenchMO = this.createMemoryObject("STENCH_MO");
        MemoryObject breezeMO = this.createMemoryObject("BREEZE_MO");
        MemoryObject glitterMO = this.createMemoryObject("GLITTER_MO");
        MemoryObject impactMO = this.createMemoryObject("IMPACT_MO");
        MemoryObject agentStatusMO = this.createMemoryObject("AGENT_STATUS_MO");
        MemoryObject nextActionMO = this.createMemoryObject("NEXT_ACTION_MO");
        MemoryObject wumpusDeadMO = this.createMemoryObject("WUMPUS_DEAD_MO");


        //Declare and create the sensors
        StenchSensor stenchSensor = new StenchSensor("STENCH_SENSOR", gameWumpus);
        stenchSensor.addOutput(stenchMO);
        insertCodelet(stenchSensor);

        BreezeSensor breezeSensor = new BreezeSensor("BREEZE_SENSOR", gameWumpus);
        breezeSensor.addOutput(breezeMO);
        insertCodelet(breezeSensor);

        GlitterSensor glitterSensor = new GlitterSensor("GLITTER_SENSOR", gameWumpus);
        glitterSensor.addOutput(glitterMO);
        insertCodelet(glitterSensor);

        ImpactSensor impactSensor = new ImpactSensor("IMPACT_SENSOR", gameWumpus);
        impactSensor.addOutput(impactMO);
        insertCodelet(impactSensor);


        WumpusDeadSensor wumpusDeadSensor = new WumpusDeadSensor("WUMPUS_DEAD_SENSOR", gameWumpus);
        wumpusDeadSensor.addOutput(wumpusDeadMO);
        insertCodelet(wumpusDeadSensor);


        AgentStatusSensor agentStatusSensor = new AgentStatusSensor("AGENT_STATUS_SENSOR", gameWumpus);
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
        AgentActuator agentActuator = new AgentActuator("AGENT_ACTUATOR", gameWumpus);
        agentActuator.addInput(nextActionMO);
        insertCodelet(agentActuator);

    }

    public static void main(String[] args) {

        GameWumpus environment = new GameWumpus();

        AgentMind agentMind = new AgentMind(environment);
        agentMind.start();
        agentMind.start();

    }

}
