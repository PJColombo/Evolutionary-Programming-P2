package application.model.p1.model.genetic_algorithm.selection_algorithms.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import application.model.p1.model.genetic_algorithm.selection_algorithms.SelectionAlgorithm;
import application.model.p1.model.genetic_algorithm.selection_algorithms.TournamentType;
import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;


public class TournamentSelection implements SelectionAlgorithm {
	private int participants = 3;
	private final double PROBABILITY_THRESHOLD = 0.5;
	
	private TournamentType type;
	
	
	public TournamentSelection(TournamentType type, int participants) {
		super();
		this.type = type;
	}

	@Override
	public <T> List<? extends Chromosome<? extends Gene<T>>> selection(List<? extends Chromosome<? extends Gene<T>>> population) {
		List<Chromosome<? extends Gene<T>>> newPopulation = new ArrayList<>(population.size());
		Integer selectedSolution;
		List<Integer> survivorsPos = new ArrayList<>(this.participants);
		for (int i = 0; i < population.size(); i++) {
			survivorsPos.clear();
			for (int j = 0; j < this.participants; j++) {
				selectedSolution = ThreadLocalRandom.current().nextInt(0, population.size());
				//Keep generating numbers until it finds a new random value.
				while(survivorsPos.contains(selectedSolution)) 
					selectedSolution = ThreadLocalRandom.current().nextInt(0, population.size());
				survivorsPos.add(selectedSolution);
				
			}
			//Sort survivors index list by fitness so we can choose a winner. 
			Collections.sort(survivorsPos, new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					return Double.compare(population.get(o1).getFitness(),population.get(o2).getFitness());
				}
				
			});
			if(this.type == TournamentType.DETERMINISTIC) {
				newPopulation.add(population.get(survivorsPos.get(0)));		
			}
			else if(this.type == TournamentType.PROBABILISTIC) {
				double r = ThreadLocalRandom.current().nextDouble();
				if(r > this.PROBABILITY_THRESHOLD)
					newPopulation.add(population.get(survivorsPos.get(0)));
				else
					//Take solution with lowest fitness value.
					newPopulation.add(population.get(survivorsPos.get(survivorsPos.size() - 1)));
			}
		}
		return newPopulation;
	}
}
