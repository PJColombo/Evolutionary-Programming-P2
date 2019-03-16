package application;

import application.model.p1.model.genetic_algorithm.GeneticAlgorithm;

public class DebugMain {

	public static void main(String[] args) {
		GeneticAlgorithm ge = new GeneticAlgorithm(null, "TSP", 10, 0, null, null, 0, null, 0, 0, false);

		
		ge.execute();
	}

}
