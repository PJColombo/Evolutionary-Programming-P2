package application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.operators.evolutive_tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
		/*Key --> city, Value --> Parent allele position*/
		HashMap<Integer, Integer> h1, h2;
		for(int i = 0; i < parentGenes1.size(); i++) {
			parentAlleles1 = parentGenes1.get(i).getAlleles();
			parentAlleles2 = parentGenes2.get(i).getAlleles();
			System.out.println("P1 " + parentAlleles1);
			System.out.println("P2: " + parentAlleles2);
			childAlleles1 = new ArrayList<>(parentAlleles1.size());
			childAlleles2 = new ArrayList<>(parentAlleles2.size());	
			
			this.fillList(childAlleles1, parentAlleles1.size());
			this.fillList(childAlleles2, parentAlleles2.size());
			
			h1 = this.buildHashTable(parentAlleles1);
			h2 = this.buildHashTable(parentAlleles2);
			
			childAlleles1.set(0, parentAlleles1.get(0));
			childAlleles2.set(0, parentAlleles2.get(0));
			T pos1 = parentAlleles2.get(0), pos2 = parentAlleles1.get(0);
			boolean cycleCompleted1 = false,  cycleCompleted2 = false;	
			int iter = 0;
			int valuePos;
			while(!cycleCompleted1 || !cycleCompleted2) {
				if(!cycleCompleted1) {	
					valuePos = h1.get((int) pos1);
					if(childAlleles1.get(valuePos) != null)
						cycleCompleted1 = true;
					else {
						childAlleles1.set(valuePos, pos1);
						pos1 = parentAlleles2.get(valuePos);
					}
				}
				
				if(!cycleCompleted2) {
					valuePos = h2.get((int) pos2);
					if(childAlleles2.get(valuePos) != null)
						cycleCompleted2 = true;
					else {
						childAlleles2.set(valuePos, pos2);
						pos2 = parentAlleles1.get(valuePos);
					}
				}
			}
			//Iterate over child alleles and fill remaining empty positions.
			for(int j = 0; j < parentAlleles1.size(); j++) {
				if(childAlleles1.get(j) == null)
					childAlleles1.set(j, parentAlleles2.get(j));
				 if(childAlleles2.get(j) == null)
					 childAlleles2.set(j, parentAlleles1.get(j));
			}
			childGenes1.add(parentGenes1.get(i).createGene(childAlleles1));
			childGenes2.add(parentGenes2.get(i).createGene(childAlleles2));
		}
		
		childChromosomes.setLeftElement(parent1.createChildren(childGenes1));
		childChromosomes.setRightElement(parent2.createChildren(childGenes2));
		
		return childChromosomes;
	}

	private <T> void fillList(List<T> l, int size) {
		for (int i = 0; i < size; i++)
			l.add(null);
	}
	private <T> HashMap<Integer, Integer> buildHashTable(List<T> l) {
		HashMap<Integer, Integer> h = new HashMap<>(l.size());
		for (int i = 0; i < l.size(); i++)
			h.put((int) l.get(i), i);
		return h;
	}
}
