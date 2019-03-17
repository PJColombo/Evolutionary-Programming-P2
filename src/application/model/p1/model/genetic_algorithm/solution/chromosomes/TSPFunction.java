package application.model.p1.model.genetic_algorithm.solution.chromosomes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

import application.model.p1.model.genetic_algorithm.solution.genes.Gene;
import application.model.p1.model.genetic_algorithm.solution.genes.TSPGene;

public class TSPFunction extends Chromosome<TSPGene>{
	TreeSet<Integer> insertedElements = new TreeSet<Integer>();
	private int lastCity;
	
	public TSPFunction(int size, int[][] distances) {
		super();
		int fromCityNumber = 25, randPos;
		this.genes = new ArrayList<TSPGene>(27);
		this.chromosomeLength = size;
		while ((size - 1) != insertedElements.size()) {
			randPos = ThreadLocalRandom.current().nextInt(1, 27 + 1);
			if((randPos != 25) && insertedElements.add(randPos)) {
				if(fromCityNumber > randPos)
					this.genes.add(new TSPGene(distances[fromCityNumber][randPos], randPos));
				else 
					this.genes.add(new TSPGene(distances[randPos][fromCityNumber], randPos));
				fromCityNumber = randPos;
			}
		}
		lastCity = fromCityNumber;
		this.fitness = this.calculateFitness(distances);
		
		
		for (int i = 0; i < size - 1; i++) {
			System.out.println("Ciudad " + this.genes.get(i).getPos() + " Valor: " + this.genes.get(i).getDecodedValue());
		}
		System.out.println("Fitnes:" + this.fitness);
	}
	
	public TSPFunction(TSPFunction tspFunction) {
		
	}
	

	@Override
	protected void calculateFenotype() {}

	
	public double calculateFitness(int[][] distances) {
		int i = 0;
		for(int j = 0; j < this.insertedElements.size(); j++) {
				i += this.genes.get(j).getDecodedValue();
		}
		if(lastCity < 25)
			this.fitness = this.calculateFitness() + distances[25][lastCity];
		else 
			this.fitness = this.calculateFitness() + distances[lastCity][25];
		return i;
	}
	
	@Override
	public <U> Chromosome<TSPGene> createChildren(List<Gene<U>> childGenes) { return null; }

	@Override
	public Chromosome<TSPGene> clone() {
		return new TSPFunction(this);
	}

	@Override
	protected double calculateFitness() {
		return 0;
	}

}
