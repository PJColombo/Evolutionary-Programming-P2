package application.model.p1.model.genetic_algorithm.selection_algorithms.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.model.p1.model.genetic_algorithm.selection_algorithms.SelectionAlgorithm;
import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;
import application.model.p1_utils.RoundDecimal;

public class RestSelection implements SelectionAlgorithm {

	private int participants; 
	private SelectionAlgorithm selAlg;
	private boolean maximize;
	/*
	 * Participants Percentage that it should take from population in case
	 * it doesn't receive participants integer variable.
	 * */
	private final double PARTICIPANTS_PERCENTAGE = 0.3;
	/*
	 * Maximum number of copies which the new population can have.
	 * The remaining individuals will be selected using the specified 
	 * selection algorithm.
	 * */
	private final double MAX_COPIES_PERCENTAGE = 0.6;
	
	public RestSelection(int participants, SelectionAlgorithm selAlg, Boolean maximize) {
		this.participants = participants;
		this.selAlg = selAlg;
		this.maximize = maximize == null ? false : maximize;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<? extends Chromosome<? extends Gene<T>>> selection(
			List<? extends Chromosome<? extends Gene<T>>> population) {
		List<Chromosome<? extends Gene<T>>> newPopulation = new ArrayList<>(population.size());
		List<Chromosome<? extends Gene<T>>> remainingPopulation = new ArrayList<>();
		int numberCopies;
		boolean isFilled = false;
		Chromosome<? extends Gene<T>> c;
		Collections.sort(population);
		if(maximize)
			Collections.reverse(population);
		if(this.participants == 0)
			this.participants = (int) Math.floor(this.PARTICIPANTS_PERCENTAGE * population.size());
		
		for(int i = 0; i < this.participants && !isFilled; i++) {
			c = population.get(i);
			numberCopies = (int) RoundDecimal.roundHalfDown(c.getScore() * this.participants * (this.participants * 3));
			for(int j = 0; j < numberCopies && !isFilled; j++) {
				newPopulation.add(c.clone());
				isFilled = newPopulation.size() == this.MAX_COPIES_PERCENTAGE * population.size();
			}
			
		}
		remainingPopulation = (List<Chromosome<? extends Gene<T>>>) population.subList(newPopulation.size(), population.size());
		//Use another selection algorithm for the rest of the individuals. 
		newPopulation.addAll(selAlg.selection(remainingPopulation));
		
		return newPopulation;
	}
	
}
