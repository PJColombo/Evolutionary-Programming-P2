package application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.operators.evolutive_tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.CrossoverOperator;
import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;
import application.model.p1_utils.Pair;

public class CycleCrossover extends CrossoverOperator {

	public CycleCrossover(double crossoverProbability) {
		super(crossoverProbability);
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> Pair<? extends Chromosome<? extends Gene<T>>, ? extends Chromosome<? extends Gene<T>>> chromosomeCrossover(
			Chromosome<? extends Gene<T>> parent1, Chromosome<? extends Gene<T>> parent2) {
		Pair<Chromosome<? extends Gene<T>>, Chromosome<? extends Gene<T>>> childChromosomes = new Pair<>();
		@SuppressWarnings("unchecked")
		List<Gene<T>> parentGenes1 = (List<Gene<T>>) parent1.getGenes(), parentGenes2 = (List<Gene<T>>) parent2.getGenes(), 
				childGenes1 = new ArrayList<Gene<T>>(parent1.getGenes().size()), childGenes2 = new ArrayList<Gene<T>>(parent1.getGenes().size());
		List<T> parentAlleles1, parentAlleles2, childAlleles1, childAlleles2;
		
		for(int i = 0; i < parentGenes1.size(); i++) {
			parentAlleles1 = parentGenes1.get(i).getAlleles();
			parentAlleles2 = parentGenes2.get(i).getAlleles();
			childAlleles1 = new ArrayList<>(parentAlleles1.size());
			childAlleles2 = new ArrayList<>(parentAlleles2.size());			
			Collections.fill(childAlleles1, null);
			Collections.fill(childAlleles2, null);
			
			childAlleles1.add(0, parentAlleles1.get(0));
			childAlleles2.add(0, parentAlleles2.get(0));
			T pos1 = parentAlleles2.get(0), pos2 = parentAlleles1.get(0);
			boolean cycleCompleted1 = false,  cycleCompleted2 = false;				
			while(!cycleCompleted1 || !cycleCompleted2) {
				if(!cycleCompleted1) {						
					if(childAlleles1.get((int) pos1) == null)
						cycleCompleted1 = true;
					else {
						childAlleles1.add((int) pos1, pos1);
						pos1 = childAlleles2.get((int) pos1);
					}
				}
				
				if(!cycleCompleted2) {
					if(childAlleles2.get((int) pos2) == null)
						cycleCompleted2 = true;
					else {
						childAlleles2.add((int) pos2, pos2);
						pos2 = childAlleles1.get((int) pos2);
					}
				}
				
			}
			//Iterate over child alleles and fill remaining empty positions.
			for(int j = 0; j < parentAlleles1.size(); j++) {
				if(childAlleles1.get(j) == null)
					childAlleles1.add(j, parentAlleles2.get(j));
				 if(childAlleles2.get(j) == null)
					 childAlleles2.add(j, parentAlleles1.get(j));
			}
			
			childGenes1.add(parentGenes1.get(i).createGene(childAlleles1));
			childGenes2.add(parentGenes2.get(i).createGene(childAlleles2));
		}
		
		childChromosomes.setLeftElement(parent1.createChildren(childGenes1));
		childChromosomes.setRightElement(parent2.createChildren(childGenes2));
		
		return childChromosomes;
	}

}
