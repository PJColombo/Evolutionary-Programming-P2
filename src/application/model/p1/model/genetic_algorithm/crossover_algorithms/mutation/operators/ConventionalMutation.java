package application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation.operators;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation.MutationOperator;
import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;

public class ConventionalMutation extends MutationOperator {
	
	
	
	public ConventionalMutation(double mutationProbability) {
		super(mutationProbability);
	}

	@Override
	public <T> Chromosome<? extends Gene<T>> mutation(Chromosome<? extends Gene<T>> c1) {
		double prob;
		boolean mutate = false;
		int allelesSize;
		@SuppressWarnings("unchecked")
		List<Gene<T>> genes = (List<Gene<T>>) c1.getGenes();
		for (int i = 0; i < genes.size(); i++) {
			allelesSize = genes.get(i).getSize();
			for(int j = 0; j < allelesSize; j++) {
				prob = ThreadLocalRandom.current().nextDouble();
				if(prob < this.mutationProbability) {
					genes.get(i).mutate(j);							
					mutate = true;
				}
			}
		}
		//Need to recalculate some values.
		if(mutate)		
			c1.evaluate();

		return c1;
	}
}
