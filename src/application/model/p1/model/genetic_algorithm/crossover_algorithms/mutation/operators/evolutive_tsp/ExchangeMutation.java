package application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation.operators.evolutive_tsp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation.MutationOperator;
import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;

public class ExchangeMutation extends  MutationOperator{

	public ExchangeMutation(double mutationProbability) {
		super(mutationProbability);
	}

	@Override
	public <T> Chromosome<? extends Gene<T>> mutation(Chromosome<? extends Gene<T>> c1) {
		@SuppressWarnings("unchecked")
		List<Gene<T>> parentGenes = (List<Gene<T>>) c1.getGenes(),
				childGenes = new ArrayList<Gene<T>>();
		List<T> alleles = new ArrayList<>(c1.getGenes().get(0).getAlleles());
		int i = 0;
		double prob;
		T aux; 
		
		while(i < alleles.size() - 1) {
			prob = ThreadLocalRandom.current().nextDouble();
			if(prob < this.mutationProbability) {
				aux = alleles.get(i);
				alleles.set(i, alleles.get(i + 1));
				alleles.set(i + 1, aux);
				i += 2;
			}else 
				i++;
		}
		
		childGenes.add(parentGenes.get(0).createGene(alleles));
		return c1.createChildren(childGenes);
	}

}
