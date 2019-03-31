package application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.operators.evolutive_tsp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

import application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.CrossoverOperator;
import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;
import application.model.p1_utils.Pair;

public class PositionOrderCrossover extends CrossoverOperator {

	public PositionOrderCrossover(double crossoverProbability) {
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
		
		TreeSet<Integer> pos = new TreeSet<Integer>();
		List<T> values = new ArrayList<>();
		List<T> allelesParent1 = new ArrayList<>(parent1.getGenes().get(0).getAlleles());
		List<T> allelesProto = new ArrayList<>(parent1.getGenes().get(0).getAlleles());
		List<T> allelesParent2 = new ArrayList<>(parent2.getGenes().get(0).getAlleles());
		List<T> childAlleles2 = new ArrayList<>();
		
		int  aux, randomPos = ThreadLocalRandom.current().nextInt(1, parent1.getChromosomeLength() + 1);
		for (int j = 0; j < randomPos; j++) {
			aux = ThreadLocalRandom.current().nextInt(0, parent1.getChromosomeLength() - 1);
			if(pos.add(aux)) {
				values.add(allelesParent1.get(aux));
			}
		}
		
		Iterator<T> it = values.iterator();
		while(it.hasNext()) {
			allelesParent2.remove(it.next());
		}
		
		for(int i = 0; i < allelesParent1.size();i++) {
			if(!pos.contains(i)) {
				allelesProto.set(i, allelesParent2.get(0));
				allelesParent2.remove(0);
			}
		}

		allelesParent1 = parent1.getGenes().get(0).getAlleles();
		allelesParent2 = parent2.getGenes().get(0).getAlleles();
		
		for(int i = 0; i < allelesParent1.size(); i++){
			setChild2(childAlleles2, allelesParent1, allelesParent2, allelesProto.get(i));
		}

		
		childGenes1.add(parentGenes1.get(0).createGene(allelesProto));
		childGenes2.add(parentGenes2.get(0).createGene(childAlleles2));
		childChromosomes.setLeftElement(parent1.createChildren(childGenes1));
		childChromosomes.setRightElement(parent2.createChildren(childGenes2));
		return childChromosomes;
	}

	
	public <T> void setChild2(List<T> child2, List<T> parent1, List<T> parent2,  T elem) {
		int posAux = parent1.indexOf(elem);
		child2.add(parent2.get(posAux));
	}
}
