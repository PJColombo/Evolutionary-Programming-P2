package application.model.p1.model.genetic_algorithm.selection_algorithms.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import application.model.p1.model.genetic_algorithm.selection_algorithms.SelectionAlgorithm;
import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;


public class StochasticUniversalSelection implements SelectionAlgorithm {

	@Override
	public <T> List<? extends Chromosome<? extends Gene<T>>> selection(List<? extends Chromosome<? extends Gene<T>>> population) {
		int popSize = population.size();
		List<Chromosome<? extends Gene<T>>> newPopulation = new ArrayList<>(popSize);
		List<Integer> survivors = new ArrayList<>(popSize);
		double survivorProbability, probability = ThreadLocalRandom.current().nextDouble();
		int survPos;
		for(int i = 1; i <= population.size(); i++) {
			survPos = 0; 
			survivorProbability = (probability + i - 1) / popSize;
			while((survivorProbability >  population.get(survPos).getAccuScore()) &&
					(survPos < popSize))
				survPos++;
			survivors.add(survPos);
			newPopulation.add(population.get(survPos).clone());
		}

		return newPopulation;
	}

}
