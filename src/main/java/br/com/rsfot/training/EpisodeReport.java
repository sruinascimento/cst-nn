package br.com.rsfot.training;

import java.io.Serializable;

public record EpisodeReport(
        int episodeNumber,
        int steps,
        boolean agentWinTheGame,
        boolean agentKilledTheWumpus
) implements Serializable {
}
