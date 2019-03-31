package application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.operators.evolutive_tsp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

import application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.CrossoverOperator;
import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;
import application.model.p1_utils.Pair;

public class PriorityOrderCrossover extends CrossoverOperator {

	public PriorityOrderCrossover(double crossoverProbability) {
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
			
		TreeSet<Integer> posInParent1 = new TreeSet<Integer>();
		TreeSet<Integer> posInParent2 = new TreeSet<Integer>();
		TreeSet<Integer> helper = new TreeSet<Integer>();
		List<T> childAlleles1 = new ArrayList<>(parent2.getGenes().get(0).getAlleles());
		List<T> childAlleles2 = new ArrayList<>(parent1.getGenes().get(0).getAlleles());
		List<T> allelesParent1 = new ArrayList<>(parent1.getGenes().get(0).getAlleles());
		List<T> allelesParent2 = new ArrayList<>(parent2.getGenes().get(0).getAlleles());
		List<T> valuesChild1 = new ArrayList<>();
		List<T> valuesChild2 = new ArrayList<>();
		int i = 1, j = 0, pos, randomPos = ThreadLocalRandom.current().nextInt(1, parent1.getChromosomeLength() + 1);
		
		while(i < randomPos){
			pos = ThreadLocalRandom.current().nextInt(0, parent1.getChromosomeLength() - 1);
			if(helper.add(pos)) { 
				valuesChild1.add(allelesParent1.get(pos));
				valuesChild2.add(allelesParent2.get(pos));
				i++;
			}
		}
		
		Iterator<T> valuesIter1 = valuesChild1.iterator();
		Iterator<T> valuesIter2 = valuesChild2.iterator();
	    while (valuesIter1.hasNext() && valuesIter2.hasNext()) {
	    	posInParent2.add(allelesParent2.indexOf(valuesIter1.next()));
	    	posInParent1.add(allelesParent1.indexOf(valuesIter2.next()));
	    }
	    
	    Iterator<Integer> posIter1 = posInParent1.iterator();
	    Iterator<Integer> posIter2 = posInParent2.iterator();
	    while(posIter1.hasNext() && posIter2.hasNext()) {
	    	childAlleles1.set(posIter2.next(), valuesChild1.get(j));
	    	childAlleles2.set(posIter1.next(), valuesChild2.get(j));
	    	j++;
	    }
	    
	    helper.clear();
	    posInParent1.clear();
	    posInParent2.clear();
	    valuesChild1.clear();
		valuesChild2.clear();
		
	    childGenes1.add(parentGenes1.get(0).createGene(childAlleles1));
		childGenes2.add(parentGenes2.get(0).createGene(childAlleles2));
		childChromosomes.setLeftElement(parent1.createChildren(childGenes1));
		childChromosomes.setRightElement(parent2.createChildren(childGenes2));
		
		return childChromosomes;
	}

}
