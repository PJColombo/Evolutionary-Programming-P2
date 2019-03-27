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
		TreeSet<Integer> helper = new TreeSet<Integer>();
		List<T> values = new ArrayList<>();
		List<T> allelesParent1 = parent1.getGenes().get(0).getAlleles();
		List<T> allelesParent2 = parent2.getGenes().get(0).getAlleles();
		List<T> childAlleles1 = parent1.getGenes().get(0).getAlleles();
		List<T> childAlleles2 = parent2.getGenes().get(0).getAlleles();
		T alleleAux;
		
		
		//Generamos el numero total de posiciones a intercambiar
		int i = 0, aux, randomPos = ThreadLocalRandom.current().nextInt(1, parent1.getChromosomeLength() + 1);
		for (int j = 0; j < 2; j++) {
			System.out.println("-------- Padre -----------");
			if(j == 0) 
				System.out.println(childAlleles2.toString());
			else
				System.out.println(childAlleles1.toString());
			
			//Generamos los indices de las posiciones, y extraemos la ciudad que ocupa esa posicion
			//Esa ciudad se busca en el allelo opuesto y se extrae la posicion, además guardamos esa ciudad
			while(i < randomPos){
				aux = ThreadLocalRandom.current().nextInt(0, parent1.getChromosomeLength() - 1);
				if(helper.add(aux)) {
					alleleAux = (i == 0) ? allelesParent1.get(aux) : allelesParent2.get(aux);
					if(j == 0) 
						pos.add(allelesParent2.indexOf(alleleAux));
					else 
						pos.add(allelesParent1.indexOf(alleleAux));
					values.add(alleleAux);
					i++;
				}
			}
			
			i = 0;
			Iterator<Integer> iterPos = pos.iterator();
		    while (iterPos.hasNext()) {
		    	if(j == 0 )
					childAlleles2.set(iterPos.next(), values.get(i));
		    	else
					childAlleles1.set(iterPos.next(), values.get(i));
				i++;
		    }
			i = 0;
			pos.clear();
			helper.clear();
			values.clear();
		}
		
		childGenes1.add(parentGenes1.get(0).createGene(childAlleles1));
		childGenes2.add(parentGenes2.get(0).createGene(childAlleles2));
		childChromosomes.setLeftElement(parent1.createChildren(childGenes1));
		childChromosomes.setRightElement(parent2.createChildren(childGenes2));
		return childChromosomes;
	}

}
