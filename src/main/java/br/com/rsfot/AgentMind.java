package br.com.rsfot;

import br.com.rsfot.game.CaveMatrix;
import br.com.rsfot.game.GameWumpus;
import br.com.rsfot.system1.motor.AgentActuator;
import br.com.rsfot.system1.sensory.*;
import br.com.rsfot.system2.learning.QLearningCodelet;
import br.com.rsfot.training.AgentQLearningCoach;
import br.com.rsoft.domain.Environment;
import br.unicamp.cst.core.entities.MemoryObject;
import br.unicamp.meca.mind.MecaMind;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        Environment environment = new Environment(CaveMatrix.SECOND_CAVE.getCave());


        // Train the agent
        double alpha = 0.1;
        double gamma = 0.99;
        double epsilon = 1.0;
        double epsilonDecay = 0.001;
        int numberOfEpisodes = 2000;
        AgentQLearningCoach agentCoach = new AgentQLearningCoach(alpha, gamma, epsilon, epsilonDecay);

        // Train the agent and save the Q-table
        String episodesReportFileName = createEpisodesReportFileName(alpha, gamma, epsilon, epsilonDecay, numberOfEpisodes);
        String qTableFileName = createQTableFileName(alpha, gamma, epsilon, epsilonDecay, numberOfEpisodes);
        agentCoach.train(environment, numberOfEpisodes, episodesReportFileName);
        try {
            agentCoach.saveQTableDat(qTableFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        GameWumpus game = new GameWumpus(CaveMatrix.SECOND_CAVE.getCave());
//        AgentMind agentMind = new AgentMind(game);

    }


    private static String createEpisodesReportFileName(double alpha, double gamma, double epsilon, double epsilonDecay, int numberOfEpisodes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        return String.format("train_second_cave/train_%s_episodesReport_alpha%.2f_gamma%.2f_epsilon%.2f_epsilonDecay%.2f_%d.csv",
                timestamp, alpha, gamma, epsilon, epsilonDecay, numberOfEpisodes);
    }

    private static String createQTableFileName(double alpha, double gamma, double epsilon, double epsilonDecay, int numberOfEpisodes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        return String.format("train_second_cave/train_%s_qTable4x4_alpha%.2f_gamma%.2f_epsilon%.2f_epsilonDecay%.2f_episodes_%d.dat",
                timestamp, alpha, gamma, epsilon, epsilonDecay, numberOfEpisodes);
    }

}
