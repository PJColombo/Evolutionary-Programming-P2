package application.model.p1.model.genetic_algorithm.solution.chromosomes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import application.model.p1.model.genetic_algorithm.solution.genes.Gene;
import application.model.p1.model.genetic_algorithm.solution.genes.TSPGene;

public class TSPFunction extends Chromosome<TSPGene>{
	private int[][] distances; 
	private int initialFinalCity;

	
	public TSPFunction(int size, int[][] distances, int initialFinalCity) {
		super();
		this.distances = distances;
		this.genes = new ArrayList<TSPGene>(size);
		this.chromosomeLength = size - 1;
		this.initialFinalCity = initialFinalCity;
		this.maximize = false;
		this.createRoute(size);
		size -= 1;
		for (int i = 0; i < size; i++) {
			System.out.println("Ciudad " + this.genes.get(i).getDecodedValue());
		}
		
		System.out.println("-------------------------------------------------");
		
		this.fitness = this.calculateFitness();
		System.out.println("Fitness:" + this.fitness);
		
	}
	
	private void createRoute(int distancesSize) {
		ArrayList<Integer> cities = new ArrayList<>(distancesSize);
		int randPos;

		for(int i = 0; i < distancesSize; i++) {
			if(i != initialFinalCity)
				cities.add(i);
		}
		
		for(int i = 0; i < distancesSize - 1; i++) {
			randPos = ThreadLocalRandom.current().nextInt(0, cities.size());
			genes.add(new TSPGene(cities.get(randPos)));
			cities.remove(randPos);
		}
	}
	
	public TSPFunction(TSPFunction tspFunction) {
		this.genes = new ArrayList<>(tspFunction.getGenes());
		this.chromosomeLength = tspFunction.chromosomeLength;
		this.initialFinalCity = tspFunction.initialFinalCity;
		this.fitness = tspFunction.getFitness();
		
		//TODO complete tspfunction cloning
		
	}

	@Override
	protected void calculateFenotype() {}

	@Override
	protected double calculateFitness() {
		double fitness = 0;
		double coste;
		int firstCity = (int) genes.get(0).getDecodedValue(), lastCity;
		int cityI, cityJ;
		if(initialFinalCity > firstCity)
			fitness += coste = distances[initialFinalCity][firstCity];
		else
			fitness += coste = distances[firstCity][initialFinalCity];
		
		System.out.println("De " + initialFinalCity + "hasta " + firstCity + " tiene un coste de : " + coste);
		for(int i = 0, j =  i + 1; i < chromosomeLength - 1; i++, j++) {
			cityI = (int) genes.get(i).getDecodedValue();
			cityJ = (int) genes.get(j).getDecodedValue();
			
			if(cityI < cityJ) {
				coste = distances[cityJ][cityI];
			}
			else {
				coste = distances[cityI][cityJ];
			}
			
			System.out.println("De " + cityI + "hasta " + cityJ + " tiene un coste de : " + coste);
			fitness += coste;
		}
		
		lastCity = (int) genes.get(genes.size() - 1).getDecodedValue();
		
		if(lastCity > initialFinalCity)
			fitness +=  coste = distances[lastCity][initialFinalCity];
		else 
			fitness += coste = distances[initialFinalCity][lastCity];
		
		System.out.println("De " + lastCity + "hasta " + initialFinalCity + " tiene un coste de : " + coste);
		return fitness;
	}

	@Override
	public <U> Chromosome<TSPGene> createChildren(List<Gene<U>> childGenes) { return null; }

	@Override
	public Chromosome<TSPGene> clone() {
		return new TSPFunction(this);
	}

}
