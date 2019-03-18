package application;

import application.model.p1.model.genetic_algorithm.GeneticAlgorithm;

public class DebugMain {

	public static void main(String[] args) {
		GeneticAlgorithm ge = new GeneticAlgorithm(null, "TSP", 10, 1, "roullete", "erx", 0.6, "reversal", 0.05, 0.0, false);
		
		ge.execute();
	}

}
