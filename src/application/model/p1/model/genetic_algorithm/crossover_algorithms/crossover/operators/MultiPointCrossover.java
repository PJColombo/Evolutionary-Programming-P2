package application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.operators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

import application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.CrossoverOperator;
import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;
import application.model.p1_utils.Pair;


public class MultiPointCrossover extends CrossoverOperator {
	
	private TreeSet<Integer> crossPoints;
	private int crossoverCounter;

	public MultiPointCrossover(double crossoverProbability, Integer crossoverCounter) {
		super(crossoverProbability);
		if(crossoverCounter == null)
			crossoverCounter = 1;
		else
			this.crossoverCounter = crossoverCounter;
		this.crossPoints = new TreeSet<Integer>();
	}
	
	@Override
	public <T> Pair<? extends Chromosome<? extends Gene<T>>, ? extends Chromosome<? extends Gene<T>>> 
		chromosomeCrossover(Chromosome<? extends Gene<T>> parent1, Chromosome<? extends Gene<T>> parent2) {
		
		Pair<Chromosome<? extends Gene<T>>, Chromosome<? extends Gene<T>>> childChromosomes = new Pair<>();
		@SuppressWarnings("unchecked")
		List<Gene<T>> parentGenes1 = (List<Gene<T>>) parent1.getGenes(), parentGenes2 = (List<Gene<T>>) parent2.getGenes(), 
				childGenes1 = new ArrayList<Gene<T>>(parent1.getGenes().size()), childGenes2 = new ArrayList<Gene<T>>(parent1.getGenes().size());
		List<T> parentAlleles1, parentAlleles2, childAlleles1, childAlleles2;
		int chromosomePosCounter = 0;
		boolean isEven = true;
		this.generateCrossPoints(parent1.getChromosomeLength());
		Iterator<Integer> iter = this.crossPoints.iterator();
		int cp1 = iter.hasNext() ? iter.next() : parent1.getChromosomeLength();
		//Iterate over genes.
		for(int i = 0; i < parentGenes1.size(); i++) {
			parentAlleles1 = parentGenes1.get(i).getAlleles(); parentAlleles2 = parentGenes2.get(i).getAlleles();
			childAlleles1 = new ArrayList<T>(parentAlleles1.size()); childAlleles2 = new ArrayList<T>(parentAlleles2.size());
			//Iterate over alleles.
			for(int j = 0; j < parentGenes1.get(i).getSize(); j++) {
				if(chromosomePosCounter >= cp1) {
					cp1 = iter.hasNext() ? iter.next() : parent1.getChromosomeLength();
					isEven = !isEven;
				}
				if(isEven) {
					childAlleles1.add(parentAlleles1.get(j));
					childAlleles2.add(parentAlleles2.get(j));
				}
				//We only swap alleles when we are on odd crosspoint positions.
				else {
					childAlleles1.add(parentAlleles2.get(j));
					childAlleles2.add(parentAlleles1.get(j));							
				}
				chromosomePosCounter++;
			}
			//Create child genes.
			childGenes1.add(parentGenes1.get(i).createGene(childAlleles1));
			childGenes2.add(parentGenes2.get(i).createGene(childAlleles2));
		}	
		
		childChromosomes.setLeftElement(parent1.createChildren(childGenes1));
		childChromosomes.setRightElement(parent2.createChildren(childGenes2));
		
		return childChromosomes;
	}
	
	private void generateCrossPoints(int chromosomeLength){
		this.crossPoints.clear();
		Random r = new Random();
		int cp;
		if(chromosomeLength > 1 && this.crossoverCounter > chromosomeLength)
			this.crossoverCounter = chromosomeLength - 1;
		for(int i = 0; i < this.crossoverCounter; i++) {
			//cp = r.nextInt(chromosomeLength - 1) + 1;
			if(chromosomeLength > 1) {
				cp = ThreadLocalRandom.current().nextInt(1, chromosomeLength);
				while(!this.crossPoints.add(cp))	
					cp = r.nextInt(chromosomeLength - 1) + 1;
			}
			else {
				cp = 1;
				this.crossPoints.add(cp);
			}
		}
	}
}
