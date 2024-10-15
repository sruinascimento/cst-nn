package br.com.rsfot.meca;

import br.com.rsfot.socket.WumpusConnectionManager;
import br.com.rsfot.meca.system1.motor.AgentActuator;
import br.com.rsfot.meca.system1.sensory.*;
import br.com.rsfot.meca.system2.learning.QLearningCodelet;
import br.unicamp.cst.core.entities.MemoryObject;
import br.unicamp.meca.mind.MecaMind;
import br.unicamp.meca.system1.codelets.*;

import java.util.ArrayList;
import java.util.List;

public class AgentMecaMind {

    public static void turnOnMecaMind(WumpusConnectionManager wumpusConnectionManager) {


        MecaMind mind = new MecaMind();



        //Declare System 1 Elements
        List<SensoryCodelet> sensoryCodelets = new ArrayList<>();
        List<MotorCodelet> motorCodelets = new ArrayList<>();


//        Declare the memory objects
        MemoryObject stenchMO = mind.createMemoryObject("STENCH_MO");
        MemoryObject breezeMO = mind.createMemoryObject("BREEZE_MO");
        MemoryObject glitterMO = mind.createMemoryObject("GLITTER_MO");
        MemoryObject impactMO = mind.createMemoryObject("IMPACT_MO");
        MemoryObject agentStatusMO = mind.createMemoryObject("AGENT_STATUS_MO");
        MemoryObject nextActionMO = mind.createMemoryObject("NEXT_ACTION_MO");
        MemoryObject wumpusDeadMO = mind.createMemoryObject("WUMPUS_DEAD_MO");


        //Declare and create the sensors
        StenchSensor stenchSensor = new StenchSensor("STENCH_SENSOR", wumpusConnectionManager);
        stenchSensor.addOutput(stenchMO);
        sensoryCodelets.add(stenchSensor);

        BreezeSensor breezeSensor = new BreezeSensor("BREEZE_SENSOR", wumpusConnectionManager);
        breezeSensor.addOutput(breezeMO);
        sensoryCodelets.add(breezeSensor);

        GlitterSensor glitterSensor = new GlitterSensor("GLITTER_SENSOR", wumpusConnectionManager);
        glitterSensor.addOutput(glitterMO);
        sensoryCodelets.add(glitterSensor);

        ImpactSensor impactSensor = new ImpactSensor("IMPACT_SENSOR", wumpusConnectionManager);
        impactSensor.addOutput(impactMO);
        sensoryCodelets.add(impactSensor);


        AgentStatusSensor agentStatusSensor = new AgentStatusSensor("AGENT_STATUS_SENSOR", wumpusConnectionManager);
        agentStatusSensor.addOutput(agentStatusMO);
        sensoryCodelets.add(agentStatusSensor);

        WumpusDeadSensor wumpusDeadSensor = new WumpusDeadSensor("WUMPUS_DEAD_SENSOR", wumpusConnectionManager);
        wumpusDeadSensor.addOutput(wumpusDeadMO);
        sensoryCodelets.add(wumpusDeadSensor);



        QLearningCodelet qLearningCodelet = new QLearningCodelet();
        qLearningCodelet.addInput(agentStatusMO);
        qLearningCodelet.addInput(breezeMO);
        qLearningCodelet.addInput(glitterMO);
        qLearningCodelet.addInput(impactMO);
        qLearningCodelet.addInput(stenchMO);
        qLearningCodelet.addInput(wumpusDeadMO);
        qLearningCodelet.addOutput(nextActionMO);




        //Declare and create the actuators
        AgentActuator agentActuator = new AgentActuator("AGENT_ACTUATOR", wumpusConnectionManager);
//        agentActuator.addInput(nextActionMO);
        motorCodelets.add(agentActuator);


        mind.insertCodelet(qLearningCodelet);
        mind.setSensoryCodelets(sensoryCodelets);
        mind.setMotorCodelets(motorCodelets);

        mind.start();

    }

    public static void main(String[] args) {
        try {

            WumpusConnectionManager wumpusConnectionManager = new WumpusConnectionManager("localhost", 7373);

            if (wumpusConnectionManager == null) {
                throw new RuntimeException("Can not connect to the Wumpus World server.");
            }

            AgentMecaMind.turnOnMecaMind(wumpusConnectionManager);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
