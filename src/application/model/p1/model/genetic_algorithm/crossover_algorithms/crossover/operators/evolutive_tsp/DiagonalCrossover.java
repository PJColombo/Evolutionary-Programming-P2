package application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.operators.evolutive_tsp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.CrossoverOperator;
import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.chromosomes.TSPFunction;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;
import application.model.p1_utils.Pair;

public class DiagonalCrossover extends CrossoverOperator {

	private int initialStartCity;
	public DiagonalCrossover(double crossoverProbability, Integer initialStartCity) {
		super(crossoverProbability);
		if(initialStartCity != null)
			this.initialStartCity = initialStartCity;
		else
			this.initialStartCity = 25;
	}

	@Override
	public <T> Pair<? extends Chromosome<? extends Gene<T>>, ? extends Chromosome<? extends Gene<T>>> chromosomeCrossover(
			Chromosome<? extends Gene<T>> parent1, Chromosome<? extends Gene<T>> parent2) {
		Pair<Chromosome<? extends Gene<T>>, Chromosome<? extends Gene<T>>> childChromosomes = new Pair<>();
		@SuppressWarnings("unchecked")
		List<Gene<T>> parentGenes1 = (List<Gene<T>>) parent1.getGenes(), parentGenes2 = (List<Gene<T>>) parent2.getGenes(), 
				childGenes1 = new ArrayList<Gene<T>>(parent1.getGenes().size()), childGenes2 = new ArrayList<Gene<T>>(parent1.getGenes().size());
		List<T> parentAlleles1, parentAlleles2, childAlleles1, childAlleles2;
		HashSet<Integer> checkList1, checkList2;
		/*Key --> city, Value --> Parent allele position*/
		HashMap<Integer, Integer> h1, h2;
		for(int i = 0; i < parentGenes1.size(); i++) {
			parentAlleles1 = parentGenes1.get(i).getAlleles();
			parentAlleles2 = parentGenes2.get(i).getAlleles();
			childAlleles1 = new ArrayList<>(parentAlleles1.size());
			childAlleles2 = new ArrayList<>(parentAlleles2.size());	
			
			checkList1 = new HashSet<>(parentAlleles1.size()); checkList2 = new HashSet<>(parentAlleles2.size());
			
			h1 = this.buildHashTable(parentAlleles1);
			h2 = this.buildHashTable(parentAlleles2);
			
			for(int k = 0; k < parentAlleles1.size(); k++) {
				//odd position.
				if(k % 2 == 0) {
					if(!checkList1.contains((int) parentAlleles2.get(k))) {
						childAlleles1.add(parentAlleles2.get(k));
						checkList1.add((int) parentAlleles2.get(k));
					}
					if(!checkList2.contains((int) parentAlleles1.get(k))) {
						childAlleles2.add(parentAlleles1.get(k));
						checkList2.add((int) parentAlleles1.get(k));
					}
					
				}
				else {
					if(!checkList1.contains((int) parentAlleles1.get(k))) {
						childAlleles1.add(parentAlleles1.get(k));
						checkList1.add((int) parentAlleles1.get(k));
					}
					if(!checkList2.contains((int) parentAlleles2.get(k))) {
						childAlleles2.add(parentAlleles2.get(k));
						checkList2.add((int) parentAlleles2.get(k));
					}
				}
			}
			//add remaining cities
			//cities + 1 because we need to add citie 27.
			for(int k = 0; k < parentAlleles1.size() + 1; k++) {
				if(k != this.initialStartCity && !checkList1.contains(k))
					childAlleles1.add(parentAlleles1.get(h1.get(k)));
				if(k != this.initialStartCity && !checkList2.contains(k))
					childAlleles2.add(parentAlleles2.get(h2.get(k)));
			}
		
			this.validRoute(childAlleles1);
			this.validRoute(childAlleles2);
			childGenes1.add(parentGenes1.get(i).createGene(childAlleles1));
			childGenes2.add(parentGenes2.get(i).createGene(childAlleles2));
		}
		
		childChromosomes.setLeftElement(parent1.createChildren(childGenes1));
		childChromosomes.setRightElement(parent2.createChildren(childGenes2));
		return childChromosomes;
	}
	
	private <T> HashMap<Integer, Integer> buildHashTable(List<T> l) {
		HashMap<Integer, Integer> h = new HashMap<>(l.size());
		for (int i = 0; i < l.size(); i++)
			h.put((int) l.get(i), i);
		return h;
	}
	
	private <T> void validRoute(List<T> route) {
		boolean valid = true;
		ArrayList<Boolean> cities = new ArrayList<>(28);
		for(int i = 0; i < 28; i++) {
			cities.add(false);
		}
		int i = 0;
		for (T c : route) {
			if(!cities.get((int) c))
					cities.set((int) c, true);
			else {
				System.out.println("Citie " + c + " already exists in pos: " + i);
			}
			i++;
		}
	}
}
