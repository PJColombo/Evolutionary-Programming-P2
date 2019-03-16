package application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation;

import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;

public abstract class MutationOperator {
	protected double mutationProbability; 
	
	
	public MutationOperator(double mutationProbability) {
		super();
		this.mutationProbability = mutationProbability;
	}


	public abstract <T> Chromosome<? extends Gene<T>> mutation(Chromosome<? extends Gene<T>> c1);
}
