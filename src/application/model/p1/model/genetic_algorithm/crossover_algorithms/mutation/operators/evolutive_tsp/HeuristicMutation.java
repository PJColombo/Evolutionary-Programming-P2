package application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation.operators.evolutive_tsp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import application.model.p1.model.genetic_algorithm.crossover_algorithms.mutation.MutationOperator;
import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;
import application.model.p1_utils.TSPDistancesCalculator;

public class HeuristicMutation extends MutationOperator {

	public HeuristicMutation(double mutationProbability) {
		super(mutationProbability);
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> Chromosome<? extends Gene<T>> mutation(Chromosome<? extends Gene<T>> c1) {
		@SuppressWarnings("unchecked")
		List<Gene<T>> parentGenes = (List<Gene<T>>) c1.getGenes(),
				childGenes = new ArrayList<Gene<T>>();
		List<T> bestAlleles = new ArrayList<>(c1.getGenes().get(0).getAlleles());
		List<T> alleles = new ArrayList<>(c1.getGenes().get(0).getAlleles());
		List<T> elem = new ArrayList<T>();
		List<Integer> pos = new ArrayList<Integer>();
		List<List<T>> permutationList = new ArrayList<List<T>>();
		double prob;
		double bestPun = 0, punt;
		
		for(int a = 0; a < alleles.size(); a++) {		
			prob = ThreadLocalRandom.current().nextDouble();
			if(prob < this.mutationProbability) {
				elem.add(alleles.get(a));
				pos.add(a);
			}
		}
		if(elem.size() > 0)
			PermutationGenerator(elem, permutationList, elem.size());
		
		int k = 0;
		for (int i = 0; i < permutationList.size(); i++) {
			for (Iterator<Integer> iterator = pos.iterator(); iterator.hasNext();) {
				int aux = iterator.next();
				alleles.set(aux,permutationList.get(i).get(k));
				k++;
			}
			punt = TSPDistancesCalculator.Calculator(25, alleles);
			if(punt < bestPun || bestPun == 0) {
				bestPun = punt;
				bestAlleles = new ArrayList<>(alleles);
			}
			permutationList.get(i);
			k=0;
		}

		childGenes.add(parentGenes.get(0).createGene(bestAlleles));
		return c1.createChildren(childGenes);
	}
	
	private static <T> void PermutationGenerator(List<T> act, List<List<T>> permutationList, int end) {
		List<T> aux;
		if (end == 1) {
			aux = new ArrayList<>(act);
            permutationList.add(aux);
        }else {
	        for (int i = 0; i < end - 1; i++) {
	        	PermutationGenerator(act, permutationList, end - 1);
	        	if(end %2 == 0)
	        		swap(act, i, end - 1);
	        	else
	        		swap(act, 0, end - 1);
	        }
	        PermutationGenerator(act, permutationList, end - 1);
        }
	}
	
	private static <T> void swap(List<T> a, int i, int j){
		T temp = a.get(i); 
        a.set(i, a.get(j)); 
        a.set(j, temp); 
	}

}
