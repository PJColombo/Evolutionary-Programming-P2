package application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation.operators.evolutive_tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation.MutationOperator;
import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;

public class ReversalMutation extends MutationOperator {

	public ReversalMutation(double mutationProbability) {
		super(mutationProbability);
	}

	@Override
	public <T> Chromosome<? extends Gene<T>> mutation(Chromosome<? extends Gene<T>> c1) {
		int cp1, cp2, aux, segmentIndex;
		double prob;
		List<T> segment = new ArrayList<>(), alleles;
		List<? extends Gene<T>> genes = c1.getGenes();
		
		for(int g = 0; g < genes.size(); g++) {		
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
		
				alleles = c1.getGenes().get(g).getAlleles();
				segment = alleles.subList(cp1, cp2 + 1);
				Collections.reverse(segment);
				
				segmentIndex = 0;
				for(int i = cp1; i <= cp2; i++) {
					alleles.set(i, segment.get(segmentIndex));
					segmentIndex++;
				}
				
			}
		}
		return c1;
	}

}
