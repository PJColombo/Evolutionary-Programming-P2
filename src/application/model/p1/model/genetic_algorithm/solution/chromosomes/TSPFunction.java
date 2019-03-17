package application.model.p1.model.genetic_algorithm.solution.chromosomes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import application.model.p1.model.genetic_algorithm.solution.genes.Gene;
import application.model.p1.model.genetic_algorithm.solution.genes.TSPGene;

public class TSPFunction extends Chromosome<TSPGene>{
	private int[][] distances; 
	private int dSize;
	private int initialFinalCity;

	
	public TSPFunction(Boolean maximize, int size, int[][] distances, int initialFinalCity) {
		super();
		this.distances = distances;
		this.dSize = size;
		this.genes = new ArrayList<TSPGene>();
		this.chromosomeLength = size - 1;
		this.initialFinalCity = initialFinalCity;
		this.maximize = maximize;
		this.genes.add(new TSPGene(distances, size, initialFinalCity));
		
		System.out.println("-------------------------------------------------");
		
		this.fitness = this.calculateFitness();
		this.calculateFenotype();
		System.out.println("Fitness:" + this.fitness);
		
	}
	
	public TSPFunction(int[][] distances, int dSize, int initialFinalCity, boolean maximize, 
			List<? extends Gene<?>> genes, int chromosomeLength) {
		super();
		this.maximize = maximize;
		this.distances = distances;
		this.dSize = dSize;
		this.initialFinalCity = initialFinalCity;
		
		this.genes = new ArrayList<TSPGene>();
		for (Gene<?> g : genes) 
			this.genes.add((TSPGene) g.clone());
		
		this.chromosomeLength = chromosomeLength;
		this.fitness = this.calculateFitness();
		this.calculateFenotype();
	}
	
	public TSPFunction(TSPFunction tspFunction) {
		this.distances = tspFunction.getDistances();
		this.initialFinalCity = tspFunction.getInitialFinalCity();

		this.genes = new ArrayList<TSPGene>(tspFunction.getGenes().size());
		for (TSPGene g : genes) 
			this.genes.add((TSPGene) g.clone());
		
		this.chromosomeLength = tspFunction.chromosomeLength;
		this.maximize = tspFunction.isMaximize();
		this.elite = tspFunction.isElite();
		this.normalizedFitness = tspFunction.getNormalizedFitness();
		this.fenotype = tspFunction.getFenotype();
		this.score = tspFunction.getScore();
		this.accuScore = tspFunction.getAccuScore();
		this.fitness = tspFunction.getFitness();		
	}

	@Override
	protected void calculateFenotype() {}

	@Override
	protected double calculateFitness() {
		double fitness = 0;
		double coste;
		List<Integer> alleles = genes.get(0).getAlleles();
		int firstCity = (int) alleles.get(0), lastCity;
		int cityI, cityJ;
		if(initialFinalCity > firstCity)
			fitness += coste = distances[initialFinalCity][firstCity];
		else
			fitness += coste = distances[firstCity][initialFinalCity];
		
		System.out.println("De " + initialFinalCity + "hasta " + firstCity + " tiene un coste de : " + coste);
		for(int i = 0, j =  i + 1; i < chromosomeLength - 1; i++, j++) {
			cityI = (int) alleles.get(i);
			cityJ = (int) alleles.get(j);
			
			if(cityI < cityJ)
				coste = distances[cityJ][cityI];
			else
				coste = distances[cityI][cityJ];
			
			System.out.println("De " + cityI + "hasta " + cityJ + " tiene un coste de : " + coste);
			fitness += coste;
		}
		
		lastCity = (int) alleles.get(alleles.size() - 1);
		
		if(lastCity > initialFinalCity)
			fitness +=  coste = distances[lastCity][initialFinalCity];
		else 
			fitness += coste = distances[initialFinalCity][lastCity];
		
		System.out.println("De " + lastCity + "hasta " + initialFinalCity + " tiene un coste de : " + coste);
		return fitness;
	}

	@Override
	public <U> Chromosome<TSPGene> createChildren(List<Gene<U>> childGenes) { 
		return new TSPFunction(this.distances, this.dSize, this.initialFinalCity,
				this.maximize, childGenes, this.chromosomeLength);
	}

	@Override
	public Chromosome<TSPGene> clone() {
		return new TSPFunction(this);
	}

	public int[][] getDistances() {
		return distances;
	}

	public int getInitialFinalCity() {
		return initialFinalCity;
	}
	
	@Override
	public String toString() {
		return "TSPFunction {"
				+ " Fenotype: " + fenotype
				+ " | Fitness: " + fitness
				+ " | Score: " + score
				+ " | AccuScore: " + accuScore
				+ " | decodedGene1 : " + genes.get(0).getDecodedValue()
				+ " | decodedGene2 : " + genes.get(1).getDecodedValue()
				+ "} ";
	}

}
