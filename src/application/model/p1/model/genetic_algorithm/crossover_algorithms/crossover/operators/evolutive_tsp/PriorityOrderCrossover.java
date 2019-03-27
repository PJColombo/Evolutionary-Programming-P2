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
		
		HashMap<Integer,Integer> hash = new HashMap<Integer,Integer>();
		TreeSet<Integer> helper = new TreeSet<Integer>();
		List<T> childAlleles1 = new ArrayList<>(parent1.getGenes().get(0).getAlleles());
		List<T> childAlleles2 = new ArrayList<>(parent2.getGenes().get(0).getAlleles());
		List<T> childAlleles1Aux = new ArrayList<>(parent1.getGenes().get(0).getAlleles());
		List<T> childAlleles2Aux = new ArrayList<>(parent2.getGenes().get(0).getAlleles());
		List<T> aux = new ArrayList<>(parent2.getGenes().get(0).getAlleles());
		List<T> values = new ArrayList<>();
		int i = 0, pos, posAux, randomPos = ThreadLocalRandom.current().nextInt(1, parent1.getChromosomeLength() + 1);
		T alleleAux;
		
		System.out.println("-------- Padre 1 ---------");
		System.out.println(childAlleles1.toString());
		System.out.println("-------- Padre 2 ---------");
		System.out.println(childAlleles2.toString());
		
		
		while(i < randomPos){
			pos = ThreadLocalRandom.current().nextInt(0, parent1.getChromosomeLength() - 1);
			if(helper.add(pos)) 
				values.add(childAlleles1.get(pos));
		}
		
		Iterator<T> valuesIter = values.iterator();
		Iterator<Integer> helperIter = helper.iterator();
		posAux = helperIter.next();
	    while (valuesIter.hasNext() && helperIter.hasNext()) {
	    	alleleAux = valuesIter.next();
	    	if(values.contains(childAlleles2.get(posAux))) {
	    		if(posAux != childAlleles2.indexOf(alleleAux))
	    			hash.put(childAlleles2.indexOf(alleleAux), posAux);
	   
	    		childAlleles1.set(posAux, alleleAux);
    			childAlleles1.set(posAux, childAlleles2Aux.get(posAux));
	    		valuesIter.remove();
	    		helperIter.remove();
	    		posAux = helperIter.next();
	    	}else
	    		posAux = helperIter.next();
	    }
	    
		Iterator<Integer> helperIter2 = helper.iterator();
	    while(helperIter2.hasNext()) {
	    	posAux = helperIter2.next();
	    	if(hash.containsKey(posAux)) {
	    		childAlleles1.set(posAux, childAlleles1.get(hash.get(posAux)));
	    		childAlleles2.set(posAux, childAlleles2.get(hash.get(posAux)));
	    	}
	    }
	    
	    
	    System.out.println("-------- Hijo  ---------");
		System.out.println(childAlleles1.toString());
		
		for(int j = 0; i < 27; i++) {
			
			childAlleles2.set(j, childAlleles2Aux.get(childAlleles1Aux.indexOf(childAlleles1.get(j))));
		}
		
		 System.out.println("-------- Hijo  ---------");
			System.out.println(childAlleles2.toString());
		
	    childGenes1.add(parentGenes1.get(0).createGene(childAlleles1));
		childGenes2.add(parentGenes2.get(0).createGene(childAlleles2));
		childChromosomes.setLeftElement(parent1.createChildren(childGenes1));
		childChromosomes.setRightElement(parent2.createChildren(childGenes2));
		
		return childChromosomes;
	}

}
