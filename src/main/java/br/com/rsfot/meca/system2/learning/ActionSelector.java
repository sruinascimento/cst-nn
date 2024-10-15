package br.com.rsfot.meca.system2.learning;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class ActionSelector {
    private final Map<List<Integer>, Map<String, Double>> qTable;
    private final List<String> possibleActions;
    private final Random random;

    public ActionSelector(Map<List<Integer>, Map<String, Double>> qTable, List<String> possibleActions) {
        this.qTable = qTable;
        this.possibleActions = possibleActions;
        this.random = new Random();
    }

    public String chooseAction(List<Integer> state) {
        Map<String, Double> actions = qTable.get(state);
        if (actions == null || actions.isEmpty()) {
            // Se não houver ações para o estado, escolher uma ação aleatória
            return possibleActions.get(random.nextInt(possibleActions.size()));
        }
        return actions.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(possibleActions.get(random.nextInt(possibleActions.size())));
    }
}