package application.model.p1.model.genetic_algorithm.selection_algorithms.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import application.model.p1.model.genetic_algorithm.selection_algorithms.SelectionAlgorithm;
import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;


public class RouletteSelection implements SelectionAlgorithm {

	@Override
	public <T> List<? extends Chromosome<? extends Gene<T>>> selection(List<? extends Chromosome<? extends Gene<T>>> population) {
		List<Chromosome<? extends Gene<T>>> newPopulation = new ArrayList<>(population.size());
		int popSize = population.size();
		double probability;
		int survPos;
		for (int i = 0; i < popSize; i++) {
			probability = ThreadLocalRandom.current().nextDouble();
			survPos = 0;
			while((survPos < popSize) && 
					(probability > population.get(survPos).getAccuScore()))
				survPos++;
			newPopulation.add(population.get(survPos).clone());
		}
		
		return newPopulation;
	}
}