package application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.operators.evolutive_tsp;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

import application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.CrossoverOperator;
import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;
import application.model.p1_utils.Pair; 

public class PartiallyMappedCrossover extends CrossoverOperator {

	public PartiallyMappedCrossover(double crossoverProbability) {
		super(crossoverProbability);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Pair<? extends Chromosome<? extends Gene<T>>, ? extends Chromosome<? extends Gene<T>>> chromosomeCrossover(
			Chromosome<? extends Gene<T>> parent1, Chromosome<? extends Gene<T>> parent2) {
		
		Pair<Chromosome<? extends Gene<T>>, Chromosome<? extends Gene<T>>> childChromosomes = new Pair<>();
		List<T> segment1 = new ArrayList<>(), segment2 = new ArrayList<>();
		List<Gene<T>> parentGenes1 = (List<Gene<T>>) parent1.getGenes(), parentGenes2 = (List<Gene<T>>) parent2.getGenes(), 
				childGenes1 = new ArrayList<Gene<T>>(parent1.getGenes().size()), childGenes2 = new ArrayList<Gene<T>>(parent1.getGenes().size());
		List<T> parentAlleles1, parentAlleles2, childAlleles1, childAlleles2;
		int cp1, cp2, aux;
		int ind;
		//Generate crosspoints sections.
		cp1 = ThreadLocalRandom.current().nextInt(1, parent1.getChromosomeLength());
		cp2 = ThreadLocalRandom.current().nextInt(1, parent1.getChromosomeLength());
		while(cp2 == cp1)
			cp2 = ThreadLocalRandom.current().nextInt(1, parent1.getChromosomeLength());
		if(cp1 > cp2) {
			aux = cp1;
			cp1 = cp2;
			cp2 = aux;
		}
		segment1 = parentGenes2.get(0).getAlleles().subList(cp1, cp2 + 1);
		segment2 = parentGenes1.get(0).getAlleles().subList(cp1, cp2 + 1);
		
		for(int i = 0; i < parentGenes1.size(); i++) {
			parentAlleles1 = parentGenes1.get(i).getAlleles();
			parentAlleles2 = parentGenes2.get(i).getAlleles();
			childAlleles1 = new ArrayList<>(parentAlleles1.size());
			childAlleles2 = new ArrayList<>(parentAlleles2.size());			
			for(int j = 0; j < parentAlleles1.size(); j++) {
				if(j >= cp1 && j <= cp2) {
					childAlleles1.add(parentAlleles1.get(j));
					childAlleles2.add(parentAlleles2.get(j));
				}
				else {
					ind = segment1.indexOf(parentAlleles1.get(j));
					if(ind > -1)
						childAlleles1.add(segment2.get(ind));
					else
						childAlleles1.add(parentAlleles1.get(j));
					ind = segment2.indexOf(parentAlleles2.get(j));
					if(ind > -1)
						childAlleles2.add(segment1.get(ind));
					else
						childAlleles2.add(parentAlleles2.get(j));					
				}
			}
			childGenes1.add(parentGenes1.get(i).createGene(childAlleles1));
			childGenes2.add(parentGenes2.get(i).createGene(childAlleles2));
		}
		
		childChromosomes.setLeftElement(parent1.createChildren(childGenes1));
		childChromosomes.setRightElement(parent2.createChildren(childGenes2));
		
		return childChromosomes;
	}

}
