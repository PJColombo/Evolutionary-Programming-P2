package application.model.p1.model.genetic_algorithm.solution.genes;

import java.util.ArrayList;
import java.util.List;

public class TSPGene extends Gene<Integer>{

	public TSPGene(int cityPos) {
		super();
		this.alleles = new ArrayList<Integer>(1);
		this.alleles.add(cityPos);
		this.interval = null;
		this.size = 1;
		this.decodeGene();
	}

	public TSPGene(TSPGene tspGene) {
		super();
		this.interval = null;
		this.size = tspGene.getSize();
		this.alleles = new ArrayList<Integer>(tspGene.getAlleles());
		this.size = tspGene.getSize();
		this.decodeGene();
	}

	public TSPGene() {}

	@Override
	public void decodeGene() {
		this.decodedValue = this.alleles.get(0);
	}

	@Override
	public Gene<Integer> createGene(List<Integer> alleles) { return null; }

	@Override
	public Gene<Integer> clone() {
		return new TSPGene(this);
	}

	@Override
	public void mutate(int pos) {}

	@Override
	protected void initializeGene() {}

}
