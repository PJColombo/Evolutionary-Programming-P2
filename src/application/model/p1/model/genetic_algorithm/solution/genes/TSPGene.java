package application.model.p1.model.genetic_algorithm.solution.genes;

import java.util.List;
import java.util.TreeSet;

import application.model.p1_utils.Pair;

public class TSPGene extends Gene<Integer>{

	private int pos;
	
	public TSPGene(double value, int pos) {
		super();
		this.alleles = null;
		this.interval = null;
		this.size = 1;
		this.decodedValue = value;
		this.pos = pos;
	}

	public TSPGene(TSPGene tspGene) {
		// TODO Auto-generated constructor stub
	}

	public TSPGene() {}

	@Override
	protected void initializeGene() {}

	@Override
	public void decodeGene() {}

	@Override
	public Gene<Integer> createGene(List<Integer> alleles) { return null; }

	@Override
	public Gene<Integer> clone() {
		return new TSPGene(this);
	}

	@Override
	public void mutate(int pos) {}

	public int getPos() {
		return this.pos;
	}

}
