package application;

import application.model.p1.model.genetic_algorithm.GeneticAlgorithm;

public class DebugMain {

	public static void main(String[] args) {
		GeneticAlgorithm ge = new GeneticAlgorithm(null, "TSP", 27, 100, "roullete", "pmx", 0.6, "hrt_swap", 0.05, 0.0, false, "roullete");
		
		ge.execute();
	}

}
