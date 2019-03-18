package application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation.operators.evolutive_tsp;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation.MutationOperator;
import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;

public class TSPSwapMutation extends MutationOperator {

	public TSPSwapMutation(double mutationProbability) {
		super(mutationProbability);
	}

	@Override
	public <T> Chromosome<? extends Gene<T>> mutation(Chromosome<? extends Gene<T>> c1) {
		int cp1, cp2, aux;
		double prob;
		T elem;
		List<? extends Gene<T>> genes = c1.getGenes();
		List<T> alleles;
		
		for(int i = 0; i < genes.size(); i++) {
			prob = ThreadLocalRandom.current().nextDouble();
			if(prob < this.mutationProbability) {	
				//Generate crosspoints sections.
				cp1 = ThreadLocalRandom.current().nextInt(1, c1.getChromosomeLength());
				cp2 = ThreadLocalRandom.current().nextInt(1, c1.getChromosomeLength());
				while(cp2 == cp1)
					cp2 = ThreadLocalRandom.current().nextInt(1, c1.getChromosomeLength());
				if(cp1 > cp2) {
					aux = cp1;
					cp1 = cp2;
					cp2 = aux;
				}
				
				alleles = c1.getGenes().get(0).getAlleles();
				elem = alleles.get(cp1);
				alleles.set(cp1, alleles.get(cp2));
				alleles.set(cp2, elem);				
			}
		}
		return c1;
	}

}
