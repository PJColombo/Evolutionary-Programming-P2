package application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.operators;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.CrossoverOperator;
import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;
import application.model.p1_utils.Pair;

public class UniformCrossover extends CrossoverOperator {
	
	public UniformCrossover(double crossoverProbability){
		super(crossoverProbability);
	}

	@Override
	public <T> Pair<? extends Chromosome<? extends Gene<T>>, ? extends Chromosome<? extends Gene<T>>> chromosomeCrossover(
			Chromosome<? extends Gene<T>> parent1, Chromosome<? extends Gene<T>> parent2) {
		
		Random r = new Random();
		Pair<Chromosome<? extends Gene<T>>, Chromosome<? extends Gene<T>>> childChromosomes = new Pair<>();
		List<Gene<T>> parentGenes1 = (List<Gene<T>>) parent1.getGenes(), parentGenes2 = (List<Gene<T>>) parent2.getGenes(), 
				childGenes1 = new ArrayList<Gene<T>>(parent1.getGenes().size()), childGenes2 = new ArrayList<Gene<T>>(parent1.getGenes().size());
		List<T> parentAlleles1, parentAlleles2, childAlleles1, childAlleles2;
		
		//Iterate over every gene and swap allele if probability occurred.
		for(int i = 0; i < parentGenes1.size(); i++) {
			parentAlleles1 = parentGenes1.get(i).getAlleles(); parentAlleles2 = parentGenes2.get(i).getAlleles();
			childAlleles1 = new ArrayList<T>(parentAlleles1.size()); childAlleles2 = new ArrayList<T>(parentAlleles2.size());
			for(int j = 0; j < parentGenes1.get(i).getSize(); j++) {
				if(r.nextDouble() < this.crossoverProbability) {
					childAlleles1.add(parentAlleles2.get(j));
					childAlleles2.add(parentAlleles1.get(j));
				}
				else {
					childAlleles1.add(parentAlleles1.get(j));
					childAlleles2.add(parentAlleles2.get(j));
				}
			}
			//Create child genes.
			childGenes1.add(parentGenes1.get(i).createGene(childAlleles1));
			childGenes2.add(parentGenes2.get(i).createGene(childAlleles2));
		}
		
		childChromosomes.setLeftElement(parent1.createChildren(childGenes1));
		childChromosomes.setRightElement(parent2.createChildren(childGenes1));
		
		return childChromosomes;
	}
	
	

}
