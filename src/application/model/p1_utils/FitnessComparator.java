package application.model.p1_utils;

import java.util.Comparator;
import java.util.List;

import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;

public class FitnessComparator implements Comparator<Integer> {

	private  List<? extends Chromosome<? extends Gene<?>>> population;
	
	public FitnessComparator(List<? extends Chromosome<? extends Gene<?>>> population) {
		this.population = population;
	}

	@Override
	public int compare(Integer o1, Integer o2) {
		return Double.compare(population.get(o1).getNormalizedFitness(), population.get(o2).getNormalizedFitness());		
	}
	

}