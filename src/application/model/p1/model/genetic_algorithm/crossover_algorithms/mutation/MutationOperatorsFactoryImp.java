package application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation;

import application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation.operators.ConventionalMutation;
import application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation.operators.InversionMutation;
import application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation.operators.SwapMutation;
import application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation.operators.evolutive_tsp.ExchangeMutation;
import application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation.operators.evolutive_tsp.HeuristicMutation;
import application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation.operators.evolutive_tsp.InsertionMutation;
import application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation.operators.evolutive_tsp.ReversalMutation;
import application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation.operators.evolutive_tsp.TSPSwapMutation;

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
			case "reversal":
				return new ReversalMutation(mutationProbability);
			case "tsp_swap":
				return new TSPSwapMutation(mutationProbability);
			case "ins_swap":
				return new InsertionMutation(mutationProbability);
			case "hrt_swap":
				return new HeuristicMutation(mutationProbability);
			case "exc_swap":
				return new ExchangeMutation(mutationProbability);
			default:
				return new ConventionalMutation(mutationProbability);
		}

	}
}
