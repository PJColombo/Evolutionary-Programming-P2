package application.model.p1.model.genetic_algorithm.selection_algorithms.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import application.model.p1.model.genetic_algorithm.selection_algorithms.SelectionAlgorithm;
import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;

public class RankingSelection implements SelectionAlgorithm {
	
	private final double BETA = 1.5;
	private boolean maximize;

	public RankingSelection(Boolean maximize) {
		if(maximize == null)
			this.maximize = false;
		else
			this.maximize = maximize;
	}
	@Override
	public <T> List<? extends Chromosome<? extends Gene<T>>> selection(List<? extends Chromosome<? extends Gene<T>>> population) {
		List<Chromosome<? extends Gene<T>>> newPopulation = new ArrayList<>(population.size());
		ArrayList<Double> fitnessSegments = this.rankPopulation(population.size());
		double entireSegment = fitnessSegments.get(fitnessSegments.size() - 1);
		Collections.sort(population); 
		if(maximize)
			Collections.reverse(population);

		newPopulation.add(population.get(0)); newPopulation.add(population.get(1));
		
		while(newPopulation.size() < population.size()) {
			double p = ThreadLocalRandom.current().nextDouble() * entireSegment;
			if(p < fitnessSegments.get(0))
				newPopulation.add(population.get(0));
			else {
				for(int i = 1; i < newPopulation.size(); i++) {
					if(p > fitnessSegments.get(i - 1) && p <= fitnessSegments.get(i)) 
						newPopulation.add(population.get(i));
				}
			}
		}
		return newPopulation;
	}

	private ArrayList<Double> rankPopulation(int popSize) {
		ArrayList<Double> fitnessSegments = new ArrayList<>();
		for(int i = 0; i < popSize; i++) {
			double probOfIth = (double) i / popSize;
			probOfIth = probOfIth * 2 * (BETA - 1);
			probOfIth = BETA - probOfIth;
			probOfIth = (double) probOfIth * ((double) 1 / popSize);
			if(i != 0)
				fitnessSegments.add(fitnessSegments.get(i - 1) + probOfIth);
			else
				fitnessSegments.add(probOfIth);
		}
		return fitnessSegments;
	}
}
