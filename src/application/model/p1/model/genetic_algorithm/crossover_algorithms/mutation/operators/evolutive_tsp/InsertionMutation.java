package application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation.operators.evolutive_tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation.MutationOperator;
import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;

public class InsertionMutation extends MutationOperator {

	public InsertionMutation(double mutationProbability) {
		super(mutationProbability);
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> Chromosome<? extends Gene<T>> mutation(Chromosome<? extends Gene<T>> c1) {
		
		List<T> alleles = c1.getGenes().get(0).getAlleles();
		double prob;
		int newPos;
		T allele;
		
		for(int a = 0; a < alleles.size(); a++) {		
			prob = ThreadLocalRandom.current().nextDouble();
			if(prob < this.mutationProbability) {
				newPos = ThreadLocalRandom.current().nextInt(0, alleles.size());
				allele = alleles.get(a);
				while(alleles.indexOf(allele) == newPos)
					newPos = ThreadLocalRandom.current().nextInt(0, alleles.size());
				alleles.add(newPos, allele);
				if(newPos > a)
					alleles.remove(a);
				else 
					alleles.remove(a + 1);
			}
		}
		return c1;
	}

}
