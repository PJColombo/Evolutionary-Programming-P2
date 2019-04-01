package application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover;

import application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.operators.MultiPointCrossover;
import application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.operators.OnePointCrossover;
import application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.operators.UniformCrossover;
import application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.operators.evolutive_tsp.CycleCrossover;
import application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.operators.evolutive_tsp.DiagonalCrossover;
import application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.operators.evolutive_tsp.EdgeRecombinationCrossover;
import application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.operators.evolutive_tsp.OrdinalCrossover;
import application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.operators.evolutive_tsp.PartiallyMappedCrossover;
import application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.operators.evolutive_tsp.PositionOrderCrossover;
import application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.operators.evolutive_tsp.PriorityOrderCrossover;

public class CrossoverOperatorFactoryImp extends CrossoverOperatorFactory {
	
	@Override
	public CrossoverOperator selectAlgorithm(String type, double crossoverProbability, Integer crossPoints, Integer initialFinalCity){
		if(type.equalsIgnoreCase("uniform"))
			return new UniformCrossover(crossoverProbability);
		else if(type.equalsIgnoreCase("multipoint"))
			return new MultiPointCrossover(crossoverProbability, crossPoints);
		else if(type.equalsIgnoreCase("onepoint"))
			return new OnePointCrossover(crossoverProbability);
		//TSP crossover operators 
		else if(type.equalsIgnoreCase("cycle"))
			return new CycleCrossover(crossoverProbability);
		else if(type.equalsIgnoreCase("erx"))
			return new EdgeRecombinationCrossover(crossoverProbability, initialFinalCity);
		else if(type.equalsIgnoreCase("pmx"))
			return new PartiallyMappedCrossover(crossoverProbability);
		else if(type.equalsIgnoreCase("ocx"))
			return new OrdinalCrossover(crossoverProbability);
		else if(type.equalsIgnoreCase("prox"))
			return new PriorityOrderCrossover(crossoverProbability);
		else if(type.equalsIgnoreCase("poox"))
			return new PositionOrderCrossover(crossoverProbability);
		else if(type.equalsIgnoreCase("diagonal"))
			return new DiagonalCrossover(crossoverProbability, initialFinalCity);
		else
			return new OnePointCrossover(crossoverProbability);
		
	}
}
