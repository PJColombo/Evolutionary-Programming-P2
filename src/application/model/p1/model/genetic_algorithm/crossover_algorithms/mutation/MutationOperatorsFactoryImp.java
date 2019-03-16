package application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation;

import application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation.operators.ConventionalMutation;
import application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation.operators.InversionMutation;
import application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation.operators.SwapMutation;

public class MutationOperatorsFactoryImp extends MutationOperatorsFactory {
	
	@Override
	public MutationOperator selectAlgorithm(String type, double mutationProbability){
		
		switch(type.toLowerCase()) {
			case "conventional":
				return new ConventionalMutation(mutationProbability);
			case "inversion":
				return new InversionMutation(mutationProbability);
			case "swap":
				return new SwapMutation(mutationProbability);
			default:
				return new ConventionalMutation(mutationProbability);
		}

	}
}
