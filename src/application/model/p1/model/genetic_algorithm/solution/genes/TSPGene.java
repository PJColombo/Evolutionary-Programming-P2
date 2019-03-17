package application.model.p1.model.genetic_algorithm.solution.genes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TSPGene extends Gene<Integer>{

	private int [][] distances; 
	private int dSize;
	private int initialFinalCity;
	
	@SuppressWarnings("unchecked")
	public TSPGene(List<?> alleles, int[][] distances, int dSize, int initialFinalCity,
			int size) {
		this.distances = distances;
		this.dSize = dSize;
		this.initialFinalCity = initialFinalCity;
		this.size = size;
		this.alleles = new ArrayList<>((List<Integer>) alleles);
		this.decodeGene();
	}
	
	public TSPGene(int [][] distances, int dSize, int initialFinalCity) {
		super();
		this.distances = distances; 
		this.dSize = dSize;
		this.initialFinalCity = initialFinalCity;
		this.alleles = new ArrayList<Integer>(dSize - 1);
		this.initializeGene();
		this.size = dSize - 1;
		this.decodeGene();
	}

	public TSPGene(TSPGene tspGene) {
		super();
		this.distances = tspGene.getDistances();
		this.dSize = tspGene.getdSize();
		this.initialFinalCity = tspGene.getInitialFinalCity();
		this.size = tspGene.getSize();
		this.alleles = new ArrayList<Integer>(tspGene.getAlleles());
		this.size = tspGene.getSize();
		this.decodeGene();
	}

	public TSPGene() {}

	@Override
	protected void initializeGene() {
		ArrayList<Integer> cities = new ArrayList<>(dSize);
		int randPos;

		for(int i = 0; i < dSize; i++) {
			if(i != initialFinalCity)
				cities.add(i);
		}
		
		for(int i = 0; i < dSize - 1; i++) {
			randPos = ThreadLocalRandom.current().nextInt(0, cities.size());
			alleles.add(cities.get(randPos));
			cities.remove(randPos);
		}
	}
	
	@Override
	public void decodeGene() {
		decodedValue = 0;
		for (Integer a : alleles)
			decodedValue += a;
	}

	@Override
	public Gene<Integer> createGene(List<Integer> alleles) {
		return new TSPGene(alleles, this.distances, this.dSize, this.initialFinalCity,
				this.size);
	}

	@Override
	public Gene<Integer> clone() {
		return new TSPGene(this);
	}

	@Override
	public void mutate(int pos) {}

	public int[][] getDistances() {
		return distances;
	}

	public int getdSize() {
		return dSize;
	}

	public int getInitialFinalCity() {
		return initialFinalCity;
	}

	
}
