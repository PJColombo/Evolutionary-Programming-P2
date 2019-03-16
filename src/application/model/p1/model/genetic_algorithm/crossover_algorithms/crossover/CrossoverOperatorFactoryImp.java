package application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover;

import application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.operators.MultiPointCrossover;
import application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.operators.OnePointCrossover;
import application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.operators.UniformCrossover;

public class CrossoverOperatorFactoryImp extends CrossoverOperatorFactory {
	
	@Override
	public CrossoverOperator selectAlgorithm(String type, double crossoverProbability, Integer crossPoints){
		if(type.equalsIgnoreCase("uniform"))
			return new UniformCrossover(crossoverProbability);
		else if(type.equalsIgnoreCase("multipoint"))
			return new MultiPointCrossover(crossoverProbability, crossPoints);
		else if(type.equalsIgnoreCase("onepoint"))
			return new OnePointCrossover(crossoverProbability);
		else
			return new OnePointCrossover(crossoverProbability);
		
	}
}
