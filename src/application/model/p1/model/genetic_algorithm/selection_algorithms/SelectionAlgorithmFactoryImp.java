package application.model.p1.model.genetic_algorithm.selection_algorithms;

import application.model.p1.model.genetic_algorithm.selection_algorithms.algorithms.RankSelection;
import application.model.p1.model.genetic_algorithm.selection_algorithms.algorithms.RouletteSelection;
import application.model.p1.model.genetic_algorithm.selection_algorithms.algorithms.StochasticUniversalSelection;
import application.model.p1.model.genetic_algorithm.selection_algorithms.algorithms.TournamentSelection;

public class SelectionAlgorithmFactoryImp extends SelectionAlgorithmFactory {

	@Override
	public SelectionAlgorithm getSelectionAlgorithm(String algorithm, int participants) {
		
		switch (algorithm.toLowerCase()) {
		case "roulette":
			return new RouletteSelection();
		case "tournament":
			return new TournamentSelection(TournamentType.DETERMINISTIC, participants);
		case "stochastic":
			return new StochasticUniversalSelection();
		case "probabilistic_tournament":
			return new TournamentSelection(TournamentType.PROBABILISTIC, participants);
		case "ranking":
			return new RankSelection();
		default:
			return new RouletteSelection();
		}
	}

}
