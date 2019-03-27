package application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.operators.evolutive_tsp;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.IntStream;

import application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.CrossoverOperator;
import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;
import application.model.p1_utils.Pair;

public class OrdinalCrossover extends CrossoverOperator {

	public OrdinalCrossover(double crossoverProbability) {
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
		
		
		List<T> parentAlleles1 = parent1.getGenes().get(0).getAlleles();
		List<T> parentAlleles2 = parent2.getGenes().get(0).getAlleles();
		List<T> childAlleles1 = new ArrayList<>(parentAlleles1.size());
		List<T> childAlleles2 = new ArrayList<>(parentAlleles1.size());
		List<Integer> dinamicAll1 = new ArrayList<Integer>(27);
		List<Integer> dinamicAll2 = new ArrayList<Integer>(27);
		List<Integer> dinamicList1 = createDinamicList(parentAlleles1.size());
		List<Integer> dinamicList2 = createDinamicList(parentAlleles1.size());
		List<Integer> dinamicAux1 = new ArrayList<Integer>(27);
		List<Integer> dinamicAux2 = new ArrayList<Integer>(27);
		T dataP1, dataP2;
		int dA1, dA2, dL1, dL2, index;
		
		for (int i = 0; i < parentAlleles1.size(); i++) {
			int aux = (int) parentAlleles1.get(i);
			int aux2 = (int) parentAlleles2.get(i);
			dinamicAll1.add(i, dinamicList1.indexOf(aux));
			dinamicAll2.add(i, dinamicList2.indexOf(aux2));
			dinamicList1.remove(dinamicList1.get(dinamicList1.indexOf(aux)));
			dinamicList2.remove(dinamicList2.get(dinamicList2.indexOf(aux2)));
		}	
		dinamicAux1.addAll(dinamicAll1.subList(0, parentAlleles1.size() / 2));	
		dinamicAux1.addAll(dinamicAll2.subList((parentAlleles1.size() / 2), parentAlleles1.size()));
		dinamicAux2.addAll(dinamicAll2.subList(0, parentAlleles1.size() / 2));
		dinamicAux2.addAll(dinamicAll1.subList((parentAlleles1.size() / 2), parentAlleles1.size()));
		
		dinamicList1 = createDinamicList(parentAlleles1.size());
		dinamicList2 = createDinamicList(parentAlleles1.size());
		
		for (int i = 0; i < parentAlleles1.size(); i++) {
			//Obtenemos el valor dinamico y su posicion en la lista dinamica
			dA1 = dinamicAux1.get(i);
			dL1 = dinamicList1.get(dA1);
			dA2 = dinamicAux2.get(i);
			dL2 = dinamicList2.get(dA2);
			
			//Comprobamos que el valor sacado de la lista dinamica no sea 25 
			if(dL1 != 25) {
				index = parentAlleles1.indexOf(dL1);
				dataP1 = parentAlleles1.get(index);
				childAlleles1.add(dataP1);
				dinamicList1.remove(dA1);
			}
			if(dL2 != 25) {
				index = parentAlleles2.indexOf(dL2);
				dataP2 = parentAlleles2.get(index);
				childAlleles2.add(dataP2);
				dinamicList2.remove(dA2);
			}
			
		}
		
		childGenes1.add(parentGenes1.get(0).createGene(childAlleles1));
		childGenes2.add(parentGenes2.get(0).createGene(childAlleles2));
		childChromosomes.setLeftElement(parent1.createChildren(childGenes1));
		childChromosomes.setRightElement(parent2.createChildren(childGenes2));
		
		return childChromosomes;
	}
	
	private List<Integer> createDinamicList(int size){
		List<Integer> dinamicList1 = new ArrayList<Integer>();
		for (int i = 0; i < size + 1; i++) {
			dinamicList1.add(i);
		}
		return dinamicList1;
	}

}
