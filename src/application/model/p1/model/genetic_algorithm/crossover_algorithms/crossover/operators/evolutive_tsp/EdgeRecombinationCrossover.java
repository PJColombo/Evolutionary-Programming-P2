package application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.operators.evolutive_tsp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.CrossoverOperator;
import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;
import application.model.p1_utils.Pair;

public class EdgeRecombinationCrossover extends CrossoverOperator {

	private Integer initialFinalCity;
	
	public EdgeRecombinationCrossover(double crossoverProbability, int initialFinalCity) {
		super(crossoverProbability);
		this.initialFinalCity = initialFinalCity;
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
		ArrayList<HashSet<T>> neighbourList1 = this.createNeighbourList(parentGenes1, parentGenes2), neighbourList2 = new ArrayList<>(neighbourList1);
		//data structure 
		HashSet<Integer> checkList1, checkList2;
		Integer initialNode, currNode; 
		boolean firstChildCompleted, secondChildCompleted;
		for(int i = 0; i < parentGenes1.size(); i++) {
			parentAlleles1 = parentGenes1.get(i).getAlleles();
			parentAlleles2 = parentGenes2.get(i).getAlleles();
			childAlleles1 = new ArrayList<>(parentAlleles1.size());
			childAlleles2 = new ArrayList<>(parentAlleles2.size());
			
			checkList1 = new HashSet<>(parentAlleles1.size()); checkList2 = new HashSet<>(parentAlleles2.size());
			firstChildCompleted = secondChildCompleted = false;
			initialNode = ThreadLocalRandom.current().nextInt(0,2);
			if(initialNode == 0) {
				childAlleles1.add(parentAlleles1.get(0));
				childAlleles2.add(parentAlleles2.get(0));				
			}
			else {
				childAlleles1.add(parentAlleles2.get(initialNode));
				childAlleles2.add(parentAlleles1.get(initialNode));				
			}
			while(!firstChildCompleted && !secondChildCompleted) {
				if(!firstChildCompleted) {
					currNode = (int) childAlleles1.get(childAlleles1.size() - 1);
					
					this.crossNode(currNode, neighbourList1);
					
					currNode = this.pickSmallestNeighbour(currNode, neighbourList1);
					
					while(currNode == null) {
						//size + 1 because alleles only have N - 1 cities (doesn't have initial/final city).
						currNode = ThreadLocalRandom.current().nextInt(0, parentAlleles1.size() + 1);
						currNode = currNode == this.initialFinalCity || checkList1.contains(currNode) ? null : currNode;
					}
					childAlleles1.add((T) currNode);
					checkList1.add(currNode);
					firstChildCompleted = childAlleles1.size() == parentAlleles1.size();
					
				}
				
				if(!secondChildCompleted) {
					currNode = (int) childAlleles2.get(childAlleles2.size() - 1);
					
					this.crossNode(currNode, neighbourList2);
					
					currNode = this.pickSmallestNeighbour(currNode, neighbourList2);
					
					while(currNode == null) {
						currNode = ThreadLocalRandom.current().nextInt(0, parentAlleles2.size() + 1);
						currNode = currNode == this.initialFinalCity || checkList2.contains(currNode) ? null : currNode;
					}
					childAlleles2.add((T) currNode);
					checkList2.add(currNode);
					secondChildCompleted = childAlleles2.size() == parentAlleles2.size();
				}
			}
			//Create child genes.
			childGenes1.add(parentGenes1.get(i).createGene(childAlleles1));
			childGenes2.add(parentGenes2.get(i).createGene(childAlleles2));
		}
		//Create child chromosomes.
		childChromosomes.setLeftElement(parent1.createChildren(childGenes1));
		childChromosomes.setRightElement(parent2.createChildren(childGenes2));
		
		return childChromosomes;
	}

	@SuppressWarnings("unchecked")
	private <T> Integer pickSmallestNeighbour(int node, ArrayList<HashSet<T>> neighbourList) {
		Integer res = null;
		int smallestSize = Integer.MAX_VALUE, curr, currSize;
		Iterator<Integer> iter = (Iterator<Integer>) neighbourList.get((int) node).iterator();
		
		while(iter.hasNext()) {
			curr = iter.next();
			if(curr != this.initialFinalCity) {				
				if(res == null) {
					res = curr;
					smallestSize = neighbourList.get(res).size();
				}
				else {
					currSize = neighbourList.get(curr).size();
					if(currSize < smallestSize) {
						res = curr;
						smallestSize = currSize;
					}
					else if(currSize == smallestSize) {
						//Take a random node if both have the same neighbour amount. 
						if(ThreadLocalRandom.current().nextDouble() < 0.5)
							res = curr;
					}
				}
			}
		}
		
		return res;
	}
	
	private <T> void crossNode(int node, ArrayList<HashSet<T>> neighbourList) {
		for(int i = 0; i < neighbourList.size(); i++)
			neighbourList.get(i).remove(node);
	}
	
	private <T> ArrayList<HashSet<T>> createNeighbourList(List<Gene<T>> genes1, List<Gene<T>> genes2) {
		//size + 1 because we need to count the initial/final city.
		int numberCities = genes1.get(0).getSize() + 1;
		ArrayList<HashSet<T>> neighbourList = new ArrayList<>(numberCities);
		for(int i = 0; i < numberCities; i++)
			neighbourList.add(new HashSet<>());
		List<T> alleles1, alleles2; 
		T element1, element2;
		int elementPos1, elementPos2;
		for(int i = 0; i < genes1.size(); i++) {
			alleles1 = genes1.get(i).getAlleles();
			alleles2 = genes2.get(i).getAlleles();
			for(int j = 0; j < alleles1.size(); j++) {				
				element1 = alleles1.get(j); element2 = alleles2.get(j);
				elementPos1 = (int) element1; elementPos2 = (int) element2;
				//add the last element if I'm in the first element
				if(j == 0) {
					neighbourList.get(elementPos1).add(alleles1.get(alleles1.size() - 1));
					neighbourList.get(elementPos1).add(alleles1.get(j + 1));
					
					neighbourList.get(elementPos2).add(alleles2.get(alleles2.size() - 1));
					neighbourList.get(elementPos2).add(alleles2.get(j + 1));
				}
				//add the first element if I'm in the last element.
				else if(j == alleles1.size() - 1) {
					neighbourList.get(elementPos1).add(alleles1.get(0));
					neighbourList.get(elementPos1).add(alleles1.get(j - 1));					
					
					neighbourList.get(elementPos2).add(alleles2.get(0));
					neighbourList.get(elementPos2).add(alleles2.get(j - 1));
				}
				else {
					neighbourList.get(elementPos1).add(alleles1.get(j - 1));					
					neighbourList.get(elementPos1).add(alleles1.get(j + 1));
					
					neighbourList.get(elementPos2).add(alleles2.get(j - 1));
					neighbourList.get(elementPos2).add(alleles2.get(j + 1));
					
				}
			}
		}
		return neighbourList;
	}
}
