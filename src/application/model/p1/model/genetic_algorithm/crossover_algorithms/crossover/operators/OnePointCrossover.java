package application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.operators;

public class OnePointCrossover extends MultiPointCrossover {

	public OnePointCrossover(double crossoverProbability) {
		super(crossoverProbability, 1);
	}
	
}