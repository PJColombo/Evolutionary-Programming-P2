package application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.operators.evolutive_tsp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import application.model.p1.model.genetic_algorithm.crossover_algorithms.crossover.CrossoverOperator;
import application.model.p1.model.genetic_algorithm.solution.chromosomes.Chromosome;
import application.model.p1.model.genetic_algorithm.solution.genes.Gene;
import application.model.p1_utils.Pair;

public class ClassicOrderCrossover extends CrossoverOperator {

	public ClassicOrderCrossover(double crossoverProbability) {
		super(crossoverProbability);
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> Pair<? extends Chromosome<? extends Gene<T>>, ? extends Chromosome<? extends Gene<T>>> chromosomeCrossover(
			Chromosome<? extends Gene<T>> parent1, Chromosome<? extends Gene<T>> parent2) {
		
		Pair<Chromosome<? extends Gene<T>>, Chromosome<? extends Gene<T>>> childChromosomes = new Pair<>();
		List<T> segment1 = new ArrayList<>(), segment2 = new ArrayList<>();
		List<T> allelesParent1 = parent1.getGenes().get(0).getAlleles();
		List<T> allelesParent2 = parent2.getGenes().get(0).getAlleles();
		List<T> childAlleles1 = new ArrayList<>(27), childAlleles2, remainAllelesToSet, repeatedToSet = new ArrayList<>();
		
		Boolean repeated = false;
		Integer firstRepeated = -1;
		int cp1, cp2, aux,pos;
		int ind, length, allelesSize = allelesParent1.size();
		T g;
		
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
		System.out.println("Padre 1:" + allelesParent1.toString());
		System.out.println("Padre 2:" + allelesParent2.toString());
		
		//Segmentamos la parte a intercambiar
		segment1 = allelesParent2.subList(cp1, cp2);
		
		System.out.println("-------- Invertimos segmentos ---------");
		System.out.println("Segmento 1:" + segment1.toString());
		System.out.println("Segmento 2:" + segment2.toString());
		
		//Creamos los nuevos hijos
		
		childAlleles1.addAll(segment1);
		//Creamos una lista auxiliar
		
		remainAllelesToSet = allelesParent1.subList(cp2, allelesSize);
		remainAllelesToSet.addAll(allelesParent1.subList(0, cp1));
		
		ind = cp2;
		//Insert until the end of the new Alleles
		while((childAlleles1.size() != allelesSize) && !remainAllelesToSet.isEmpty()) {
			if(ind == allelesSize) 
				ind = 0;
			
			if(!segment1.contains(remainAllelesToSet.get(0))) {
				if(ind >= cp2)
					childAlleles1.add(remainAllelesToSet.get(0));
				else 
					childAlleles1.add(ind, remainAllelesToSet.get(0));
				ind++;
			}else 
				repeatedToSet.add(remainAllelesToSet.get(0));
			
			remainAllelesToSet.remove(0);
			System.out.println("-------- Hijo -----------");
			System.out.println(childAlleles1.toString());
			System.out.println("-------- Faltan ---------");
			System.out.println(remainAllelesToSet.toString());
			System.out.println("-------- Repetidos ------");
			System.out.println(repeatedToSet.toString());
			
		}
		//Insert the new alleles until it is completed
		
		segment2 = allelesParent1.subList(cp1, cp2);
		
		System.out.println("-------- Segmento 1 ---------");
		System.out.println(segment1.toString());
		System.out.println("-------- Segmento 2 ---------");
		System.out.println(segment2.toString());
		while(childAlleles1.size() != allelesSize) {
			if(ind == allelesSize){
				ind = 0;
			}
			pos = segment1.indexOf(repeatedToSet.get(0));
			g = segment2.get(pos);
			childAlleles1.add(ind, g);
			
			repeatedToSet.remove(0);
			System.out.println("-------- Segmento 1 ---------");
			System.out.println(segment1.toString());
			System.out.println("-------- Segmento 2 ---------");
			System.out.println(segment2.toString());
			System.out.println("-------- Padre ---------");
			System.out.println(parent1.getGenes().get(0).getAlleles().toString());
			System.out.println("-------- Hijo ---------");
			System.out.println(childAlleles1.toString());
			System.out.println("-------- Faltan ---------");
			System.out.println(repeatedToSet.toString());
		}
		
		System.out.println(childAlleles1.toString());
		System.out.println(parent1.getGenes().get(0).getAlleles().toString());
		return null;
	}

}
