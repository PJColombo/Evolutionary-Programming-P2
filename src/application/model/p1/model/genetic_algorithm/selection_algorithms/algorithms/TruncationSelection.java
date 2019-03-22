package application.model.p1.model.genetic_algorithm.selection_algorithms.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.model.p1.model.genetic_algorithm.selection_algorithms.SelectionAlgorithm;
import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;

public class TruncationSelection implements SelectionAlgorithm {
	private double truncPerc = 0.5;
	
	public TruncationSelection(Double truncPerc) {
		if(truncPerc != null && truncPerc > 0.5)
			this.truncPerc = truncPerc;
	}
	
	@Override
	public <T> List<? extends Chromosome<? extends Gene<T>>> selection(
			List<? extends Chromosome<? extends Gene<T>>> population) {
		List<Chromosome<? extends Gene<T>>> newPopulation = new ArrayList<>(population.size());
		int selectionLimit = (int) Math.floor(1 / this.truncPerc), selectionCounter = 0, i = 0;
		boolean isFilled = false;
		Chromosome<? extends Gene<T>> c;
		
		if(population.get(0).isMaximize())
			Collections.reverse(population);
		else
			Collections.sort(population);

		System.out.println("-----------------------");
		for (Chromosome<? extends Gene<T>> c1 : population) {
			System.out.println(c1);
		}
		while(!isFilled) {		
			c = population.get(i);
			while(selectionCounter < selectionLimit && !isFilled) {				
				newPopulation.add(c.clone());
				selectionCounter++;
				isFilled = newPopulation.size() == population.size();
			}
			selectionCounter = 0;
			i++;
		}
		System.out.println(newPopulation.size());
		System.out.println("-----------------------");
		for (Chromosome<? extends Gene<T>> c1 : newPopulation) {
			System.out.println(c1);
		}
		return newPopulation;
	}

}
